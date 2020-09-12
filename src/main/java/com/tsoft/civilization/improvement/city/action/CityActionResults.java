package com.tsoft.civilization.improvement.city.action;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.building.L10nBuilding;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class CityActionResults {
    public static final ActionSuccessResult CAN_START_CONSTRUCTION = new ActionSuccessResult(L10nCity.CAN_START_CONSTRUCTION);
    public static final ActionSuccessResult CAN_BUY_UNIT = new ActionSuccessResult(L10nCity.CAN_BUY_THIS_UNIT);
    public static final ActionSuccessResult BUILDING_CONSTRUCTION_IS_STARTED = new ActionSuccessResult(L10nCity.BUILDING_CONSTRUCTION_IS_STARTED);
    public static final ActionSuccessResult UNIT_CONSTRUCTION_IS_STARTED = new ActionSuccessResult(L10nCity.UNIT_CONSTRUCTION_IS_STARTED);
    public static final ActionSuccessResult CAN_DESTROY_BUILDING = new ActionSuccessResult(L10nBuilding.CAN_DESTROY);
    public static final ActionSuccessResult BUILDING_DESTROYED = new ActionSuccessResult(L10nBuilding.BUILDING_DESTROYED);
    public static final ActionSuccessResult BUILDING_WAS_BOUGHT = new ActionSuccessResult(L10nBuilding.BUILDING_WAS_BOUGHT);
    public static final ActionSuccessResult UNIT_WAS_BOUGHT = new ActionSuccessResult(L10nUnit.UNIT_WAS_BOUGHT);

    public static final ActionFailureResult INVALID_BUILDING = new ActionFailureResult(L10nCity.INVALID_BUILDING);
    public static final ActionFailureResult INVALID_UNIT = new ActionFailureResult(L10nCity.INVALID_UNIT);

    public static final ActionFailureResult CANT_BUILD_BUILDING_OTHER_ACTION_IN_PROGRESS = new ActionFailureResult(L10nCity.CANT_BUILD_BUILDING_OTHER_ACTION_IN_PROGRESS);
    public static final ActionFailureResult CANT_BUILD_THIS_BUILDING = new ActionFailureResult(L10nCity.CANT_BUILD_THIS_BUILDING);
    public static final ActionFailureResult CANT_DESTROY_BUILDING = new ActionFailureResult(L10nBuilding.CANT_DESTROY);
    public static final ActionFailureResult CANT_BUY_THIS_BUILDING = new ActionFailureResult(L10nCity.CANT_BUY_THIS_BUILDING);

    public static final ActionFailureResult CANT_BUILD_UNIT_OTHER_ACTION_IN_PROGRESS = new ActionFailureResult(L10nCity.CANT_BUILD_UNIT_OTHER_ACTION_IN_PROGRESS);
    public static final ActionFailureResult CANT_BUILD_THIS_UNIT = new ActionFailureResult(L10nCity.CANT_BUILD_THIS_UNIT);
    public static final ActionFailureResult WRONG_ERA_OR_TECHNOLOGY = new ActionFailureResult(L10nCivilization.WRONG_ERA_OR_TECHNOLOGY);
    public static final ActionFailureResult BUILDING_NOT_FOUND = new ActionFailureResult(L10nCity.BUILDING_NOT_FOUND);
    public static final ActionFailureResult NOT_ENOUGH_MONEY = new ActionFailureResult(L10nCivilization.NOT_ENOUGH_MONEY);
    public static final ActionFailureResult CITY_NOT_FOUND = new ActionFailureResult(L10nCity.CITY_NOT_FOUND);
    public static final ActionFailureResult CITY_CANT_PLACE_UNIT = new ActionFailureResult(L10nCity.CITY_CANT_PLACE_UNIT);
}
