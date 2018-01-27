package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.unit.UnitCategory;

public class DefenseAgainstRangedAttackSkill extends AbstractSkill {
    @Override
    public int getAttackStrikePercent(HasCombatStrength attacker, HasCombatStrength defender) {
        int value = 0;
        UnitCategory attackerType = attacker.getUnitCategory();
        if (UnitCategory.MILITARY_RANGED.equals(attackerType)) {
            value += -15;
        }
        return value;
    }

    @Override
    public int getTargetStrikeOnDefensePercent(HasCombatStrength attacker, HasCombatStrength defender) {
        return 0;
    }
}
