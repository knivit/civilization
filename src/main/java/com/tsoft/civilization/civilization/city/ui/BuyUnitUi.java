package com.tsoft.civilization.civilization.city.ui;

import com.tsoft.civilization.civilization.city.action.BuyUnitAction;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.GOLD_IMAGE;

@RequiredArgsConstructor
public class BuyUnitUi {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final BuyUnitAction buyUnitAction;

    private static String getLocalizedName() {
        return L10nUnit.BUY.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.BUY_DESCRIPTION.getLocalized();
    }

    public StringBuilder getHtml(City city, String unitClassUuid) {
        if (buyUnitAction.canBuyUnit(city, unitClassUuid).isFail()) {
            return null;
        }

        return Format.text("""
            <button onclick="$buttonOnClick">$buttonLabel: $buyCost $goldImage</button>
            """,

            "$buttonOnClick", ClientAjaxRequest.buyUnitAction(city, unitClassUuid),
            "$buttonLabel", getLocalizedName(),
            "$buyCost", city.getUnitBuyCost(unitClassUuid),
            "$goldImage", GOLD_IMAGE
        );
    }
}
