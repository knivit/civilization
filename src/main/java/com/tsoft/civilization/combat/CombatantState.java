package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CombatantState {

    private final double strikeStrength;
    private final double appliedStrikeStrength;

    private final double rangedExperience;
    private final double rangedExperienceAfterAttack;

    private final double meleeExperience;
    private final double meleeExperienceAfterAttack;

    private final double defenseStrength;
    private final double defenseStrengthAfterAttack;

    private final double defenseExperience;
    private final double defenseExperienceAfterAttack;

    private final boolean isDestroyed;

    @Override
    public String toString() {
        return
            "SS: %5.1f AS: %5.1f RE: %5.1f REA: %5.1f ME: %5.1f MEA: %5.1f DS: %5.1f DSA: %5.1f DE: %5.1f DEA: %5.1f D: %d"
                .formatted(
                    strikeStrength, appliedStrikeStrength,
                    rangedExperience, rangedExperienceAfterAttack,
                    meleeExperience, meleeExperienceAfterAttack,
                    defenseStrength, defenseStrengthAfterAttack, defenseExperience, defenseExperienceAfterAttack,
                    isDestroyed ? 1 : 0);
    }
}
