package com.tsoft.civilization.unit.civil.greatmerchant.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.unit.civil.greatmerchant.GreatMerchant;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

import static com.tsoft.civilization.unit.move.UnitMoveService.INVALID_TARGET_LOCATION;

// The Trade Mission is an ability that is basically a trading mission. Send the
// Great Merchant to the city of a City State, and they will launch a trade
// mission, which will end up generating a lot of gold for your empire, as well
// as improving relations between your empire and the city-state.
public class TradeMissionAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult tradeMission(GreatMerchant unit) {
        return INVALID_TARGET_LOCATION;
    }

    private static ActionAbstractResult canTradeMission(GreatMerchant unit) {
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
        if (canTradeMission(unit).isFail()) {
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
