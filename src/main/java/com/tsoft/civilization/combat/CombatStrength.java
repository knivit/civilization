package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CombatStrength {

    private static final CombatStrength ZERO = CombatStrength.builder().build();

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

    public CombatStrength add(CombatStrength other) {
        return add(other, this.rangedAttackRadius, this.canConquerCity, this.isDestroyed);
    }

    public CombatStrength setRangedAttackRadius(int rangedAttackRadius) {
        return add(ZERO, rangedAttackRadius, this.canConquerCity, this.isDestroyed);
    }

    public CombatStrength setIsDestroyed(boolean isDestroyed) {
        return add(ZERO, this.rangedAttackRadius, this.canConquerCity, isDestroyed);
    }

    private CombatStrength add(CombatStrength other, int rangedAttackRadius, boolean canConquerCity, boolean isDestroyed) {
        return CombatStrength.builder()
            .meleeAttackStrength(meleeAttackStrength + other.meleeAttackStrength)
            .targetBackFireStrength(targetBackFireStrength + other.targetBackFireStrength)
            .defenseStrength(defenseStrength + other.defenseStrength)
            .rangedAttackStrength(rangedAttackStrength + other.rangedAttackStrength)
            .rangedAttackRadius(rangedAttackRadius)
            .attackExperience(attackExperience + other.attackExperience)
            .defenseExperience(defenseExperience + other.defenseExperience)
            .canConquerCity(canConquerCity)
            .isDestroyed(isDestroyed)
            .build();
    }
}
