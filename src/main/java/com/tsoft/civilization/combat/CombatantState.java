package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CombatantState {

    private final int strikeStrength;
    private final int appliedStrikeStrength;

    private final int rangedExperience;
    private final int rangedExperienceAfterAttack;

    private final int meleeExperience;
    private final int meleeExperienceAfterAttack;

    private final int defenseStrength;
    private final int defenseStrengthAfterAttack;

    private final int defenseExperience;
    private final int defenseExperienceAfterAttack;

    private final boolean isDestroyed;

}
