package com.tsoft.civilization.action.civilization;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.world.Civilization;

import java.util.UUID;

public class NextMoveAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult nextMove(Civilization civilization) {
        ActionAbstractResult result = canNextMove();
        if (result.isFail()) {
            return result;
        }

        if (!civilization.nextMove()) {
            return NextMoveActionResults.AWAITING_OTHERS_TO_MOVE;
        }
        return NextMoveActionResults.CAN_GO_NEXT;
    }

    private static ActionAbstractResult canNextMove() {
        return NextMoveActionResults.CAN_GO_NEXT;
    }

    private static String getClientJSCode() {
        return "client.nextMoveAction()";
    }

    private static String getLocalizedName() {
        return L10nCivilization.NEXT_MOVE.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nCivilization.NEXT_MOVE_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(Civilization civilization) {
        if (canNextMove().isFail()) {
            return null;
        }

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td>",

            "$buttonOnClick", getClientJSCode(),
            "$buttonLabel", getLocalizedName());
    }
}
