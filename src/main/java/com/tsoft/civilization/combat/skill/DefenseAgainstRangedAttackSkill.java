package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.unit.util.UnitType;

public class DefenseAgainstRangedAttackSkill extends AbstractSkill {
    @Override
    public int getAttackStrikePercent(HasCombatStrength attacker, HasCombatStrength defender) {
        int value = 0;
        UnitType attackerType = attacker.getUnitType();
        if (UnitType.MILITARY_RANGED.equals(attackerType)) {
            value += -15;
        }
        return value;
    }

    @Override
    public int getTargetStrikeOnDefensePercent(HasCombatStrength attacker, HasCombatStrength defender) {
        return 0;
    }
}
