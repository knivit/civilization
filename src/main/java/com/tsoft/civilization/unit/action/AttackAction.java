package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.combat.BaseCombatService;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.AbstractDir;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.event.Event;

import java.util.*;

public class AttackAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CAN_ATTACK = new ActionSuccessResult(L10nUnit.CAN_ATTACK);
    public static final ActionSuccessResult UNDERSHOOT = new ActionSuccessResult(L10nUnit.UNDERSHOOT);
    public static final ActionSuccessResult TARGET_DESTROYED = new ActionSuccessResult(L10nUnit.TARGET_DESTROYED);
    public static final ActionSuccessResult ATTACKED = new ActionSuccessResult(L10nUnit.ATTACKED);
    public static final ActionSuccessResult ATTACKER_IS_DESTROYED_DURING_ATTACK = new ActionSuccessResult(L10nUnit.ATTACKER_IS_DESTROYED_DURING_ATTACK);

    public static final ActionFailureResult NO_TARGETS_TO_ATTACK = new ActionFailureResult(L10nUnit.NO_TARGETS_TO_ATTACK);
    public static final ActionFailureResult MELEE_NOT_ENOUGH_PASS_SCORE = new ActionFailureResult(L10nUnit.MELEE_NOT_ENOUGH_PASS_SCORE);
    public static final ActionFailureResult ATTACKER_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult NOT_MILITARY_UNIT = new ActionFailureResult(L10nUnit.NOT_MILITARY_UNIT);
    public static final ActionFailureResult INVALID_LOCATION = new ActionFailureResult(L10nWorld.INVALID_LOCATION);

    private static final TileService tileService = new TileService();
    private static final BaseCombatService baseCombatService = new BaseCombatService();

    public static ActionAbstractResult attack(HasCombatStrength attacker, Point location) {
        ActionAbstractResult result = canAttack(attacker);
        if (result.isFail()) {
            return result;
        }

        if (location == null) {
            return INVALID_LOCATION;
        }

        HasCombatStrength target = getTargetToAttackAtLocation(attacker, location);
        if (target == null) {
            return NO_TARGETS_TO_ATTACK;
        }

        if (attacker.getUnitCategory().isRanged()) {
            return rangedAttack(attacker, target);
        }

        // For a melee unit, move to the target first, then attack
        return meleeAttack((AbstractUnit)attacker, target);
    }

    private static ActionAbstractResult canAttack(HasCombatStrength attacker) {
        if (attacker == null || attacker.isDestroyed()) {
            return ATTACKER_NOT_FOUND;
        }

        if (!attacker.getUnitCategory().isMilitary()) {
            return NOT_MILITARY_UNIT;
        }

        HasCombatStrengthList targets = getTargetsToAttack(attacker);
        if (targets.isEmpty()) {
            return NO_TARGETS_TO_ATTACK;
        }

        return CAN_ATTACK;
    }

    private static HasCombatStrength getTargetToAttackAtLocation(HasCombatStrength attacker, Point location) {
        Civilization civilization = attacker.getCivilization();

        // first, if there is a city then attack it
        City city = civilization.getWorld().getCityAtLocation(location);
        if (city != null) {
            return city;
        }

        // second, see is there a military unit
        UnitList foreignUnits = civilization.getWorld().getUnitsAtLocation(location, civilization);
        AbstractUnit militaryUnit = foreignUnits.findFirstMilitaryUnit();
        if (militaryUnit != null) {
            return militaryUnit;
        }

        return foreignUnits.getAny();
    }

    // No need to move, this is a ranged attack
    private static ActionAbstractResult rangedAttack(HasCombatStrength attacker, HasCombatStrength target) {
        int missileStrength = getMissileStrength(attacker, target);
        if (missileStrength <= 0) {
            return UNDERSHOOT;
        }

        return attackTarget(attacker, target, missileStrength);
    }

    private static int getMissileStrength(HasCombatStrength attacker, HasCombatStrength target) {
        List<Point> path = getMissilePath(attacker.getLocation(), target.getLocation());
        int rangedAttackStrength = attacker.getCombatStrength().getRangedAttackStrength();

        // add all bonuses
        int missileStrength = baseCombatService.calcStrikeStrength(attacker, rangedAttackStrength, target);

        for (Point loc : path) {
            AbstractTile tile = attacker.getCivilization().getTilesMap().getTile(loc);
            missileStrength -= tileService.getMissilePastCost(attacker, tile);
            if (missileStrength <= 0) {
                break;
            }
        }
        return missileStrength;
    }

    private static ActionAbstractResult meleeAttack(AbstractUnit attacker, HasCombatStrength target) {
        // target must be next to the attacker
        AbstractDir dir = attacker.getCivilization().getTilesMap().getDirToLocation(attacker.getLocation(), target.getLocation());
        if (dir == null) {
            return NO_TARGETS_TO_ATTACK;
        }

        // attacker must be able to pass to target's tile
        UnitMoveResult moveResult = MoveUnitAction.getMoveOnAttackResult(attacker, target.getLocation());
        if (moveResult.isFailed()) {
            return MELEE_NOT_ENOUGH_PASS_SCORE;
        }

        // calc the strength of the attack
        int meleeAttackStrength = attacker.getCombatStrength().getMeleeAttackStrength();
        int attackStrength = baseCombatService.calcStrikeStrength(attacker, meleeAttackStrength, target);

        return attackTarget(attacker, target, attackStrength);
    }

    private static ActionAbstractResult attackTarget(HasCombatStrength attacker, HasCombatStrength target, int strikeStrength) {
        World world = attacker.getCivilization().getWorld();
        int attackerStrength = attacker.getCombatStrength().getDefenseStrength();
        int targetStrength = target.getCombatStrength().getDefenseStrength();

        // before the attack calculate target's backfire strength
        int targetBackFireStrength = baseCombatService.calcTargetBackFireStrength(attacker, target);

        // decrease target's strength
        // take into account target's defense experience
        strikeStrength -= target.getCombatStrength().getDefenseExperience();
        if (strikeStrength <= 0) strikeStrength = 1;
        targetStrength -= strikeStrength;

        // a ranged attack can't destroy a city
        if (attacker.getUnitCategory().isRanged() && target.getUnitCategory().isCity()) {
            if (targetStrength <= 0) {
                targetStrength = 1;
            }
        }

        // see is the target destroyed
        boolean isTargetAlive;
        if (targetStrength <= 0) {
            destroyTarget(attacker, target);
            isTargetAlive = false;
        } else {
            target.addCombatStrength(CombatStrength.builder()
                .defenseStrength(targetStrength - target.getCombatStrength().getDefenseStrength())
                .defenseExperience(1)
                .build());
            isTargetAlive = true;
        }

        // the target backfired the attacker
        attackerStrength -= targetBackFireStrength;

        // fighting units can't be destroyed simultaneously
        if (attackerStrength <= 0 && !isTargetAlive) {
            attackerStrength = 1;
        }

        // see is the attacked destroyed
        boolean isAttackerAlive;
        if (attackerStrength <= 0) {
            attacker.destroyedBy(target, false);
            isAttackerAlive = false;
        } else {
            target.addCombatStrength(CombatStrength.builder()
                .defenseStrength(attackerStrength - target.getCombatStrength().getDefenseStrength())
                .attackExperience(2)
                .build());
            isAttackerAlive = true;
        }

        // set to 0 attacker's passScore - it can't action (move, attack, capture etc) anymore
        attacker.setPassScore(0);

        // send the event about the attack
        world.sendEvent(new Event(Event.UPDATE_WORLD + Event.UPDATE_STATUS_PANEL, attacker, L10nUnit.ATTACK_DONE_EVENT));

        // return the result
        if (isAttackerAlive && !isTargetAlive) {
            return TARGET_DESTROYED;
        }

        if (isAttackerAlive) {
            return ATTACKED;
        }

        return ATTACKER_IS_DESTROYED_DURING_ATTACK;
    }

    private static void destroyTarget(HasCombatStrength attacker, HasCombatStrength target) {
        Point location = target.getLocation();

        if (attacker.getUnitCategory().isRanged()) {
            // if the attacker is a ranged unit
            // the destroy the target only
            target.destroyedBy(attacker, false);
        } else {
            // if attacker is a melee unit
            // then destroy all units on that location
            target.destroyedBy(attacker, true);

            // second, move the attacker there
            ((AbstractUnit)attacker).moveTo(location);
        }
    }

    // Find out all targets to attack
    public static HasCombatStrengthList getTargetsToAttack(HasCombatStrength attacker) {
        if (attacker.getUnitCategory().isRanged()) {
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
            if (world.isWar(myCivilization, otherCivilization)) {
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
        World world = myCivilization.getWorld();

        for (HasCombatStrength target : targetsAround) {
            // check there is a war
            if (!world.isWar(myCivilization, target.getCivilization())) {
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

        CityList citiesAround = world.getCitiesAtLocations(locations, attacker.getCivilization());
        UnitList unitsAround = world.getUnitsAtLocations(locations, attacker.getCivilization());

        HasCombatStrengthList targets = new HasCombatStrengthList();
        targets.addAll(citiesAround);
        targets.addAll(unitsAround);

        return targets;
    }

    // Digital Differential Analyzer (DDA) algorithm for rasterization of lines
    // Point 'from' doesn't included
    private static List<Point> getMissilePath(Point from, Point to) {
        List<Point> path = new ArrayList<>();

        int ix1 = from.getX();
        int iy1 = from.getY();
        int ix2 = to.getX();
        int iy2 = to.getY();
        float len = Math.max(Math.abs(ix2 - ix1), Math.abs(iy2 - iy1));
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

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", getClientJSCode(attacker),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }

    private static StringBuilder getClientJSCode(HasCombatStrength attacker) {
        JsonBlock locations = new JsonBlock('\'');
        locations.startArray("locations");
        HasCombatStrengthList targets = getTargetsToAttack(attacker);
        for (HasCombatStrength target : targets) {
            JsonBlock locBlock = new JsonBlock('\'');
            locBlock.addParam("col", target.getLocation().getX());
            locBlock.addParam("row", target.getLocation().getY());
            locations.addElement(locBlock.getText());
        }
        locations.stopArray();

        if (attacker instanceof City) {
            return ClientAjaxRequest.cityAttackAction(attacker, locations);
        }

        return ClientAjaxRequest.unitAttackAction(attacker, locations);
    }
}
