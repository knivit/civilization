package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.civilization.city.L10nCity;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.util.l10n.L10n;

import static com.tsoft.civilization.util.l10n.L10nLanguage.EN;
import static com.tsoft.civilization.util.l10n.L10nLanguage.RU;

public final class CityUnitActionResults {

    private CityUnitActionResults() { }

    public static final ActionSuccessResult CAN_BUY_UNIT = new ActionSuccessResult(new L10n()
        .put(EN, "Can buy this unit")
        .put(RU, "Можно купить этот юнит"));

    public static final ActionSuccessResult UNIT_WAS_BOUGHT = new ActionSuccessResult(new L10n()
        .put(EN, "An Unit was bought")
        .put(RU, "Юнит куплен"));

    public static final ActionFailureResult CANT_BUY_THIS_UNIT = new ActionFailureResult(new L10n()
        .put(EN, "Can't by this unit")
        .put(RU, "Нельзя купить юнит"));

    public static final ActionSuccessResult CAN_START_UNIT_CONSTRUCTION = new ActionSuccessResult(new L10n()
        .put(EN, "Can start unit construction")
        .put(RU, "Можно начать строительство юнита"));

    public static final ActionSuccessResult UNIT_CONSTRUCTION_IS_STARTED = new ActionSuccessResult(new L10n()
        .put(EN, "Unit's construction is started")
        .put(RU, "Начато строительство юнита"));

    public static final ActionFailureResult INVALID_UNIT = new ActionFailureResult(new L10n()
        .put(EN, "Invalid unit")
        .put(RU, "Неверный юнит"));

    public static final ActionFailureResult CANT_BUILD_THIS_UNIT = new ActionFailureResult(new L10n()
        .put(EN, "Can't build this unit")
        .put(RU, "Невозможно построить этот юнит"));

    public static final ActionFailureResult CANT_BUILD_UNIT_OTHER_ACTION_IN_PROGRESS = new ActionFailureResult(new L10n()
        .put(EN, "Can't start unit's building as there is other action is in progress.")
        .put(RU, "Нельзя начать строительство юнита, пока идет другое строительство."));

    public static final ActionFailureResult CITY_CANT_PLACE_UNIT = new ActionFailureResult(new L10n()
        .put(EN, "City can't place the unit")
        .put(RU, "Город не может принять этот юнит"));

}
