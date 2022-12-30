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
            "SS:" + strikeStrength + ' ' +
            "AS:" + appliedStrikeStrength + ' ' +
            "RE:" + rangedExperience + ' ' +
            "REA:" + rangedExperienceAfterAttack + ' ' +
            "ME:" + meleeExperience + ' ' +
            "MEA:" + meleeExperienceAfterAttack + ' ' +
            "DS:" + defenseStrength + ' ' +
            "DSA:" + defenseStrengthAfterAttack + ' ' +
            "DE:" + defenseExperience + ' ' +
            "DEA:" + defenseExperienceAfterAttack + ' ' +
            "D:" + (isDestroyed ? 1 : 0);
    }
}
