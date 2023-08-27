package com.tsoft.civilization.civilization.ui;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.civilization.action.NextTurnAction;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NextTurnUi {

    private final NextTurnAction nextTurnAction;

    public static NextTurnUi newInstance() {
        return new NextTurnUi(new NextTurnAction());
    }

    private static String getLocalizedName() {
        return L10nCivilization.NEXT_TURN.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nCivilization.NEXT_MOVE_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(Civilization civilization) {
        if (nextTurnAction.canNextTurn(civilization).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td>
            """,

            "$buttonOnClick", ClientAjaxRequest.nextTurnAction(),
            "$buttonLabel", getLocalizedName());
    }
}
