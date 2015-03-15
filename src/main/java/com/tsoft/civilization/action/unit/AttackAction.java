package com.tsoft.civilization.action.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.util.AbstractDir;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JSONBlock;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.util.Event;

import java.util.*;

public class AttackAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult attack(HasCombatStrength attacker, Point location) {
        ActionAbstractResult result = canAttack(attacker);
        if (result.isFail()) {
            return result;
        }

        if (location == null) {
            return AttackActionResults.INVALID_LOCATION;
        }

        HasCombatStrength target = getTargetToAttackAtLocation(attacker, location);
        if (target == null) {
            return AttackActionResults.NO_TARGETS_TO_ATTACK;
        }

        if (attacker.getUnitType().isRanged()) {
            return rangedAttack(attacker, target);
        }

        // For a melee unit, move to the target first, then attack
        return meleeAttack(attacker, target);
    }

    private static ActionAbstractResult canAttack(HasCombatStrength attacker) {
        if (attacker == null || attacker.isDestroyed()) {
            return AttackActionResults.ATTACKER_NOT_FOUND;
        }

        if (!attacker.getUnitType().isMilitary()) {
            return AttackActionResults.NOT_MILITARY_UNIT;
        }

        HasCombatStrengthList targets = getTargetsToAttack(attacker);
        if (targets.isEmpty()) {
            return AttackActionResults.NO_TARGETS_TO_ATTACK;
        }

        return AttackActionResults.CAN_ATTACK;
    }

    private static HasCombatStrength getTargetToAttackAtLocation(HasCombatStrength attacker, Point location) {
        Civilization civilization = attacker.getCivilization();

        // first, is there a city then attack it
        City city = civilization.getWorld().getCityAtLocation(location);
        if (city != null) {
            return city;
        }

        // second, see is there a military unit
        UnitCollection foreignUnits = civilization.getWorld().getUnitsAtLocation(location, civilization);
        AbstractUnit militaryUnit = foreignUnits.findMilitaryUnit();
        if (militaryUnit != null) {
            return militaryUnit;
        }

        return foreignUnits.get(0);
    }

    // No need to move, this is a ranged attack
    private static ActionAbstractResult rangedAttack(HasCombatStrength attacker, HasCombatStrength target) {
        int missileStrength = getMissileStrength(attacker, target);
        if (missileStrength <= 0) {
            return AttackActionResults.UNDERSHOOT;
        }

        return attackTarget(attacker, target, missileStrength);
    }

    private static int getMissileStrength(HasCombatStrength attacker, HasCombatStrength target) {
        List<Point> path = getMissilePath(attacker.getLocation(), target.getLocation());
        int rangedAttackStrength = attacker.getCombatStrength().getRangedAttackStrength();

        // add all bonuses
        int missileStrength = attacker.getCombatStrength().getStrikeStrength(rangedAttackStrength, target);

        for (Point loc : path) {
            AbstractTile tile = attacker.getCivilization().getTilesMap().getTile(loc);
            missileStrength -= tile.getMissilePastCost(attacker);
            if (missileStrength <= 0) {
                break;
            }
        }
        return missileStrength;
    }

    private static ActionAbstractResult meleeAttack(HasCombatStrength attacker, HasCombatStrength target) {
        // target must be next to the attacker
        AbstractDir dir = attacker.getCivilization().getTilesMap().getDirToLocation(attacker.getLocation(), target.getLocation());
        if (dir == null) {
            return AttackActionResults.NO_TARGETS_TO_ATTACK;
        }

        // attacker must be able to pass to target's tile
        UnitMoveResult moveResult = MoveUnitAction.getMoveOnAttackResult((AbstractUnit)attacker, target.getLocation());
        if (moveResult.isFailed()) {
            return AttackActionResults.MELEE_NOT_ENOUGH_PASS_SCORE;
        }

        // calc the strength of the attack
        int meleeAttackStrength = attacker.getCombatStrength().getMeleeAttackStrength();
        int attackStrength = attacker.getCombatStrength().getStrikeStrength(meleeAttackStrength, target);

        return attackTarget(attacker, target, attackStrength);
    }

    private static ActionAbstractResult attackTarget(HasCombatStrength attacker, HasCombatStrength target, int strikeStrength) {
        World world = attacker.getCivilization().getWorld();
        int unitStrength = attacker.getCombatStrength().getStrength();
        int targetStrength = target.getCombatStrength().getStrength();
        int targetBackFireStrength = attacker.getCombatStrength().getTargetBackFireStrength(target);

        // decrease target's strength
        // take into account target's defense experience
        strikeStrength -= target.getCombatStrength().getDefenseExperience();
        if (strikeStrength <= 0) strikeStrength = 1;

        boolean isTargetAlive= false;
        targetStrength -= strikeStrength;

        // a ranged attack can't destroy a city
        if (attacker.getUnitType().isRanged() && target.getUnitType().isCity()) {
            if (targetStrength <= 0) {
                targetStrength = 1;
            }
        }

        if (targetStrength <= 0) {
            Point location = target.getLocation();

            if (attacker.getUnitType().isRanged()) {
                // if the attacker is a ranged unit
                // the destroy the target only
                target.destroyBy(attacker, false);
            } else {
                // if attacker is a melee unit
                // then destroy all units on that location
                target.destroyBy(attacker, true);

                // second, move the attacker there
                ((AbstractUnit)attacker).moveTo(location);
            }
        } else {
            target.getCombatStrength().setStrength(targetStrength);
            target.getCombatStrength().addDefenseExperience(1);
            isTargetAlive = true;
        }

        // is the attacker was destroyed during the attack ?
        boolean isAttackerAlive = false;
        unitStrength -= targetBackFireStrength;

        // fighting units can't be destroyed simultaneously
        if (unitStrength <= 0 && !isTargetAlive) {
            unitStrength = 1;
        }

        if (unitStrength <= 0) {
            attacker.destroyBy(target, false);
        } else {
            attacker.getCombatStrength().setStrength(unitStrength);
            attacker.getCombatStrength().addAttackExperience(2);
            isAttackerAlive = true;
        }

        // set to 0 attacker's passScore - it can't action (move, attack, capture etc) anymore
        attacker.setPassScore(0);

        // send the event about the attack
        world.sendEvent(new Event(attacker, L10nUnit.ATTACK_DONE_EVENT, Event.UPDATE_WORLD + Event.UPDATE_STATUS_PANEL));

        // return the result
        if (isAttackerAlive && !isTargetAlive) {
            return AttackActionResults.TARGET_DESTROYED;
        }

        if (isAttackerAlive) {
            return AttackActionResults.ATTACKED;
        }

        return AttackActionResults.ATTACKER_IS_DESTROYED_DURING_ATTACK;
    }

    // Find out all targets to attack
    public static HasCombatStrengthList getTargetsToAttack(HasCombatStrength attacker) {
        if (attacker.getUnitType().isRanged()) {
            return getTargetsToRangedAttack(attacker);
        }

        // All the foreign units, cities are targets to attack
        // For a melee attack targets must be located next to the attacker
        return getTargetsToMeleeAttack(attacker);
    }

    // Get all targets where the unit's missile can reach (there can be more than one targets on a tile)
    private static HasCombatStrengthList getTargetsToRangedAttack(HasCombatStrength attacker) {
        HasCombatStrengthList targets = new HasCombatStrengthList();

        // Add foreign units/cities if its civilization is in war with our
        int attackRadius = attacker.getCombatStrength().getRangedAttackRadius();
        HasCombatStrengthList targetsAround = getPossibleTargetsAround(attacker, attackRadius);
        Civilization myCivilization = attacker.getCivilization();
        World world = myCivilization.getWorld();
        for (HasCombatStrength target : targetsAround) {
            Civilization otherCivilization = target.getCivilization();
            if (!myCivilization.equals(otherCivilization) && world.isWar(myCivilization, otherCivilization)) {
                int missileStrength = getMissileStrength(attacker, target);
                if (missileStrength > 0) {
                    targets.add(target);
                }
            }
        }

        return targets;
    }

    // Get all targets to fight (there can be more than one targets on a tile)
    private static HasCombatStrengthList getTargetsToMeleeAttack(HasCombatStrength attacker) {
        HasCombatStrengthList targets = new HasCombatStrengthList();

        // Add foreign units and cities if its civilization is in war with our
        List<HasCombatStrength> targetsAround = getPossibleTargetsAround(attacker, 1);
        Civilization myCivilization = attacker.getCivilization();
        for (HasCombatStrength target : targetsAround) {
            // check there is a war
            if (!attacker.getCivilization().getWorld().isWar(myCivilization, target.getCivilization())) {
                continue;
            }

            // check we can move to the location
            UnitMoveResult moveResult = MoveUnitAction.getMoveOnAttackResult((AbstractUnit)attacker, target.getLocation());
            if (moveResult.isFailed()) {
                continue;
            }

            targets.add(target);
        }

        return targets;
    }

    private static HasCombatStrengthList getPossibleTargetsAround(HasCombatStrength attacker, int radius) {
        World world = attacker.getCivilization().getWorld();
        Collection<Point> locations = world.getLocationsAround(attacker.getLocation(), radius);

        CityCollection citiesAround = world.getCitiesAtLocations(locations, attacker.getCivilization());
        UnitCollection unitsAround = world.getUnitsAtLocations(locations, attacker.getCivilization());

        HasCombatStrengthList targets = new HasCombatStrengthList();
        targets.addAll(citiesAround);
        targets.addAll(unitsAround);

        return targets;
    }

    // Digital Differential Analyzer (DDA) algorithm for rasterization of lines
    // Point 'from' doesn't included
    private static List<Point> getMissilePath(Point from, Point to) {
        List<Point> path = new ArrayList<Point>();

        int ix1 = from.getX();
        int iy1 = from.getY();
        int ix2 = to.getX();
        int iy2 = to.getY();
        int len = Math.max(Math.abs(ix2 - ix1), Math.abs(iy2 - iy1));
        float dX = (ix2 - ix1) / len;
        float dY = (iy2 - iy1) / len;

        float x = ix1, y = iy1;
        for (int i = 0; i < len; i ++) {
            x += dX;
            y += dY;
            path.add(new Point(Math.round(x), Math.round(y)));
        }
        path.add(to);
        return path;
    }

    private static String getClientJSCode(HasCombatStrength attacker) {
        JSONBlock block = new JSONBlock('\'');
        block.startArray("locations");
        HasCombatStrengthList targets = getTargetsToAttack(attacker);
        for (HasCombatStrength target : targets) {
            JSONBlock locBlock = new JSONBlock('\'');
            locBlock.addParam("col", target.getLocation().getX());
            locBlock.addParam("row", target.getLocation().getY());
            block.addElement(locBlock.getText());
        }
        block.stopArray();

        if (attacker instanceof City) {
            return String.format("client.cityAttackAction({ attacker:'%1$s', ucol:'%2$s', urow:'%3$s', %4$s })",
                    attacker.getId(), attacker.getLocation().getX(), attacker.getLocation().getY(), block.getValue());
        }

        return String.format("client.unitAttackAction({ attacker:'%1$s', ucol:'%2$s', urow:'%3$s', %4$s })",
                attacker.getId(), attacker.getLocation().getX(), attacker.getLocation().getY(), block.getValue());
    }

    private static String getLocalizedName() {
        return L10nUnit.ATTACK_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.ATTACK_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(HasCombatStrength attacker) {
        if (canAttack(attacker).isFail()) {
            return null;
        }

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td><td>$actionDescription</td>",

            "$buttonOnClick", getClientJSCode(attacker),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
