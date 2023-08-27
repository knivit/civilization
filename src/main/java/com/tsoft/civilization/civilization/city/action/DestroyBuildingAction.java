package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.building.catalog.palace.Palace;
import com.tsoft.civilization.civilization.building.catalog.settlement.Settlement;
import com.tsoft.civilization.civilization.city.ui.CityBuildingActionResults;

public class DestroyBuildingAction {

    public ActionAbstractResult canDestroyBuilding(AbstractBuilding building) {
        if (building == null || building.isDestroyed()) {
            return CityBuildingActionResults.BUILDING_NOT_FOUND;
        }

        if (Palace.CLASS_UUID.equals(building.getClassUuid()) || Settlement.CLASS_UUID.equals(building.getClassUuid())) {
            return CityBuildingActionResults.CANT_DESTROY_BUILDING;
        }

        return CityBuildingActionResults.CAN_DESTROY_BUILDING;
    }

    public ActionAbstractResult destroyBuilding(AbstractBuilding building) {
        ActionAbstractResult result = canDestroyBuilding(building);
        if (result.isFail()) {
            return result;
        }

        building.getCity().destroyBuilding(building);

        return CityBuildingActionResults.BUILDING_DESTROYED;
    }
}
