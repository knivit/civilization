package com.tsoft.civilization.combat.city;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.SkillList;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class CityCombatStrength implements CombatStrength {

    // Attack
    private final int attackStrength;         // City's ranged attack strength
    private final int rangedAttackRadius;     // Ranged attack radius
    private final int attackExperience;       // Experience during an attack

    // Defense
    private final int defenseStrength;        // City's defense strength
    private final int defenseExperience;      // Experience during a defense

    // Skills
    private final SkillList skills = new SkillList();

    // Status
    private final boolean isDestroyed;


    public CityCombatStrengthBuilder copy() {
        return CityCombatStrength.builder()
            .attackStrength(attackStrength)
            .rangedAttackRadius(rangedAttackRadius)
            .attackExperience(attackExperience)
            .defenseStrength(defenseStrength)
            .defenseExperience(defenseExperience)
            .defenseStrength(defenseStrength)
            .skills(skills)
            .isDestroyed(isDestroyed);
    }
}
