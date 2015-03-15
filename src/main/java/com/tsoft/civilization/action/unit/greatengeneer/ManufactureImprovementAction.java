package com.tsoft.civilization.action.unit.greatengeneer;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.unit.MoveUnitActionResults;
import com.tsoft.civilization.unit.GreatEngineer;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

// The Great Engineer can be used to build a Manufacture tile, which is basically
// a tile improvement that generates 3 production hammers when it is worked,
// which makes it quite useful for a city that is used as a production base.
public class ManufactureImprovementAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildManufactureImprovement(GreatEngineer unit) {
        return MoveUnitActionResults.INVALID_LOCATION;
    }

    private static ActionAbstractResult canBuildManufactureImprovement(GreatEngineer unit) {
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
        if (canBuildManufactureImprovement(unit).isFail()) {
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
