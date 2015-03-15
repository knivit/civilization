package com.tsoft.civilization.action.city;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.building.L10nBuilding;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.util.BuildingCatalog;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

public class BuildBuildingAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildBuilding(City city, String buildingClassUuid) {
        ActionAbstractResult result = canBuildBuilding(city, buildingClassUuid);
        if (result.isFail()) {
            return result;
        }

        AbstractBuilding building = BuildingCatalog.values().findByClassUuid(buildingClassUuid);
        city.startConstruction(building);

        return CityActionResults.BUILDING_CONSTRUCTION_IS_STARTED;
    }

    private static ActionAbstractResult canBuildBuilding(City city, String buildingClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractBuilding building = BuildingCatalog.values().findByClassUuid(buildingClassUuid);
        if (building == null || building.getProductionCost() < 0) {
            return CityActionResults.INVALID_BUILDING;
        }

        if (!building.checkEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (!city.canPlaceBuilding(building)) {
            return CityActionResults.CANT_BUILD_THIS_BUILDING;
        }

        if (!city.canStartConstruction()) {
            return CityActionResults.CANT_BUILD_BUILDING_OTHER_ACTION_IN_PROGRESS;
        }

        return CityActionResults.CAN_START_CONSTRUCTION;
    }

    private static String getClientJSCode(City city, String buildingClassUuid) {
        return String.format("client.buildBuildingAction({ city:'%1$s', buildingUuid:'%2$s' })", city.getId(), buildingClassUuid);
    }

    private static String getLocalizedName() {
        return L10nBuilding.BUILD.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nBuilding.BUILD_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(City city, String buildingClassUuid) {
        if (canBuildBuilding(city, buildingClassUuid).isFail()) {
            return null;
        }

        return Format.text(
            "<button onclick=\"$buttonOnClick\">$buttonLabel: $productionCost $production</button>",

            "$buttonOnClick", getClientJSCode(city, buildingClassUuid),
            "$buttonLabel", getLocalizedName(),
            "$productionCost", city.getBuildingProductionCost(buildingClassUuid),
            "$production", L10nCity.PRODUCTION
        );
    }
}
