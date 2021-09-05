package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.population.Happiness;
import com.tsoft.civilization.combat.*;
import com.tsoft.civilization.combat.event.AttackDoneEvent;
import com.tsoft.civilization.combat.event.UnitHasWonAttackEvent;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.service.move.MoveUnitService;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;

import java.util.Collection;

/**
 * Attacker    |  Target              | Attacker's actions
 * ------------|----------------------|-----------------------------------------------------------------|-------------------------
 * Ranged Unit |  Ranged Unit         | Ranged fire (can destroy it)                                    | Ranged backFire (can destroy it, but not both)
 *             |     with civilian    |    can destroy / capture it                                     |
 *             |----------------------|-----------------------------------------------------------------|-------------------------
 *             |  Melee Unit          | Ranged fire (can destroy it)                                    | Melee backfire if the Attacker on the adj tile
 *             |     with civilian    |    can destroy / capture it                                     |
 *             |----------------------|-----------------------------------------------------------------|-------------------------
 *             |  City                | Ranged fire (can NOT destroy it)                                | Ranged backfire (can destroy it)
 *             |     civilian units   |    all types of units positioned in the city's tile             |
 *             |     military units   |    are completely invulnerable to attacks                       |
 *             |     buildings        |    can destroy them (player can select which)                   |
 *             |     wonders          |    can destroy them (player can select which)                   |
 *             |     citizens         |    can destroy them till minimum (depends on Era)               |
 *             |----------------------|-----------------------------------------------------------------|-------------------------
 *             | City-state           | As the usual city except it can not be razed                    |
 *             |----------------------|-----------------------------------------------------------------|-------------------------
 *             |  Tile                | nothing                                                         |
 *             |     with improvement | Ranged fire (can destroy it)                                    |
 *             |     with resource    | nothing                                                         |
 * ------------|----------------------|-----------------------------------------------------------------|-------------------------
 * Melee Unit  |  Ranged Unit         | Attack if the Target on the route tile (before the attack moves | Ranged backfire (can destroy it, but not both)
 *             |                      | to the adj tile)                                                |
 *             |     with civilian    |    can destroy / capture it                                     |
 *             |----------------------|-----------------------------------------------------------------|-------------------------
 *             |  Melee Unit          | Attack if the Target on the route tile (moves to the adj tile)  | Backfire (can destroy it, but not both)
 *             |     with civilian    |    can destroy / capture it                                     |
 *             |----------------------|-----------------------------------------------------------------|-------------------------
 *             |  City                | Attack if the city on the route tile (moves to the adj tile)    |
 *             |                      |    All types of units positioned in the city's tile             |
 *             |                      |    are completely invulnerable to attacks                       |
 *             |                      |    If the attacker wins, then it moves on the City's tile       |
 *             |                      |    (can raze / capture / annex / puppet the city)               |
 *             |     civilian units   |    captured                                                     |
 *             |     military units   |    destroyed  (destroyed on lose)                               |
 *             |     buildings        |    All the defensive and some randomly selected destroyed       |
 *             |     citizens         |    destroyed on 60%                                             |
 *             |     great people     |    destroyed                                                    |
 *             |----------------------|-----------------------------------------------------------------|-------------------------
 *             | City-state           | As the usual city except it can not be razed                    |
 *             |----------------------|-----------------------------------------------------------------|-------------------------
 *             |  Tile                | Can capture it                                                  |
 *             |     with improvement | Fire (can destroy it)                                           |
 *             |     with resource    | nothing                                                         |
 * ------------|------------------------------------------------------------------------------------------------------------------
 * City        |  Ranged Unit         | Ranged fire (can destroy it)                                    | no backfire
 *             |     with civilian    | nothing                                                         |
 *             |  Melee Unit          | Ranged fire (can destroy it)                                    | no backfire
 *             |     with civilian    | nothing                                                         |
 * ------------|------------------------------------------------------------------------------------------------------------------
 */
public class BaseCombatService {

    private static final MoveUnitService moveService = new MoveUnitService();
    private static final CaptureUnitService captureService = new CaptureUnitService();

    HasCombatStrengthList getPossibleTargetsAround(HasCombatStrength attacker, int radius) {
        World world = attacker.getCivilization().getWorld();
        Collection<Point> locations = world.getLocationsAround(attacker.getLocation(), radius);

        CityList citiesAround = world.getCitiesAtLocations(locations, attacker.getCivilization());
        UnitList unitsAround = world.getUnitsAtLocations(locations, attacker.getCivilization());

        HasCombatStrengthList targets = new HasCombatStrengthList();
        targets.addAll(citiesAround);
        targets.addAll(unitsAround);

        return targets;
    }

