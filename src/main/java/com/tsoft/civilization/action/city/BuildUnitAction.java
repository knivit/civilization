package com.tsoft.civilization.action.city;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Format;

import java.util.UUID;

public class BuildUnitAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buildUnit(City city, String unitClassUuid) {
        ActionAbstractResult result = canBuildUnit(city, unitClassUuid);
        if (result.isFail()) {
            return result;
        }

        AbstractUnit unit = AbstractUnit.getUnitFromCatalogByClassUuid(unitClassUuid);
        city.startConstruction(unit);

        return CityActionResults.UNIT_CONSTRUCTION_IS_STARTED;
    }

    public static ActionAbstractResult canBuildUnit(City city, String unitClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractUnit unit = AbstractUnit.getUnitFromCatalogByClassUuid(unitClassUuid);
        if (unit == null || unit.getProductionCost() < 0) {
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

    private static String getClientJSCode(City city, String unitClassUuid) {
        return String.format("client.buildUnitAction({ city:'%1$s', unitUuid:'%2$s' })", city.getId(), unitClassUuid);
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

        return Format.text(
            "<button onclick=\"$buttonOnClick\">$buttonLabel: $productionCost $production</button>",

            "$buttonOnClick", getClientJSCode(city, unitClassUuid),
            "$buttonLabel", getLocalizedName(),
            "$productionCost", city.getUnitProductionCost(unitClassUuid),
            "$production", L10nCity.PRODUCTION
        );
    }
}
