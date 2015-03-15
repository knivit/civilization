package com.tsoft.civilization.action.unit;

import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class MoveUnitActionResults {
    public static final ActionSuccessResult UNIT_MOVED = new ActionSuccessResult(L10nUnit.UNIT_MOVED);
    public static final ActionSuccessResult CAN_MOVE = new ActionSuccessResult(L10nUnit.CAN_MOVE);

    public static final ActionFailureResult UNIT_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult INVALID_LOCATION = new ActionFailureResult(L10nUnit.INVALID_LOCATION);
    public static final ActionFailureResult NO_LOCATIONS_TO_MOVE = new ActionFailureResult(L10nUnit.NO_LOCATIONS_TO_MOVE);
}
