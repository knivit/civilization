package com.tsoft.civilization.combat.melee;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.city.CityCombatStrength;
import com.tsoft.civilization.combat.skill.SkillList;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MeleeCombatStrength implements CombatStrength {

    // Attack
    private final int attackStrength;         // Melee units' attack strength
    private final int attackExperience;       // Experience during an attack

    // Backfire
    private final int backFireStrength;       // Back fire strength

    // Defense
    private final int defenseStrength;        // Unit's defense strength
    private final int defenseExperience;      // Experience during a defense

    // Skills
    private final SkillList skills = new SkillList();

    // Options
    private final boolean isDestroyed;        // Was destroyed during a step

    public MeleeCombatStrengthBuilder copy() {
        MeleeCombatStrengthBuilder strength = MeleeCombatStrength.builder()
            .attackStrength(attackStrength)
            .attackExperience(attackExperience)
            .backFireStrength(backFireStrength)
            .defenseStrength(defenseStrength)
            .defenseExperience(defenseExperience)
            .isDestroyed(isDestroyed);

            strength.(skills)
    }
}