    CombatResult attack(HasCombatStrength attacker, HasCombatStrength target, int strikeStrength) {
        World world = attacker.getCivilization().getWorld();

        /** PREPARE THE ATTACK */

        CombatantState.CombatantStateBuilder attackerState = CombatantState.builder()
            .strikeStrength(strikeStrength);

        int targetDefenseStrength = target.calcCombatStrength().getDefenseStrength();
        CombatantState.CombatantStateBuilder targetState = CombatantState.builder()
            .defenseStrength(targetDefenseStrength);

        // before the attack calculate target's backfire strength
        int targetBackFireStrength = calcTargetBackFireStrength(attacker, target);
        targetState.strikeStrength(targetBackFireStrength);

        // get target's defense experience
        int targetDefenseExperience = target.calcCombatStrength().getDefenseExperience();
        targetState.defenseExperience(targetDefenseExperience);

        // attacker's strike strength will be discounted on target's defense experience
        strikeStrength -= targetDefenseExperience;
        if (strikeStrength <= 0) strikeStrength = 1;
        attackerState.appliedStrikeStrength(strikeStrength);

        // a ranged attack can't destroy a city
        if (attacker.getUnitCategory().isRanged() && target.getUnitCategory().isCity()) {
            if (targetDefenseStrength <= strikeStrength) {
                strikeStrength = targetDefenseStrength - 1;
            }
        }

        /** ATTACK */

        // fire !
        targetDefenseStrength -= strikeStrength;

        //
        // TARGET UPDATE
        //

        // target's defense strength decrease
        targetState.defenseStrengthAfterAttack(targetDefenseStrength);

        // target's damage update after the attack
        CombatDamage targetDamage = CombatDamage.builder().damage(strikeStrength).build();
        target.addCombatDamage(targetDamage);

        // target's ranged attack experience won't change
        int targetRangedAttackExperience = target.calcCombatStrength().getRangedAttackExperience();
        targetState.rangedExperience(targetRangedAttackExperience);
        int targetRangedAttackExperienceAfterAttack = targetRangedAttackExperience;
        targetState.rangedExperienceAfterAttack(targetRangedAttackExperienceAfterAttack);

        // target's melee attack experience won't change
        int targetMeleeAttackExperience = target.calcCombatStrength().getMeleeAttackExperience();
        targetState.meleeExperience(targetMeleeAttackExperience);
        int targetMeleeAttackExperienceAfterAttack = targetMeleeAttackExperience;
        targetState.meleeExperienceAfterAttack(targetMeleeAttackExperienceAfterAttack);

        // target's defense experience increase
        int targetDefenseExperienceAfterAttack = targetDefenseExperience + 1;
        targetState.defenseExperience(targetDefenseExperience);
        targetState.defenseExperienceAfterAttack(targetDefenseExperienceAfterAttack);

        // target's experience update after the attack
        CombatExperience targetExperience = CombatExperience.builder()
            .rangedAttackExperience(targetRangedAttackExperienceAfterAttack)
            .meleeAttackExperience(targetMeleeAttackExperienceAfterAttack)
            .defenseExperience(targetDefenseExperienceAfterAttack)
            .build();
        target.addCombatExperience(targetExperience);

        // target's status
        boolean targetDestroyed = (targetDefenseStrength <= 0);
        targetState.isDestroyed(targetDestroyed);

        //
        // ATTACKER UPDATE
        //

        // attacker's ranged attack experience increase
        int attackerRangedAttackExperience = attacker.calcCombatStrength().getRangedAttackExperience();
        attackerState.rangedExperience(attackerRangedAttackExperience);
        int attackerRangedAttackExperienceAfterAttack = attackerRangedAttackExperience;
        if (attacker.getUnitCategory().isRanged()) {
            attackerRangedAttackExperienceAfterAttack = attackerRangedAttackExperienceAfterAttack + 2;
        }
        attackerState.rangedExperienceAfterAttack(attackerRangedAttackExperienceAfterAttack);

        // attacker's melee attack experience increase
        int attackerMeleeAttackExperience = attacker.calcCombatStrength().getMeleeAttackExperience();
        attackerState.meleeExperience(attackerMeleeAttackExperience);
        int attackerMeleeAttackExperienceAfterAttack = attackerMeleeAttackExperience;
        if (attacker.getUnitCategory().isMelee()) {
            attackerMeleeAttackExperienceAfterAttack = attackerMeleeAttackExperience + 2;
        }
        attackerState.meleeExperienceAfterAttack(attackerMeleeAttackExperienceAfterAttack);

        /** BACKFIRE */

        // the target backfired the attacker
        int attackerDefenseStrength = attacker.calcCombatStrength().getDefenseStrength();
        attackerState.defenseStrength(attackerDefenseStrength);

        int attackerDefenseStrengthAfterAttack = attackerDefenseStrength - targetBackFireStrength;

        // fighting units can't be destroyed simultaneously
        if (attackerDefenseStrengthAfterAttack <= 0 && targetDestroyed) {
            attackerDefenseStrengthAfterAttack = 1;
        }
        attackerState.defenseStrengthAfterAttack(attackerDefenseStrengthAfterAttack);

        // attacker's backfire damage
        CombatDamage attackerDamage = CombatDamage.builder().damage(targetBackFireStrength).build();
        attacker.addCombatDamage(attackerDamage);

        // attacker's defense experience increase
        int attackerDefenseExperience = attacker.calcCombatStrength().getDefenseExperience();
        int attackerDefenseExperienceAfterAttack = attackerDefenseExperience;
        if (targetBackFireStrength > 0) {
            attackerDefenseExperienceAfterAttack = attackerDefenseExperience + 1;
        }
        attackerState.defenseExperience(attackerDefenseExperience);
        attackerState.defenseExperienceAfterAttack(attackerDefenseExperienceAfterAttack);

        // attacker's experience update after the attack
        CombatExperience attackerExperience = CombatExperience.builder()
            .rangedAttackExperience(attackerRangedAttackExperienceAfterAttack)
            .meleeAttackExperience(attackerMeleeAttackExperienceAfterAttack)
            .defenseExperience(attackerDefenseExperienceAfterAttack)
            .build();
        attacker.addCombatExperience(attackerExperience);

        // attacker's state
        boolean attackerDestroyed = (attackerDefenseStrengthAfterAttack <= 0);
        attackerState.isDestroyed(attackerDestroyed);

        // set to 0 attacker's passScore - it can't action (move, attack, capture etc) anymore
        attacker.setPassScore(0);

        /** SUMMARY */

        if (targetDestroyed) {
            destroyTarget(attacker, target);
            moveAttacker(attacker, target);
        }

        if (attackerDestroyed) {
            destroyTarget(target, attacker);
            moveAttacker(target, attacker);
        }

        // send the event about the attack
        world.sendEvent(AttackDoneEvent.builder()
            .build());

        // return the result
        return CombatResult.builder()
            .attackerState(attackerState.build())
            .targetState(targetState.build())
            .status(CombatStatus.DONE)
            .build();
    }

