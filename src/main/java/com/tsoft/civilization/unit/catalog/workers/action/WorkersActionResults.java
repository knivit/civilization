package com.tsoft.civilization.unit.catalog.workers.action;

import com.tsoft.civilization.unit.catalog.workers.L10nWorkers;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class WorkersActionResults {

    public static final ActionSuccessResult CAN_BUILD_IMPROVEMENT = new ActionSuccessResult(L10nWorkers.CAN_BUILD_IMPROVEMENT);
    public static final ActionSuccessResult BUILDING_IMPROVEMENT = new ActionSuccessResult(L10nWorkers.BUILDING_IMPROVEMENT);
    public static final ActionSuccessResult IMPROVEMENT_IS_BUILT = new ActionSuccessResult(L10nWorkers.IMPROVEMENT_IS_BUILT);

    public static final ActionFailureResult FAIL_INAPPROPRIATE_TILE = new ActionFailureResult(L10nWorkers.FAIL_INAPPROPRIATE_TILE);
    public static final ActionFailureResult FAIL_FOREST_MUST_BE_REMOVED_FIRST = new ActionFailureResult(L10nWorkers.FAIL_FOREST_MUST_BE_REMOVED_FIRST);
    public static final ActionFailureResult FAIL_NO_MINING_TECHNOLOGY = new ActionFailureResult(L10nWorkers.FAIL_NO_MINING_TECHNOLOGY);
    public static final ActionFailureResult FAIL_IMPROVEMENT_ALREADY_EXISTS = new ActionFailureResult(L10nWorkers.FAIL_IMPROVEMENT_ALREADY_EXISTS);
    public static final ActionFailureResult FAIL_CANT_BUILD_IN_CITY_CENTER = new ActionFailureResult(L10nWorkers.FAIL_CANT_BUILD_IN_CITY_CENTER);
}
