package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.combat.*;
import com.tsoft.civilization.unit.L10nUnit;

public class PillageService {

    public static final ActionSuccessResult CAN_PILLAGE = new ActionSuccessResult(L10nCombat.CAN_PILLAGE);
    public static final ActionSuccessResult TARGET_PILLAGED = new ActionSuccessResult(L10nCombat.TARGET_PILLAGED);

    public static final ActionFailureResult PILLAGE_FAILED = new ActionFailureResult(L10nCombat.PILLAGE_FAILED);
    public static final ActionFailureResult NO_TARGETS_TO_PILLAGE = new ActionFailureResult(L10nCombat.NO_TARGETS_TO_PILLAGE);
    public static final ActionFailureResult ATTACKER_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult NOT_MILITARY_UNIT = new ActionFailureResult(L10nUnit.NOT_MILITARY_UNIT);

    private static final PillageCombatService pillageCombatService = new PillageCombatService();


    public ActionAbstractResult pillage(HasCombatStrength attacker) {
        ActionAbstractResult result = canPillage(attacker);
        if (result.isFail()) {
            return result;
        }

        CombatResult combatResult = pillageCombatService.attack(attacker);
        if (CombatStatus.FAILED_NO_TARGETS.equals(combatResult.getStatus())) {
            return NO_TARGETS_TO_PILLAGE;
        }

        if (!CombatStatus.DONE.equals(combatResult.getStatus())) {
            return PILLAGE_FAILED;
        }

        return TARGET_PILLAGED;
    }

    public ActionAbstractResult canPillage(HasCombatStrength attacker) {
        if (attacker == null || attacker.isDestroyed()) {
            return ATTACKER_NOT_FOUND;
        }

        if (!attacker.getUnitCategory().isMilitary()) {
            return NOT_MILITARY_UNIT;
        }

        HasCombatStrengthList targets = pillageCombatService.getTargetsToPillage(attacker);
        if (targets == null || targets.isEmpty()) {
            return NO_TARGETS_TO_PILLAGE;
        }

        return CAN_PILLAGE;
    }

    public HasCombatStrengthList getTargetsToPillage(HasCombatStrength attacker) {
        return pillageCombatService.getTargetsToPillage(attacker);
    }
}
