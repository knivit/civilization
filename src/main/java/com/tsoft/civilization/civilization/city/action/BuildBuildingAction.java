package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.building.BuildingFactory;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.ui.CityActionResults;
import com.tsoft.civilization.civilization.city.ui.CityBuildingActionResults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuildBuildingAction {

    public ActionAbstractResult canBuildBuilding(City city, String buildingClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractBuilding building = BuildingFactory.findByClassUuid(buildingClassUuid);

        if (buildingClassUuid == null || building == null) {
            return CityBuildingActionResults.INVALID_BUILDING;
        }

        if (!building.requiredEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (city.getBuildingService().alreadyExists(building.getClassUuid())) {
            return CityBuildingActionResults.CANT_BUILD_THIS_BUILDING;
        }

        if (!city.canStartConstruction()) {
            return CityBuildingActionResults.CANT_BUILD_BUILDING_OTHER_ACTION_IN_PROGRESS;
        }

        int productionCost = city.getBuildingProductionCost(buildingClassUuid);
        if (productionCost < 0) {
            return CityBuildingActionResults.CANT_BUILD_THIS_BUILDING;
        }

        return CityBuildingActionResults.CAN_START_CONSTRUCTION;
    }

    public ActionAbstractResult buildBuilding(City city, String buildingClassUuid) {
        ActionAbstractResult result = canBuildBuilding(city, buildingClassUuid);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        AbstractBuilding building = BuildingFactory.newInstance(buildingClassUuid, city);
        city.startConstruction(building);
        return CityBuildingActionResults.BUILDING_CONSTRUCTION_IS_STARTED;
    }
}
