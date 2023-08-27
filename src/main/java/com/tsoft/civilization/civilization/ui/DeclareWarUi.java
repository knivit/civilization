package com.tsoft.civilization.civilization.ui;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.L10nCivilization;
import com.tsoft.civilization.civilization.action.DeclareWarAction;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeclareWarUi {

    private final DeclareWarAction declareWarAction;

    public static DeclareWarUi newInstance() {
        return new DeclareWarUi(new DeclareWarAction());
    }

    private static String getLocalizedName() {
        return L10nCivilization.DECLARE_WAR_NAME.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nCivilization.DECLARE_WAR_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(Civilization myCivilization, Civilization otherCivilization) {
        if (declareWarAction.canDeclareWar(myCivilization, otherCivilization).isFail()) {
            return null;
        }

        return Format.text("""
            <td><button onclick="$buttonOnClick">$buttonLabel</button></td><td>$actionDescription</td>
            """,

            "$buttonOnClick", ClientAjaxRequest.declareWar(otherCivilization),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription());
    }
}
