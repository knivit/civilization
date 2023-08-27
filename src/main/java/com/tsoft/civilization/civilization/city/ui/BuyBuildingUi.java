package com.tsoft.civilization.civilization.city.ui;

import com.tsoft.civilization.civilization.building.L10nBuilding;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.action.BuyBuildingAction;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.GOLD_IMAGE;

@RequiredArgsConstructor
public class BuyBuildingUi {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final BuyBuildingAction buyBuildingAction;

    private static String getLocalizedName() {
        return L10nBuilding.BUY.getLocalized();
    }

    public StringBuilder getHtml(City city, String buildingClassUuid) {
        if (buyBuildingAction.canBuyBuilding(city, buildingClassUuid).isFail()) {
            return null;
        }

        return Format.text(
            "<button onclick=\"$buttonOnClick\">$buttonLabel: $buyCost $goldImage</button>",

            "$buttonOnClick", ClientAjaxRequest.buyBuildingAction(city, buildingClassUuid),
            "$buttonLabel", getLocalizedName(),
            "$buyCost", city.getBuildingBuyCost(buildingClassUuid),
            "$goldImage", GOLD_IMAGE
        );
    }
}
