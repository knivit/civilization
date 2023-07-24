package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.HasCombatStrength;

public interface AbstractHealingSkill extends AbstractSkill {

    CombatDamage heal(HasCombatStrength unit, CombatDamage damage);
}
