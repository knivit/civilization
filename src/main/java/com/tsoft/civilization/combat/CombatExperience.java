package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CombatExperience {

    public static final CombatExperience ZERO = CombatExperience.builder()
        .rangedAttackExperience(0)
        .meleeAttackExperience(0)
        .defenseExperience(0)
        .build();

    private final double rangedAttackExperience;
    private final double meleeAttackExperience;
    private final double defenseExperience;

    public CombatExperience add(CombatExperience other) {
        return CombatExperience.builder()
            .rangedAttackExperience(rangedAttackExperience + other.rangedAttackExperience)
            .meleeAttackExperience(meleeAttackExperience + other.meleeAttackExperience)
            .defenseExperience(defenseExperience + other.defenseExperience)
            .build();
    }
}