    // City can't be destroyed during an attack
    // It can be only captured (and destroyed later in a separate action)
    private void destroyTarget(HasCombatStrength attacker, HasCombatStrength victim) {
        if (victim instanceof City) {
            captureService.captureCity(attacker, (City)victim);
            return;
        }

        // if the attacker is a ranged unit
        // then destroy the target only
        if (attacker.getUnitCategory().isRanged()) {
            destroy(attacker, victim);
            return;
        }

        // if attacker is a melee unit
        // then destroy all units on that location
        destroyAllUnitsAtLocation(attacker, victim.getLocation());
    }

    private void moveAttacker(HasCombatStrength attacker, HasCombatStrength victim) {
        if (attacker.getUnitCategory().isMelee()) {
            moveService.doMoveUnit((AbstractUnit) attacker, victim.getLocation());
        }
    }

    // destroy all units located in that location
    public void destroyAllUnitsAtLocation(HasCombatStrength attacker, Point location) {
        Civilization winner = attacker.getCivilization();
        UnitList victims = winner.getWorld().getUnitsAtLocation(location);

        for (AbstractUnit victim : victims) {
            destroy(attacker, victim);
        }
    }

    public void destroy(HasCombatStrength attacker, HasCombatStrength victim) {
        victim.destroy();

        attacker.getCivilization().addEvent(UnitHasWonAttackEvent.builder()
            .attackerName(attacker.getView().getName())
            .targetName(victim.getView().getName())
            .build()
        );

        // A city can be only captured (and destroyed later)
        // This solution lets do not use a dialog with a player
        if (victim instanceof City) {
            throw new IllegalArgumentException("A city can only be captured, FIXME: use CaptureService#captureCity()");
        }

        Civilization loser = victim.getCivilization();
        loser.getUnitService().destroyUnit((AbstractUnit) victim);
    }

