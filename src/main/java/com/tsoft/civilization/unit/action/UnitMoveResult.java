package com.tsoft.civilization.unit.action;

public enum UnitMoveResult {
    FAIL_NOT_ENOUGH_PASSING_SCORE(false),
    FAIL_NOT_ENOUGH_PASSING_SCORE_TO_SWAP(false),
    FAIL_SWAPPING_MUST_BE_THE_ONLY_MOVE(false),
    FAIL_TILE_OCCUPIED(false),
    FAIL_COMBAT_LOSS(false),
    FAIL_CANT_CROSS_BORDERS(false),
    FAIL_TILE_OCCUPIED_BY_FOREIGN_UNIT(false),
    FAIL_TO_CONQUER_CITY(false),

    SUCCESS_MOVED(true),
    SUCCESS_SWAPPED(true),
    SUCCESS_ENTERED_INTO_OWN_CITY(true),
    SUCCESS_BEAT_FOREIGN_UNIT(true),
    SUCCESS_CAPTURE_FOREIGN_UNIT(true),
    SUCCESS_CITY_CONQUERED(true),

    CHECK_FAILED(false);

    private final boolean isSuccess;

    UnitMoveResult(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public boolean isFailed() {
        return !isSuccess;
    }
}
