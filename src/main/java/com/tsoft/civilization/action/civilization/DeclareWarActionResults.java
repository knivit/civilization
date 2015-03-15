package com.tsoft.civilization.action.civilization;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class DeclareWarActionResults {
    public static final ActionSuccessResult CAN_DECLARE_WAR = new ActionSuccessResult(L10nCivilization.CAN_DECLARE_WAR);

    public static final ActionFailureResult WRONG_CIVILIZATION = new ActionFailureResult(L10nCivilization.WRONG_CIVILIZATION);
    public static final ActionFailureResult ALREADY_WAR = new ActionFailureResult(L10nCivilization.ALREADY_WAR);
}
