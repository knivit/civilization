package com.tsoft.civilization.combat.city;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CityCombatDamage {

    private final int citizenDamage;
    private final int defensiveBuildingDamage;
}
