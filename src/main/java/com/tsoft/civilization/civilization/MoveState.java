package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10n;

public enum MoveState {

    IN_PROGRESS(L10nCivilization.MOVE_IN_PROGRESS),
    DONE(L10nCivilization.MOVE_DONE);

    private final L10n description;

    MoveState(L10n description) {
        this.description = description;
    }

    public L10n getDescription() {
        return description;
    }
}
