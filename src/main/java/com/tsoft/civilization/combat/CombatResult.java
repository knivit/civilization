package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class CombatResult {

    private final CombatantState attackerState;
    private final CombatantState targetState;
    private final CombatStatus status;
}
