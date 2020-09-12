package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class DestroyUnitResults {
    public static final ActionSuccessResult UNIT_DESTROYED = new ActionSuccessResult(L10nUnit.UNIT_DESTROYED);
    public static final ActionSuccessResult CAN_DESTROY_UNIT = new ActionSuccessResult(L10nUnit.CAN_DESTROY_UNIT);

    public static final ActionFailureResult UNIT_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult NO_PASS_SCORE = new ActionFailureResult(L10nUnit.NO_PASS_SCORE);
    public static final ActionFailureResult LAST_SETTLERS_CANT_BE_DESTOYED = new ActionFailureResult(L10nUnit.LAST_SETTLERS_CANT_BE_DESTROYED);
}
