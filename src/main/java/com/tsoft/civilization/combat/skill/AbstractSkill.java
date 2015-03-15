package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.combat.HasCombatStrength;

public abstract class AbstractSkill {
    public abstract int getAttackStrikePercent(HasCombatStrength attacker, HasCombatStrength defender);

    public abstract int getTargetStrikeOnDefensePercent(HasCombatStrength attacker, HasCombatStrength defender);
}
