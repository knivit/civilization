package com.tsoft.civilization.improvement.city.action;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.BuildingFactory;
import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.building.L10nBuilding;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class BuildBuildingAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final L10n localizedDescription = L10nBuilding.BUILD_DESCRIPTION;

    public static ActionAbstractResult buildBuilding(City city, String buildingClassUuid) {
        ActionAbstractResult result = canBuildBuilding(city, buildingClassUuid);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        AbstractBuilding building = BuildingFactory.newInstance(buildingClassUuid, city);
        city.startConstruction(building);
        return CityActionResults.BUILDING_CONSTRUCTION_IS_STARTED;
    }

    private static ActionAbstractResult canBuildBuilding(City city, String buildingClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractBuilding building = BuildingFactory.findByClassUuid(buildingClassUuid);

        if (buildingClassUuid == null || building == null) {
            return CityActionResults.INVALID_BUILDING;
        }

        if (!building.requiresEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (!city.canPlaceBuilding(building)) {
            return CityActionResults.CANT_BUILD_THIS_BUILDING;
        }

        if (!city.canStartConstruction()) {
            return CityActionResults.CANT_BUILD_BUILDING_OTHER_ACTION_IN_PROGRESS;
        }

        int productionCost = city.getBuildingProductionCost(buildingClassUuid);
        if (productionCost < 0) {
            return CityActionResults.CANT_BUILD_THIS_BUILDING;
        }

        return CityActionResults.CAN_START_CONSTRUCTION;
    }

    public static StringBuilder getHtml(City city, String buildingClassUuid) {
        if (canBuildBuilding(city, buildingClassUuid).isFail()) {
            return null;
        }

        return Format.text("""
            <button onclick="$buttonOnClick">$buttonLabel: $productionCost $production</button>
            """,

            "$buttonOnClick", ClientAjaxRequest.buildBuildingAction(city, buildingClassUuid),
            "$buttonLabel", L10nBuilding.BUILD.getLocalized(),
            "$productionCost", city.getBuildingProductionCost(buildingClassUuid),
            "$production", L10nCity.PRODUCTION
        );
    }
}
