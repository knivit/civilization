package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.population.Happiness;
import com.tsoft.civilization.combat.CombatResult;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.combat.event.AttackDoneEvent;
import com.tsoft.civilization.combat.event.UnitHasWonAttackEvent;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.service.move.MoveUnitService;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;

import java.util.Collection;

public class BaseCombatService {

    private static final MoveUnitService moveService = new MoveUnitService();

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
        //
        // PREPARE THE ATTACK
        //
        CombatResult.CombatResultBuilder result = CombatResult.builder();
        result.originalStrikeStrength(strikeStrength);

        World world = attacker.getCivilization().getWorld();

        int targetDefenseStrength = target.getCombatStrength().getDefenseStrength();
        result.targetDefenseStrength(targetDefenseStrength);

        // before the attack calculate target's backfire strength
        int targetBackFireStrength = calcTargetBackFireStrength(attacker, target);
        result.targetBackFireStrength(targetBackFireStrength);

        // get target's defense experience
        int targetDefenseExperience = target.getCombatStrength().getDefenseExperience();
        result.targetDefenseExperience(targetDefenseExperience);

        // attacker's strike will be discounted on target's defense experience
        strikeStrength -= targetDefenseExperience;
        if (strikeStrength <= 0) strikeStrength = 1;
        result.appliedStrikeStrength(strikeStrength);

        //
        // ATTACK
        //

        // decrease target's defense strength
        targetDefenseStrength -= strikeStrength;

        // a ranged attack can't destroy a city
        if (attacker.getUnitCategory().isRanged() && target.getUnitCategory().isCity()) {
            if (targetDefenseStrength <= 0) {
                targetDefenseStrength = 1;
            }
        }
        result.targetDefenseStrengthAfterAttack(targetDefenseStrength);

        // increase target's defense experience after the attack
        int targetDefenseExperienceAfterAttack = targetDefenseExperience + 1;
        result.targetDefenseExperienceAfterAttack(targetDefenseExperienceAfterAttack);

        // update target's combat strength after the attack
        target.setCombatStrength(target.getCombatStrength().copy()
            .defenseStrength(targetDefenseStrength)
            .defenseExperience(targetDefenseExperienceAfterAttack)
            .build());

        boolean targetDestroyed = (targetDefenseStrength <= 0);
        result.targetDestroyed(targetDestroyed);

        if (targetDestroyed) {
            destroy(attacker, target);
        }

        //
        // BACKFIRE
        //

        // the target backfired the attacker
        int attackerDefenseStrength = attacker.getCombatStrength().getDefenseStrength();
        result.attackerDefenseStrength(attackerDefenseStrength);

        int attackerDefenseStrengthAfterAttack = attackerDefenseStrength - targetBackFireStrength;

        // fighting units can't be destroyed simultaneously
        if (attackerDefenseStrengthAfterAttack <= 0 && targetDestroyed) {
            attackerDefenseStrengthAfterAttack = 1;
        }
        result.attackerDefenseStrengthAfterAttack(attackerDefenseStrengthAfterAttack);

        // attacker's attack experience increase
        int attackerAttackExperience = attacker.getCombatStrength().getAttackExperience();
        result.attackerAttackExperience(attackerAttackExperience);

        int attackerAttackExperienceAfterAttack = attackerAttackExperience + 2;
        result.attackerAttackExperienceAfterAttack(attackerAttackExperienceAfterAttack);

        // update attacker's combat strength after the attack
        attacker.setCombatStrength(attacker.getCombatStrength().copy()
            .defenseStrength(attackerDefenseStrengthAfterAttack)
            .attackExperience(attackerAttackExperienceAfterAttack)
            .build());

        // is the attacker destroyed
        boolean attackerDestroyed = (attackerDefenseStrengthAfterAttack <= 0);
        result.attackerDestroyed(attackerDestroyed);

        if (attackerDestroyed) {
            destroy(target, attacker);
        }

