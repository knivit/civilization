package com.tsoft.civilization.unit.civil.greatengineer.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.action.MoveUnitActionResults;
import com.tsoft.civilization.unit.civil.greatengineer.GreatEngineer;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

// The Hurry Production ability is used such that the Engineer will speed the
// production of whatever is being built. Most of the time, the building, Wonder
// or unit will be instantly completed, but for the Wonders late in the game, it
// will be less immediate production, but more of a big boost in production hammers.
public class HurryProductionAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult hurryProduction(GreatEngineer unit) {
        return MoveUnitActionResults.INVALID_LOCATION;
    }

    private static ActionAbstractResult canHurryProduction(GreatEngineer unit) {
        return MoveUnitActionResults.INVALID_LOCATION;
    }

    private static String getClientJSCode(GreatEngineer unit) {
        return "not implemented";
    }

    private static String getLocalizedName() {
        return "not implemented";
    }

    private static String getLocalizedDescription() {
        return "not implemented";
    }

    public static StringBuilder getHtml(GreatEngineer unit) {
        if (canHurryProduction(unit).isFail()) {
            return null;
        }

        return Format.text(
            "<td><button onclick=\"$buttonOnClick\">$buttonLabel</button></td><td>$actionDescription</td>",

            "$buttonOnClick", getClientJSCode(unit),
            "$buttonLabel", getLocalizedName(),
            "$actionDescription", getLocalizedDescription()
        );
    }
}
