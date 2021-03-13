package com.tsoft.civilization.unit.civil.settlers.action;

import com.tsoft.civilization.unit.civil.settlers.L10nSettlers;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class SettlersActionResults {
    public static final ActionSuccessResult CITY_BUILT = new ActionSuccessResult(L10nSettlers.CITY_BUILT);
    public static final ActionSuccessResult CAN_BUILD_CITY = new ActionSuccessResult(L10nSettlers.CAN_BUILD_CITY);

    public static final ActionFailureResult CANT_BUILD_CITY_TILE_IS_OCCUPIED = new ActionFailureResult(L10nSettlers.CANT_BUILD_CITY_TILE_IS_OCCUPIED);
    public static final ActionFailureResult CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY = new ActionFailureResult(L10nSettlers.CANT_BUILD_CITY_THERE_IS_ANOTHER_CITY_NEARBY);
    public static final ActionFailureResult NO_PASS_SCORE = new ActionFailureResult(L10nUnit.NO_PASS_SCORE);
    public static final ActionFailureResult UNIT_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
}