        // set to 0 attacker's passScore - it can't action (move, attack, capture etc) anymore
        attacker.setPassScore(0);
        result.done(true);

        // send the event about the attack
        world.sendEvent(AttackDoneEvent.builder()
            .build());

        // return the result
        return result.build();
    }

    private void destroyUnit(HasCombatStrength attacker, HasCombatStrength victim) {
        Point location = victim.getLocation();

        if (attacker.getUnitCategory().isRanged()) {
            // if the attacker is a ranged unit
            // the destroy the target only
            destroy(attacker, victim);
        } else {
            // if attacker is a melee unit
            // then destroy all units on that location
            destroyAllUnitsAtLocation(attacker, victim.getLocation());

            // move the attacker there
            moveService.moveUnit((AbstractUnit) attacker, location);
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

        // A city may be destroyed only during a melee attack
        if (victim instanceof City) {
            throw new IllegalArgumentException("FIXME: find out the calling method and use CaptureService#captureCity(); A city may be destroyed only during a melee attack");
        } else {
            Civilization loser = victim.getCivilization();
            loser.getUnitService().destroyUnit((AbstractUnit) victim);
        }
    }

    /** Calc the strike strength */
    int calcStrikeStrength(HasCombatStrength attacker, int strikeStrength, HasCombatStrength target) {
        int strikeSkillsPercent = calcAttackerStrikeSkillsPercent(attacker, target);
        int strikeGeneralsPercent = getGreatGeneralCount(attacker) * 33;
        int strikeTerrainPercent = 0;
        int strikeAgainstThatTypePercent = 0;
        int strikeHappinessPercent = calcAttackerStrikeHappinessPercent(attacker);

        return strikeStrength + attacker.getCombatStrength().getAttackExperience() +
            (int)Math.round(((double)strikeStrength * (double)strikeSkillsPercent ) / 100.0) +
            (int)Math.round(((double)strikeStrength * (double)strikeGeneralsPercent ) / 100.0) +
            (int)Math.round(((double)strikeStrength * (double)strikeTerrainPercent ) / 100.0) +
            (int)Math.round(((double)strikeStrength * (double)strikeAgainstThatTypePercent ) / 100.0) +
            (int)Math.round(((double)strikeStrength * (double)strikeHappinessPercent ) / 100.0);
    }

    private int calcAttackerStrikeSkillsPercent(HasCombatStrength attacker, HasCombatStrength target) {
        int value = 0;
        for (AbstractSkill skill : attacker.getSkills()) {
            value += skill.getAttackStrikePercent(attacker, target);
        }
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
        int strikeOnDefenseSkillPercent = calcTargetStrikeOnDefenseSkillsPercent(attacker, target);
        int strikeOnDefenseGeneralsPercent = getGreatGeneralCount(attacker) * 10;
        int strikeOnDefenseTerrainPercent = 0;
        int strikeOnDefenseAgainstThatTypePercent = 0;

        int targetBackFireStrength = target.getCombatStrength().getTargetBackFireStrength();
        return targetBackFireStrength +
            (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseSkillPercent ) / 100.0) +
            (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseGeneralsPercent ) / 100.0) +
            (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseTerrainPercent ) / 100.0) +
            (int)Math.round(((double) targetBackFireStrength * (double)strikeOnDefenseAgainstThatTypePercent ) / 100.0);
    }

    private int calcTargetStrikeOnDefenseSkillsPercent(HasCombatStrength attacker, HasCombatStrength target) {
        int value = 0;
        for (AbstractSkill skill : target.getSkills()) {
            value += skill.getTargetStrikeOnDefensePercent(attacker, target);
        }
        return value;
    }

    private int getGreatGeneralCount(HasCombatStrength unit) {
        Civilization civilization = unit.getCivilization();
        UnitList unitsAround = civilization.getUnitService().getUnitsAround(unit.getLocation(), 2);
        return unitsAround.getGreatGeneralCount();
    }
}
