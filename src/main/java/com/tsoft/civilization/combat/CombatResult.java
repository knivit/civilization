package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public final class CombatResult {

    // Attacker
    private final int attackerDefenseStrength;
    private final int attackerDefenseStrengthAfterAttack;
    private final int attackerAttackExperience;
    private final int attackerAttackExperienceAfterAttack;
    private final boolean attackerDestroyed;

    private final int originalStrikeStrength;
    private final int appliedStrikeStrength;            // originalStrikeStrength - targetDefenseExperience

    // Target
    private final int targetDefenseStrength;
    private final int targetDefenseStrengthAfterAttack; // targetDefenseStrength - appliedStrikeStrength
    private final int targetDefenseExperience;
    private final int targetDefenseExperienceAfterAttack;
    private final boolean targetDestroyed;

    private final int targetBackFireStrength;

    // Combat status
    private final boolean done;
    private final boolean skippedAsRangedUndershoot;
    private final boolean skippedAsNoTargetsToAttack;
    private final boolean skippedAsMeleeNotEnoughPassScore;
}
