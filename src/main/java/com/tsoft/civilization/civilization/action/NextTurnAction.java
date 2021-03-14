package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.MoveState;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.civilization.Civilization;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class NextTurnAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult MOVE_DONE = new ActionSuccessResult(L10nCivilization.MOVE_DONE);
    public static final ActionSuccessResult NEXT_TURN = new ActionSuccessResult(L10nCivilization.NEXT_TURN);

    public static final ActionFailureResult NO_ACTIONS_AVAILABLE = new ActionFailureResult(L10nCivilization.NO_ACTIONS_AVAILABLE);

    public static ActionAbstractResult nextTurn(Civilization civilization) {
        ActionAbstractResult result = canNextTurn(civilization);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        civilization.stopYear();
        return MOVE_DONE;
    }

    private static ActionAbstractResult canNextTurn(Civilization civilization) {
        if (civilization.getMoveState() == MoveState.DONE) {
            return NO_ACTIONS_AVAILABLE;
        }

        return NEXT_TURN;
    }

    private static String getClientJSCode() {
        return "client.nextTurnAction()";
    }

    private static String getLocalizedName() {
        return L10nCivilization.NEXT_TURN.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nCivilization.NEXT_MOVE_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(Civilization civilization) {
        if (canNextTurn(civilization).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td>
            """,

            "$buttonOnClick", getClientJSCode(),
            "$buttonLabel", getLocalizedName());
    }
}
