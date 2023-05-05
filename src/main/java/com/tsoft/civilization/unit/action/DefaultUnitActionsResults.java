package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.unit.L10nUnit;

public final class DefaultUnitActionsResults {

    public static final ActionFailureResult UNIT_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult NO_PASS_SCORE = new ActionFailureResult(L10nUnit.NO_PASS_SCORE);
    public static final ActionFailureResult NOT_MY_TERRITORY = new ActionFailureResult(L10nUnit.NOT_MY_TERRITORY);

    private DefaultUnitActionsResults() { }
}
