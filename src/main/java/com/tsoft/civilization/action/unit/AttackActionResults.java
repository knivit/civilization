package com.tsoft.civilization.action.unit;

import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class AttackActionResults {
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
}
