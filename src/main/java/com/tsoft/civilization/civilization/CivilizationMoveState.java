package com.tsoft.civilization.civilization;

import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum CivilizationMoveState {

    IN_PROGRESS(L10nCivilization.MOVE_IN_PROGRESS),
    DONE(L10nCivilization.MOVE_DONE);

    @Getter
    private final L10n description;
}
