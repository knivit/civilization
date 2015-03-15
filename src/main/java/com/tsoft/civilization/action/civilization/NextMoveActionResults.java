package com.tsoft.civilization.action.civilization;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.action.ActionSuccessResult;

public class NextMoveActionResults {
    public static final ActionSuccessResult CAN_GO_NEXT = new ActionSuccessResult(L10nCivilization.CAN_GO_NEXT);
    public static final ActionSuccessResult AWAITING_OTHERS_TO_MOVE = new ActionSuccessResult(L10nCivilization.AWAITING_OTHERS_TO_MOVE);
}
