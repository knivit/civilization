package com.tsoft.civilization.combat.melee;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MeleeCombatDamage {

    private final int meleeUnitDamage;
    private final int rangedUnitDamage;
    private final int citizenDamage;
    private final int defensiveBuildingDamage;

}