    /** Calc the strike strength */
    int calcStrikeStrength(HasCombatStrength attacker, int strikeStrength, HasCombatStrength target) {
        int strikeSkillsPercent = calcAttackerStrikeSkillsPercent(attacker, target);
        int strikeGeneralsPercent = getGreatGeneralCount(attacker) * 33;
        int strikeTerrainPercent = 0;
        int strikeAgainstThatTypePercent = 0;
        int strikeHappinessPercent = calcAttackerStrikeHappinessPercent(attacker);

        return strikeStrength + //attacker.calcCombatStrength().getAttackExperience() +
            (int)Math.round(((double)strikeStrength * (double)strikeSkillsPercent ) / 100.0 +
                ((double)strikeStrength * (double)strikeGeneralsPercent ) / 100.0 +
                ((double)strikeStrength * (double)strikeTerrainPercent ) / 100.0 +
                ((double)strikeStrength * (double)strikeAgainstThatTypePercent ) / 100.0);
    }

    private int calcAttackerStrikeSkillsPercent(HasCombatStrength attacker, HasCombatStrength target) {
        int value = 0;
        //for (AbstractSkill skill : attacker.getSkills()) {
        //    value += skill.getAttackStrikePercent(attacker, target);
        //}
        return value;
    }

    private int calcAttackerStrikeHappinessPercent(HasCombatStrength attacker) {
        int value = 0;

        Happiness happiness = attacker.getCivilization().calcHappiness();
        if (happiness.getTotal() < -30) {
            value -= 33;
        } else
        if (happiness.getTotal() < -15) {
            value -= 15;
        } else
        if (happiness.getTotal() > 30) {
            value += 33;
        } else
        if (happiness.getTotal() > 15) {
            value += 15;
        }

        return value;
    }

    /** Calc the target's back strike strength (strike strength on defense) */
    private int calcTargetBackFireStrength(HasCombatStrength attacker, HasCombatStrength target) {
        int targetBackFireStrength = 0;

        if (target.getUnitCategory().isRanged()) {
            targetBackFireStrength = target.calcCombatStrength().getRangedBackFireStrength();
        }

        // Melee unit must be on adjacent tile to fire back
        if (target.getUnitCategory().isMelee()) {
            Point attackerLocation = attacker.getLocation();
            Point targetLocation = target.getLocation();
            if (!attacker.getCivilization().getTilesMap().isTilesNearby(attackerLocation, targetLocation)) {
                return 0;
            }

            targetBackFireStrength = target.calcCombatStrength().getMeleeBackFireStrength();
        }

        int strikeOnDefenseSkillPercent = calcTargetStrikeOnDefenseSkillsPercent(attacker, target);
        int strikeOnDefenseGeneralsPercent = getGreatGeneralCount(attacker) * 10;
        int strikeOnDefenseTerrainPercent = 0;
        int strikeOnDefenseAgainstThatTypePercent = 0;

        return targetBackFireStrength +
            (int)Math.round(
                ((double) targetBackFireStrength * (double)strikeOnDefenseSkillPercent ) / 100.0 +
                ((double) targetBackFireStrength * (double)strikeOnDefenseGeneralsPercent ) / 100.0 +
                ((double) targetBackFireStrength * (double)strikeOnDefenseTerrainPercent ) / 100.0 +
                ((double) targetBackFireStrength * (double)strikeOnDefenseAgainstThatTypePercent ) / 100.0);
    }

    private int calcTargetStrikeOnDefenseSkillsPercent(HasCombatStrength attacker, HasCombatStrength target) {
        int value = 0;
        //for (AbstractSkill skill : target.getSkills()) {
        //    value += skill.getTargetStrikeOnDefensePercent(attacker, target);
        //}
        return value;
    }

    private int getGreatGeneralCount(HasCombatStrength unit) {
        Civilization civilization = unit.getCivilization();
        UnitList unitsAround = civilization.getUnitService().getUnitsAround(unit.getLocation(), 2);
        return unitsAround.getGreatGeneralCount();
    }
}
