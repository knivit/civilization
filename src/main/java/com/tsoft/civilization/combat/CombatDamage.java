package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CombatDamage {

    private final int damage;

    public CombatDamage add(CombatDamage other) {
        return CombatDamage.builder()
            .damage(damage + other.damage)
            .build();
    }
}
