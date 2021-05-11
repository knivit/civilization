package com.tsoft.civilization.combat.ranged;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class RangedCombatDamage {

    private final int meleeUnitDamage;
    private final int rangedUnitDamage;
    private final int citizenDamage;
    private final int defensiveBuildingDamage;
}
