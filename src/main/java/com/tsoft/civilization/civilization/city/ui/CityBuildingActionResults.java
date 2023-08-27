package com.tsoft.civilization.civilization.city.ui;

import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public final class CityBuildingActionResults {

    private CityBuildingActionResults() { }

    public static final ActionSuccessResult CAN_START_CONSTRUCTION = new ActionSuccessResult(new L10n()
        .put(EN, "Can start building construction")
        .put(RU, "Можно начать строительство здания"));

    public static final ActionSuccessResult BUILDING_CONSTRUCTION_IS_STARTED = new ActionSuccessResult(new L10n()
        .put(EN, "Building construction is started")
        .put(RU, "Начато строительство здания"));

    public static final ActionSuccessResult CAN_DESTROY_BUILDING = new ActionSuccessResult(new L10n()
        .put(EN, "The building can be destroyed")
        .put(RU, "Здание может быть разрушено"));

    public static final ActionSuccessResult BUILDING_DESTROYED = new ActionSuccessResult(new L10n()
        .put(EN, "Building $buildingName has been destroyed")
        .put(RU, "Здание $buildingName разрушено"));

    public static final ActionSuccessResult BUILDING_WAS_BOUGHT = new ActionSuccessResult(new L10n()
        .put(EN, "The Building was bought")
        .put(RU, "Здание было куплено"));

    public static final ActionFailureResult INVALID_BUILDING = new ActionFailureResult(new L10n()
        .put(EN, "Invalid building")
        .put(RU, "Неверное здание"));

    public static final ActionFailureResult CANT_BUILD_BUILDING_OTHER_ACTION_IN_PROGRESS = new ActionFailureResult(new L10n()
        .put(EN, "Can't start a building as there is other action is in progress")
        .put(RU, "Нельзя начать строительство, потому что выполняется другое действие"));

    public static final ActionFailureResult CANT_BUILD_THIS_BUILDING = new ActionFailureResult(new L10n()
        .put(EN, "Can't build this building")
        .put(RU, "Невозможно построить это здание"));

    public static final ActionFailureResult CANT_DESTROY_BUILDING = new ActionFailureResult(new L10n()
        .put(EN, "The building can't be destroyed")
        .put(RU, "Здание не может быть разрушено"));

    public static final ActionFailureResult CANT_BUY_THIS_BUILDING = new ActionFailureResult(new L10n()
        .put(EN, "Can't buy this building")
        .put(RU, "Невозможно купить это здание"));

    public static final ActionFailureResult BUILDING_NOT_FOUND = new ActionFailureResult(new L10n()
        .put(EN, "Building not found")
        .put(RU, "Здание не найдено"));
}
