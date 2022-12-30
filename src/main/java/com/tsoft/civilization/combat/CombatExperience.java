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
}
