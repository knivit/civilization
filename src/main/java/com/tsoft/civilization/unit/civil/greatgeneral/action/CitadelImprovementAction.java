package com.tsoft.civilization.unit.civil.greatgeneral.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.civil.greatgeneral.GreatGeneral;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

import static com.tsoft.civilization.unit.move.UnitMoveService.INVALID_TARGET_LOCATION;

// The Great General is made to generate the Citadel, which is useful when it is
// in your lands. When the Citadel is on land that you control, the Citadel will
// deal damage to all units next to it that isn't friendly. But if it were to
// change hands, it will be under the control of the new owner.
public class CitadelImprovementAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildCitadelImprovement(GreatGeneral unit) {
        return INVALID_TARGET_LOCATION;
    }

    private static ActionAbstractResult canBuildbuildCitadelImprovement(GreatGeneral unit) {
        return INVALID_TARGET_LOCATION;
    }

    private static String getClientJSCode(GreatGeneral unit) {
        return "not implemented";
    }

    private static String getLocalizedName() {
        return "not implemented";
    }

    private static String getLocalizedDescription() {
        return "not implemented";
    }

    public static StringBuilder getHtml(GreatGeneral unit) {
        if (canBuildbuildCitadelImprovement(unit).isFail()) {
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
