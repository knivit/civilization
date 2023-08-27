package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.CivilizationMoveState;
import com.tsoft.civilization.civilization.Civilization;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class NextTurnAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult MOVE_DONE = new ActionSuccessResult(L10nCivilization.MOVE_DONE);
    public static final ActionSuccessResult NEXT_TURN = new ActionSuccessResult(L10nCivilization.NEXT_TURN);

    public static final ActionFailureResult NO_ACTIONS_AVAILABLE = new ActionFailureResult(L10nCivilization.NO_ACTIONS_AVAILABLE);

    public ActionAbstractResult nextTurn(Civilization civilization) {
        ActionAbstractResult result = canNextTurn(civilization);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        civilization.stopYear();

        return MOVE_DONE;
    }

    public ActionAbstractResult canNextTurn(Civilization civilization) {
        if (civilization.getCivilizationMoveState() == CivilizationMoveState.DONE) {
            return NO_ACTIONS_AVAILABLE;
        }

        return NEXT_TURN;
    }
}
