package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.combat.*;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.JsonBlock;

import java.util.*;

public class AttackAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CAN_ATTACK = new ActionSuccessResult(L10nUnit.CAN_ATTACK);
    public static final ActionSuccessResult RANGED_UNDERSHOOT = new ActionSuccessResult(L10nUnit.RANGED_UNDERSHOOT);
    public static final ActionSuccessResult TARGET_DESTROYED = new ActionSuccessResult(L10nUnit.TARGET_DESTROYED);
    public static final ActionSuccessResult ATTACKED = new ActionSuccessResult(L10nUnit.ATTACKED);
    public static final ActionSuccessResult ATTACKER_IS_DESTROYED_DURING_ATTACK = new ActionSuccessResult(L10nUnit.ATTACKER_IS_DESTROYED_DURING_ATTACK);

    public static final ActionFailureResult ATTACK_SKIPPED = new ActionFailureResult(L10nUnit.ATTACK_SKIPPED);
    public static final ActionFailureResult NO_TARGETS_TO_ATTACK = new ActionFailureResult(L10nUnit.NO_TARGETS_TO_ATTACK);
    public static final ActionFailureResult MELEE_NOT_ENOUGH_PASS_SCORE = new ActionFailureResult(L10nUnit.MELEE_NOT_ENOUGH_PASS_SCORE);
    public static final ActionFailureResult ATTACKER_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult NOT_MILITARY_UNIT = new ActionFailureResult(L10nUnit.NOT_MILITARY_UNIT);
    public static final ActionFailureResult INVALID_LOCATION = new ActionFailureResult(L10nWorld.INVALID_LOCATION);

    private final CombatService combatService;

    public AttackAction(CombatService combatService) {
        this.combatService = combatService;
    }

    public ActionAbstractResult attack(HasCombatStrength attacker, Point location) {
        ActionAbstractResult result = canAttack(attacker);
        if (result.isFail()) {
            return result;
        }

        if (location == null) {
            return INVALID_LOCATION;
        }

        HasCombatStrength target = combatService.getTargetToAttackAtLocation(attacker, location);
        if (target == null) {
            return NO_TARGETS_TO_ATTACK;
        }

        CombatResult combatResult = combatService.attack(attacker, target);
        if (combatResult.isSkippedAsNoTargetsToAttack()) {
            return NO_TARGETS_TO_ATTACK;
        }

        if (combatResult.isSkippedAsMeleeNotEnoughPassScore()) {
            return MELEE_NOT_ENOUGH_PASS_SCORE;
        }

        if (combatResult.isSkippedAsRangedUndershoot()) {
            return RANGED_UNDERSHOOT;
        }

        if (!combatResult.isDone()) {
            return ATTACK_SKIPPED;
        }

        if (combatResult.isTargetDestroyed()) {
            return TARGET_DESTROYED;
        }

        if (combatResult.isAttackerDestroyed()) {
            return ATTACKER_IS_DESTROYED_DURING_ATTACK;
        }

        return ATTACKED;
    }

    private ActionAbstractResult canAttack(HasCombatStrength attacker) {
        if (attacker == null || attacker.isDestroyed()) {
            return ATTACKER_NOT_FOUND;
        }

        if (!attacker.getUnitCategory().isMilitary()) {
            return NOT_MILITARY_UNIT;
        }

        HasCombatStrengthList targets = combatService.getTargetsToAttack(attacker);
        if (targets == null || targets.isEmpty()) {
            return NO_TARGETS_TO_ATTACK;
        }

        return CAN_ATTACK;
    }

    private String getLocalizedName() {
        return L10nUnit.ATTACK_NAME.getLocalized();
    }

    private String getLocalizedDescription() {
        return L10nUnit.ATTACK_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(HasCombatStrength attacker) {
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

    private StringBuilder getClientJSCode(HasCombatStrength attacker) {
        JsonBlock locations = new JsonBlock('\'');
        locations.startArray("locations");
        HasCombatStrengthList targets = combatService.getTargetsToAttack(attacker);
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
