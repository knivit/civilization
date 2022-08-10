package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.civilization.building.BuildingFactory;
import com.tsoft.civilization.civilization.building.L10nBuilding;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.GOLD_IMAGE;

@Slf4j
public class BuyBuildingAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buyBuilding(City city, String buildingClassUuid) {
        ActionAbstractResult result = canBuyBuilding(city, buildingClassUuid);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        city.getCivilization().buyBuilding(buildingClassUuid, city);

        return CityActionResults.BUILDING_WAS_BOUGHT;
    }

    private static ActionAbstractResult canBuyBuilding(City city, String buildingClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractBuilding building = BuildingFactory.findByClassUuid(buildingClassUuid);
        if (building == null || building.getGoldCost(city.getCivilization()) < 0) {
            return CityActionResults.INVALID_BUILDING;
        }

        if (!building.requiresEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (!city.canPlaceBuilding(building)) {
            return CityActionResults.CANT_BUY_THIS_BUILDING;
        }

        if (!city.getCivilization().canBuyBuilding(building)) {
            return CityActionResults.NOT_ENOUGH_MONEY;
        }

        return CityActionResults.CAN_START_CONSTRUCTION;
    }

    private static String getLocalizedName() {
        return L10nBuilding.BUY.getLocalized();
    }

    public static StringBuilder getHtml(City city, String buildingClassUuid) {
        if (canBuyBuilding(city, buildingClassUuid).isFail()) {
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
