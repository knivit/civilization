package com.tsoft.civilization.improvement.city.action;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Format;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class BuyUnitAction {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static ActionAbstractResult buyUnit(City city, String unitClassUuid) {
        ActionAbstractResult result = canBuyUnit(city, unitClassUuid);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        city.getCivilization().buyUnit(unitClassUuid, city);
        return CityActionResults.UNIT_WAS_BOUGHT;
    }

    public static ActionAbstractResult canBuyUnit(City city, String unitClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractUnit<?> unit = UnitFactory.createUnit(unitClassUuid);
        if (unit.getGoldCost() < 0) {
            return CityActionResults.INVALID_UNIT;
        }

        if (!unit.checkEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (!city.getCivilization().canBuyUnit(unit)) {
            return CityActionResults.NOT_ENOUGH_MONEY;
        }

        if (!city.canPlaceUnit(unit)) {
            return CityActionResults.CITY_CANT_PLACE_UNIT;
        }

        return CityActionResults.CAN_BUY_UNIT;
    }

    private static String getClientJSCode(City city, String unitClassUuid) {
        return String.format("client.buyUnitAction({ city:'%1$s', unitUuid:'%2$s' })", city.getId(), unitClassUuid);
    }

    private static String getLocalizedName() {
        return L10nUnit.BUY.getLocalized();
    }

    private static String getLocalizedDescription() {
        return L10nUnit.BUY_DESCRIPTION.getLocalized();
    }

    public static StringBuilder getHtml(City city, String unitClassUuid) {
        if (canBuyUnit(city, unitClassUuid).isFail()) {
            return null;
        }

        return Format.text(
            "<button onclick=\"$buttonOnClick\">$buttonLabel: $buyCost $gold</button>",

            "$buttonOnClick", getClientJSCode(city, unitClassUuid),
            "$buttonLabel", getLocalizedName(),
            "$buyCost", city.getUnitBuyCost(unitClassUuid),
            "$gold", L10nCity.GOLD
        );
    }
}
