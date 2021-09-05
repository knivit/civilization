package com.tsoft.civilization.combat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CombatStrength {

    public static final CombatStrength ZERO = CombatStrength.builder().build();

    // Ranged attack
    private final int rangedAttackLevel;      // Unit's level in ranged attacks
    private final int rangedAttackStrength;   // Unit's ranged attack strength
    private final int rangedAttackRadius;     // Ranged attack radius
    private final int rangedAttackExperience; // Experience during an attack
    private final int rangedBackFireStrength; // Backfire strength

    // Melee attack
    private final int meleeAttackLevel;       // Unit's level in melee attacks
    private final int meleeAttackStrength;    // Unit's ranged attack strength
    private final int meleeAttackExperience;  // Experience during an attack
    private final int meleeBackFireStrength;  // Backfire strength

    // Defense
    private final int defenseLevel;           // Unit's defense level
    private final int defenseStrength;        // Unit's defense strength
    private final int defenseExperience;      // Experience during a defense

    // Options
    private final boolean isDestroyed;        // Was destroyed during a step

    public CombatStrength add(CombatStrength other) {
        return CombatStrength.builder()
            .rangedAttackStrength(rangedAttackStrength + other.rangedAttackStrength)
            .rangedAttackRadius(rangedAttackRadius + other.rangedAttackRadius)
            .rangedAttackExperience(rangedAttackExperience + other.rangedAttackExperience)
            .rangedBackFireStrength(rangedBackFireStrength + other.rangedBackFireStrength)

            .meleeAttackStrength(meleeAttackStrength + other.meleeAttackStrength)
            .meleeAttackExperience(meleeAttackExperience + other.meleeAttackExperience)
            .meleeBackFireStrength(meleeBackFireStrength + other.meleeBackFireStrength)

            .defenseStrength(defenseStrength + other.defenseStrength)
            .defenseExperience(defenseExperience + other.defenseExperience)

            .build();
    }

    public CombatStrength minus(CombatDamage damage) {
        return copy()
            .defenseStrength(defenseStrength - damage.getDamage())
            .build();
    }

    public CombatStrength add(CombatExperience experience) {
        return copy()
            .rangedAttackExperience(rangedAttackExperience + experience.getRangedAttackExperience())
            .meleeAttackExperience(meleeAttackExperience + experience.getMeleeAttackExperience())
            .defenseExperience(defenseExperience + experience.getDefenseExperience())
            .build();
    }

    public CombatStrength.CombatStrengthBuilder copy() {
        return CombatStrength.builder()
            .rangedAttackStrength(rangedAttackStrength)
            .rangedAttackRadius(rangedAttackRadius)
            .rangedAttackExperience(rangedAttackExperience)
            .rangedBackFireStrength(rangedBackFireStrength)

            .meleeAttackStrength(meleeAttackStrength)
            .meleeAttackExperience(meleeAttackExperience)
            .meleeBackFireStrength(meleeBackFireStrength)

            .defenseStrength(defenseStrength)
            .defenseExperience(defenseExperience)

            .isDestroyed(isDestroyed);
    }
}
