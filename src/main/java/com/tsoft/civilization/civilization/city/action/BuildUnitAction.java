package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.ui.CityActionResults;
import com.tsoft.civilization.civilization.city.ui.CityBuildingActionResults;
import com.tsoft.civilization.civilization.city.ui.CityUnitActionResults;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;

public class BuildUnitAction {

    public ActionAbstractResult canBuildUnit(City city, String unitClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractUnit unit = UnitFactory.findByClassUuid(unitClassUuid);

        if (unit.getBaseProductionCost(city.getCivilization()) < 0) {
            return CityUnitActionResults.INVALID_UNIT;
        }

        if (!unit.checkEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (!city.canStartConstruction()) {
            return CityUnitActionResults.CANT_BUILD_UNIT_OTHER_ACTION_IN_PROGRESS;
        }

        return CityBuildingActionResults.CAN_START_CONSTRUCTION;
    }

    public ActionAbstractResult buildUnit(City city, String unitClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        ActionAbstractResult result = canBuildUnit(city, unitClassUuid);
        if (result.isFail()) {
            return result;
        }

        AbstractUnit unit = UnitFactory.newInstance(city.getCivilization(), unitClassUuid);
        city.startConstruction(unit);
        return CityUnitActionResults.UNIT_CONSTRUCTION_IS_STARTED;
    }
}
