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

import static com.tsoft.civilization.web.ajax.ServerStaticResource.PRODUCTION_IMAGE;

@Slf4j
public class BuildBuildingAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

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

        if (!building.requiredEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (city.getBuildingService().alreadyExists(building.getClassUuid())) {
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
            <button onclick="$buttonOnClick">$buttonLabel: $productionCost $productionImage</button>
            """,

            "$buttonOnClick", ClientAjaxRequest.buildBuildingAction(city, buildingClassUuid),
            "$buttonLabel", L10nBuilding.BUILD.getLocalized(),
            "$productionCost", city.getBuildingProductionCost(buildingClassUuid),
            "$productionImage", PRODUCTION_IMAGE
        );
    }
}
