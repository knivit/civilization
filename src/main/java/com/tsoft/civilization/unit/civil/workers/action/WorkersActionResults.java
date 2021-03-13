package com.tsoft.civilization.unit.civil.workers.action;

import com.tsoft.civilization.improvement.L10nImprovement;
import com.tsoft.civilization.unit.L10nUnit;
import com.tsoft.civilization.unit.civil.workers.L10nWorkers;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;

public class WorkersActionResults {
    public static final ActionSuccessResult CAN_BUILD_IMPROVEMENT = new ActionSuccessResult(L10nWorkers.CAN_BUILD_IMPROVEMENT);
    public static final ActionSuccessResult BUILDING_IMPROVEMENT = new ActionSuccessResult(L10nWorkers.BUILDING_IMPROVEMENT);
    public static final ActionSuccessResult IMPROVEMENT_IS_BUILT = new ActionSuccessResult(L10nWorkers.IMPROVEMENT_IS_BUILT);

    public static final ActionSuccessResult CAN_REMOVE_FOREST = new ActionSuccessResult(L10nWorkers.CAN_REMOVE_FOREST);
    public static final ActionSuccessResult FOREST_IS_REMOVED = new ActionSuccessResult(L10nWorkers.FOREST_IS_REMOVED);
    public static final ActionSuccessResult REMOVING_FOREST = new ActionSuccessResult(L10nWorkers.REMOVING_FOREST);

    public static final ActionSuccessResult CAN_REMOVE_HILL = new ActionSuccessResult(L10nWorkers.CAN_REMOVE_HILL);
    public static final ActionSuccessResult HILL_IS_REMOVED = new ActionSuccessResult(L10nWorkers.HILL_IS_REMOVED);
    public static final ActionSuccessResult REMOVING_HILL = new ActionSuccessResult(L10nWorkers.REMOVING_HILL);

    public static final ActionFailureResult FAIL_NO_FOREST_HERE = new ActionFailureResult(L10nWorkers.FAIL_NO_FOREST_HERE);
    public static final ActionFailureResult FAIL_NO_HILL_HERE = new ActionFailureResult(L10nWorkers.FAIL_NO_HILL_HERE);
    public static final ActionFailureResult FAIL_FOREST_MUST_BE_REMOVED_FIRST = new ActionFailureResult(L10nWorkers.FAIL_FOREST_MUST_BE_REMOVED_FIRST);
    public static final ActionFailureResult FAIL_NO_MINING_TECHNOLOGY = new ActionFailureResult(L10nWorkers.FAIL_NO_MINING_TECHNOLOGY);
    public static final ActionFailureResult UNIT_NOT_FOUND = new ActionFailureResult(L10nUnit.UNIT_NOT_FOUND);
    public static final ActionFailureResult IMPROVEMENT_ALREADY_EXISTS = new ActionFailureResult(L10nImprovement.IMPROVEMENT_ALREADY_EXISTS);
}
