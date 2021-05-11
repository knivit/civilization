package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.unit.UnitCategory;
import lombok.Getter;

import static com.tsoft.civilization.combat.skill.L10nSkill.DEFENSE_AGAINST_RANGED_ATTACK;

public class DefenseAgainstRangedAttackSkill implements AbstractSkill {

    @Getter
    private final L10n localizedName = DEFENSE_AGAINST_RANGED_ATTACK;

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
