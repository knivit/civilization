package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;

public interface AbstractCombatSkill extends AbstractSkill {

    SkillType getSkillType();

    default CombatStrength getCombatStrength(HasCombatStrength unit, SkillLevel level) {
        return CombatStrength.ZERO;
    }

    default CombatStrength getCombatStrength(HasCombatStrength unit, CombatStrength attackerCombatStrength, SkillLevel level) {
        return CombatStrength.ZERO;
    }
}
