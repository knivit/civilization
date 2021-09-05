package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CombatDamage {

    public static final CombatDamage ZERO = CombatDamage.builder().damage(0).build();

    private final int damage;

    public CombatDamage add(CombatDamage other) {
        return CombatDamage.builder()
            .damage(damage + other.damage)
            .build();
    }
}
