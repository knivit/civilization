package com.tsoft.civilization.improvement.city.action;

import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;

import java.util.UUID;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.PRODUCTION_IMAGE;

public class BuildUnitAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildUnit(City city, String unitClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        ActionAbstractResult result = canBuildUnit(city, unitClassUuid);
        if (result.isFail()) {
            return result;
        }

        AbstractUnit unit = UnitFactory.newInstance(city.getCivilization(), unitClassUuid);
        city.startConstruction(unit);
        return CityActionResults.UNIT_CONSTRUCTION_IS_STARTED;
    }

    private static ActionAbstractResult canBuildUnit(City city, String unitClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractUnit unit = UnitFactory.findByClassUuid(unitClassUuid);

        if (unit.getProductionCost(city.getCivilization()) < 0) {
            return CityActionResults.INVALID_UNIT;
        }

        if (!unit.checkEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (!city.canStartConstruction()) {
            return CityActionResults.CANT_BUILD_UNIT_OTHER_ACTION_IN_PROGRESS;
        }

        return CityActionResults.CAN_START_CONSTRUCTION;
    }

    private static String getLocalizedName() {
        return L10nUnit.BUILD.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.BUILD_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(City city, String unitClassUuid) {
        if (canBuildUnit(city, unitClassUuid).isFail()) {
            return null;
        }

        return Format.text("""
            <button onclick="$buttonOnClick">$buttonLabel: $productionCost $productionImage</button>
            """,

            "$buttonOnClick", ClientAjaxRequest.buildUnitAction(city, unitClassUuid),
            "$buttonLabel", getLocalizedName(),
            "$productionCost", city.getUnitProductionCost(unitClassUuid),
            "$productionImage", PRODUCTION_IMAGE
        );
    }
}
