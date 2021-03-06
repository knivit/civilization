package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.L10nSkill;
import com.tsoft.civilization.combat.skill.SkillLevel;
import com.tsoft.civilization.combat.skill.SkillType;
import com.tsoft.civilization.unit.UnitCategory;
import lombok.Getter;

/**
 * Minus 15% to ranged attacker's attack strength
 */
public class AgainstRangedAttackDefenseSkill implements AbstractCombatSkill {

    public static final AbstractCombatSkill AGAINST_RANGED_ATTACK_DEFENSE_SKILL = new AgainstRangedAttackDefenseSkill();

    @Getter
    private final L10n localizedName = L10nSkill.DEFENSE_AGAINST_RANGED_ATTACK;

    @Getter
    private final SkillType skillType = SkillType.ATTACKER_MODIFIER;

    @Override
    public CombatStrength getCombatStrength(HasCombatStrength attacker, CombatStrength attackerCombatStrength, SkillLevel level) {
        UnitCategory attackerType = attacker.getUnitCategory();

        if (UnitCategory.MILITARY_RANGED.equals(attackerType)) {
            int val = (int)Math.round(attackerCombatStrength.getRangedAttackStrength() * 0.15) * level.getValue();
            return CombatStrength.builder()
                .rangedAttackStrength(-val)
                .build();
        }

        return CombatStrength.ZERO;
    }
}
