package com.tsoft.civilization.unit.civil.greatmerchant.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.civil.greatmerchant.GreatMerchant;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

import static com.tsoft.civilization.unit.service.move.MoveUnitService.INVALID_TARGET_LOCATION;

// The Great Merchant can build the Customs House, which will generate 4 gold per
// turn when it is worked. That makes it very useful for a city that is based off
// gold production, and that also ends up very nice for your empire, given the
// infinite amount of uses gold has.
public class CustomsHouseImprovementAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildCustomsHouseImprovement(GreatMerchant unit) {
        return INVALID_TARGET_LOCATION;
    }

    private static ActionAbstractResult canBuildCustomsHouseImprovement(GreatMerchant unit) {
        return INVALID_TARGET_LOCATION;
    }

    private static String getClientJSCode(GreatMerchant unit) {
        return "not implemented";
    }

    private static String getLocalizedName() {
        return "not implemented";
    }

    private static String getLocalizedDescription() {
        return "not implemented";
    }

    public static StringBuilder getHtml(GreatMerchant unit) {
        if (canBuildCustomsHouseImprovement(unit).isFail()) {
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
