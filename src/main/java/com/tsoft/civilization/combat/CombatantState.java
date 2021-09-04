package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CombatantState {

    private final int strikeStrength;
    private final int appliedStrikeStrength;

    private final int experience;
    private final int experienceAfterAttack;

    private final int defenseStrength;
    private final int defenseStrengthAfterAttack;

    private final boolean isDestroyed;

}
