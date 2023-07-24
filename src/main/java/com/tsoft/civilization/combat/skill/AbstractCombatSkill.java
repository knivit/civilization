package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;

public interface AbstractCombatSkill extends AbstractSkill {

    SkillName getSkillName();

    SkillType getSkillType();

    default CombatStrength getCombatStrength(HasCombatStrength unit) {
        return CombatStrength.ZERO;
    }

    default CombatStrength getCombatStrength(HasCombatStrength unit, CombatStrength attackerCombatStrength) {
        return CombatStrength.ZERO;
    }
}
