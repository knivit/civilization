package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.city.L10nCity;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Format;
import com.tsoft.civilization.web.ajax.ClientAjaxRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import static com.tsoft.civilization.web.ajax.ServerStaticResource.GOLD_IMAGE;

@Slf4j
public class BuyUnitAction {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public static final ActionSuccessResult CAN_BUY_UNIT = new ActionSuccessResult(L10nCity.CAN_BUY_THIS_UNIT);
    public static final ActionSuccessResult UNIT_WAS_BOUGHT = new ActionSuccessResult(L10nUnit.UNIT_WAS_BOUGHT);

    public static final ActionFailureResult CANT_BUY_THIS_UNIT = new ActionFailureResult(L10nUnit.CANT_BUY_THIS_UNIT);

    public static ActionAbstractResult buyUnit(City city, String unitClassUuid) {
        ActionAbstractResult result = canBuyUnit(city, unitClassUuid);
        log.debug("{}", result);

        if (result.isFail()) {
            return result;
        }

        if (!city.getCivilization().buyUnit(unitClassUuid, city)) {
            return CANT_BUY_THIS_UNIT;
        }

        return UNIT_WAS_BOUGHT;
    }

    public static ActionAbstractResult canBuyUnit(City city, String unitClassUuid) {
        if (city == null || city.isDestroyed()) {
            return CityActionResults.CITY_NOT_FOUND;
        }

        AbstractUnit unit = UnitFactory.findByClassUuid(unitClassUuid);
        if (unit.getGoldCost(city.getCivilization()) < 0) {
            return CityActionResults.INVALID_UNIT;
        }

        if (!unit.checkEraAndTechnology(city.getCivilization())) {
            return CityActionResults.WRONG_ERA_OR_TECHNOLOGY;
        }

        if (!city.getCivilization().getUnitService().canBePlaced(unit, city.getLocation())) {
            return CityActionResults.CITY_CANT_PLACE_UNIT;
        }

        if (!city.getCivilization().getUnitService().canBuyUnit(unit, city)) {
            return CityActionResults.NOT_ENOUGH_MONEY;
        }

        return CAN_BUY_UNIT;
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

        return Format.text("""
            <button onclick="$buttonOnClick">$buttonLabel: $buyCost $goldImage</button>
            """,

            "$buttonOnClick", ClientAjaxRequest.buyUnitAction(city, unitClassUuid),
            "$buttonLabel", getLocalizedName(),
            "$buyCost", city.getUnitBuyCost(unitClassUuid),
            "$goldImage", GOLD_IMAGE
        );
    }
}
