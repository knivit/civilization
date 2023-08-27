package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.ui.CityActionResults;
import com.tsoft.civilization.civilization.city.ui.CityUnitActionResults;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuyUnitAction {

    public ActionAbstractResult canBuyUnit(City city, String unitClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractUnit unit = UnitFactory.findByClassUuid(unitClassUuid);
        if (unit.getGoldCost(city.getCivilization()) < 0) {
            return CityUnitActionResults.INVALID_UNIT;
        }

        if (!unit.checkEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (!city.getCivilization().getUnitService().canBePlaced(unit, city.getLocation())) {
            return CityUnitActionResults.CITY_CANT_PLACE_UNIT;
        }

        if (!city.getCivilization().getUnitService().canBuyUnit(unit, city)) {
            return CityActionResults.NOT_ENOUGH_MONEY;
        }

        return CityUnitActionResults.CAN_BUY_UNIT;
    }

    public ActionAbstractResult buyUnit(City city, String unitClassUuid) {
        ActionAbstractResult result = canBuyUnit(city, unitClassUuid);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        if (!city.getCivilization().buyUnit(unitClassUuid, city)) {
            return CityUnitActionResults.CANT_BUY_THIS_UNIT;
        }

        return CityUnitActionResults.UNIT_WAS_BOUGHT;
    }
}
