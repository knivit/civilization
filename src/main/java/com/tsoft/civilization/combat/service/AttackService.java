package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.*;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;

public class AttackService {

    public static final ActionSuccessResult CAN_ATTACK = new ActionSuccessResult(L10nCombat.CAN_ATTACK);
    public static final ActionSuccessResult RANGED_UNDERSHOOT = new ActionSuccessResult(L10nCombat.RANGED_UNDERSHOOT);
    public static final ActionSuccessResult TARGET_DESTROYED = new ActionSuccessResult(L10nCombat.TARGET_DESTROYED);
    public static final ActionSuccessResult ATTACKED = new ActionSuccessResult(L10nCombat.ATTACKED);
    public static final ActionSuccessResult ATTACKER_IS_DESTROYED_DURING_ATTACK = new ActionSuccessResult(L10nCombat.ATTACKER_IS_DESTROYED_DURING_ATTACK);

    public static final ActionFailureResult ATTACK_FAILED = new ActionFailureResult(L10nCombat.ATTACK_FAILED);
    public static final ActionFailureResult NO_TARGETS_TO_ATTACK = new ActionFailureResult(L10nCombat.NO_TARGETS_TO_ATTACK);
    public static final ActionFailureResult MELEE_NOT_ENOUGH_PASS_SCORE = new ActionFailureResult(L10nUnit.MELEE_NOT_ENOUGH_PASS_SCORE);
    public static final ActionFailureResult ATTACKER_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult NOT_MILITARY_UNIT = new ActionFailureResult(L10nUnit.NOT_MILITARY_UNIT);
    public static final ActionFailureResult INVALID_TARGET_LOCATION = new ActionFailureResult(L10nCombat.INVALID_TARGET_LOCATION);

    private static final MeleeCombatService meleeCombatService = new MeleeCombatService();
    private static final RangedCombatService rangedCombatService = new RangedCombatService();
    private static final ImprovementCombatService improvementCombatService = new ImprovementCombatService();
    private static final ResourceCombatService resourceCombatService = new ResourceCombatService();

    public ActionAbstractResult attack(HasCombatStrength attacker, Point location) {
        ActionAbstractResult result = canAttack(attacker);
        if (result.isFail()) {
            return result;
        }

        if (location == null || location.equals(attacker.getLocation())) {
            return INVALID_TARGET_LOCATION;
        }

        HasCombatStrength target = getTargetToAttackAtLocation(attacker, location);
        if (target == null) {
            return NO_TARGETS_TO_ATTACK;
        }

        CombatResult combatResult = attackTarget(attacker, target);
        if (CombatStatus.FAILED_NO_TARGETS.equals(combatResult.getStatus())) {
            return NO_TARGETS_TO_ATTACK;
        }

        if (CombatStatus.FAILED_MELEE_NOT_ENOUGH_PASS_SCORE.equals(combatResult.getStatus())) {
            return MELEE_NOT_ENOUGH_PASS_SCORE;
        }

        if (CombatStatus.FAILED_RANGED_UNDERSHOOT.equals(combatResult.getStatus())) {
            return RANGED_UNDERSHOOT;
        }

        if (!CombatStatus.DONE.equals(combatResult.getStatus())) {
            return ATTACK_FAILED;
        }

        if (combatResult.getTargetState().isDestroyed()) {
            return TARGET_DESTROYED;
        }

        if (combatResult.getAttackerState().isDestroyed()) {
            return ATTACKER_IS_DESTROYED_DURING_ATTACK;
        }

        return ATTACKED;
    }

    CombatResult attackTarget(HasCombatStrength attacker, HasCombatStrength target) {
        if (attacker.getUnitCategory().isRanged()) {
            return rangedCombatService.attack(attacker, target);
        }

        // For a melee unit, move to the target first, then attack
        return meleeCombatService.attack(attacker, target);
    }

    CombatResult attackImprovement(HasCombatStrength attacker, AbstractImprovement improvement) {
        return improvementCombatService.attack(attacker, improvement);
    }

    CombatResult attackResource(HasCombatStrength attacker, AbstractResource resource) {
        return resourceCombatService.attack(attacker, resource);
    }

    public ActionAbstractResult canAttack(HasCombatStrength attacker) {
        if (attacker == null || attacker.isDestroyed()) {
            return ATTACKER_NOT_FOUND;
        }

        if (!attacker.getUnitCategory().isMilitary()) {
            return NOT_MILITARY_UNIT;
        }

        HasCombatStrengthList targets = getTargetsToAttack(attacker);
        if (targets == null || targets.isEmpty()) {
            return NO_TARGETS_TO_ATTACK;
        }

        return CAN_ATTACK;
    }

    // Find out all targets to attack
    public HasCombatStrengthList getTargetsToAttack(HasCombatStrength attacker) {
        if (attacker.getUnitCategory().isRanged()) {
            return rangedCombatService.getTargetsToAttack(attacker);
        }

        // All the foreign units, cities are targets to attack
        // For a melee attack targets must be located next to the attacker
        return meleeCombatService.getTargetsToAttack(attacker);
    }

    private HasCombatStrength getTargetToAttackAtLocation(HasCombatStrength attacker, Point location) {
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

        // no military enemy, get any civilian
        return foreignUnits.getAny();
    }
}
