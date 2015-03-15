package com.tsoft.civilization.action.unit;

import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class CaptureUnitActionResults {
    public static final ActionSuccessResult CAN_CAPTURE = new ActionSuccessResult(L10nUnit.CAN_CAPTURE);
    public static final ActionSuccessResult FOREIGN_UNIT_CAPTURED = new ActionSuccessResult(L10nUnit.FOREIGN_UNIT_CAPTURED);

    public static final ActionFailureResult NO_LOCATIONS_TO_CAPTURE = new ActionFailureResult(L10nUnit.NO_LOCATIONS_TO_CAPTURE);
    public static final ActionFailureResult NOTHING_TO_CAPTURE = new ActionFailureResult(L10nUnit.NOTHING_TO_CAPTURE);
    public static final ActionFailureResult ATTACKER_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult INVALID_LOCATION = new ActionFailureResult(L10nWorld.INVALID_LOCATION);
}
