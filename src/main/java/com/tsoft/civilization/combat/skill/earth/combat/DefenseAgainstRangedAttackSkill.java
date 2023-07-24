package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.unit.UnitCategory;
import lombok.Getter;

/**
 * Minus 15% to ranged attacker's attack strength
 */
public class DefenseAgainstRangedAttackSkill implements AbstractCombatSkill {

    public static final AbstractCombatSkill DEFENSE_AGAINST_RANGED_ATTACK_SKILL = new DefenseAgainstRangedAttackSkill();

    @Getter
    private final SkillName skillName = SkillName.DEFENSE_AGAINST_RANGED_ATTACK;

    @Getter
    private final L10n localizedName = L10nSkill.DEFENSE_AGAINST_RANGED_ATTACK;

    @Getter
    private final SkillType skillType = SkillType.ATTACKER_MODIFIER;

    private DefenseAgainstRangedAttackSkill() { }

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
