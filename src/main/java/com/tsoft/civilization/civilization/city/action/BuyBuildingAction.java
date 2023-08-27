package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.building.BuildingFactory;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.ui.CityActionResults;
import com.tsoft.civilization.civilization.city.ui.CityBuildingActionResults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuyBuildingAction {

    public ActionAbstractResult canBuyBuilding(City city, String buildingClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractBuilding building = BuildingFactory.findByClassUuid(buildingClassUuid);
        if (building == null || building.getGoldCost(city.getCivilization()) < 0) {
            return CityBuildingActionResults.INVALID_BUILDING;
        }

        if (!building.requiredEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (city.getBuildingService().alreadyExists(building.getClassUuid())) {
            return CityBuildingActionResults.CANT_BUY_THIS_BUILDING;
        }

        if (!city.getCivilization().canBuyBuilding(building)) {
            return CityActionResults.NOT_ENOUGH_MONEY;
        }

        return CityBuildingActionResults.CAN_START_CONSTRUCTION;
    }
    public ActionAbstractResult buyBuilding(City city, String buildingClassUuid) {
        ActionAbstractResult result = canBuyBuilding(city, buildingClassUuid);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        city.getCivilization().buyBuilding(buildingClassUuid, city);

        return CityBuildingActionResults.BUILDING_WAS_BOUGHT;
    }
}
