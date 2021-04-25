package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public final class CombatStrength {

    // Attack
    private final int meleeAttackStrength;    // Melee units' attack strength
    private final int targetBackFireStrength; // Any units' back fire
    private final int rangedAttackStrength;   // Unit's ranged attack strength
    private final int rangedAttackRadius;     // Ranged attack radius
    private final int attackExperience;       // Experience during an attack

    // Defense
    private final int defenseStrength;        // Unit's defense strength
    private final int defenseExperience;      // Experience during a defense

    // Options
    private final boolean canConquerCity;     // Unit's options
    private final boolean isDestroyed;        // Was destroyed during a step

    public CombatStrengthBuilder copy() {
        return CombatStrength.builder()
            .meleeAttackStrength(meleeAttackStrength)
            .targetBackFireStrength(targetBackFireStrength)
            .rangedAttackStrength(rangedAttackStrength)
            .rangedAttackRadius(rangedAttackRadius)
            .attackExperience(attackExperience)
            .defenseStrength(defenseStrength)
            .defenseExperience(defenseExperience)
            .canConquerCity(canConquerCity)
            .isDestroyed(isDestroyed);
    }
}
