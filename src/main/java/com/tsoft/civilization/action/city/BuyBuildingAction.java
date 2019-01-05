package com.tsoft.civilization.action.city;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.building.L10nBuilding;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.util.BuildingCatalog;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.util.Format;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

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

        AbstractBuilding building = BuildingCatalog.values().findByClassUuid(buildingClassUuid);
        if (building == null || building.getGoldCost() < 0) {
            return CityActionResults.INVALID_BUILDING;
        }

        if (!building.checkEraAndTechnology(city.getCivilization())) {
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

    private static String getClientJSCode(City city, String buildingClassUuid) {
        return String.format("client.buyBuildingAction({ city:'%1$s', buildingUuid:'%2$s' })", city.getId(), buildingClassUuid);
    }

    private static String getLocalizedName() {
        return L10nBuilding.BUY.getLocalized();
    }

    public static StringBuilder getHtml(City city, String buildingClassUuid) {
        if (canBuyBuilding(city, buildingClassUuid).isFail()) {
            return null;
        }

        return Format.text(
            "<button onclick=\"$buttonOnClick\">$buttonLabel: $buyCost $gold</button>",

            "$buttonOnClick", getClientJSCode(city, buildingClassUuid),
            "$buttonLabel", getLocalizedName(),
            "$buyCost", city.getBuildingBuyCost(buildingClassUuid),
            "$gold", L10nCity.GOLD
        );
    }
}
