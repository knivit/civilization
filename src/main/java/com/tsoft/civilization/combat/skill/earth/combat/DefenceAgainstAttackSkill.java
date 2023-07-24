package com.tsoft.civilization.combat.skill.earth.combat;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.L10nSkill;
import com.tsoft.civilization.combat.skill.SkillName;
import com.tsoft.civilization.combat.skill.SkillType;
import com.tsoft.civilization.util.l10n.L10n;
import lombok.Getter;

public class DefenceAgainstAttackSkill implements AbstractCombatSkill {

    public static final AbstractCombatSkill DEFENSE_AGAINST_ATTACK_SKILL = new DefenceAgainstAttackSkill();

    @Getter
    private final SkillName skillName = SkillName.DEFENSE_AGAINST_ATTACK;

    @Getter
    private final L10n localizedName = L10nSkill.DEFENSE_AGAINST_ATTACK;

    @Getter
    private final SkillType skillType = SkillType.ATTACKER_MODIFIER;

    private DefenceAgainstAttackSkill() { }

    @Override
    public CombatStrength getCombatStrength(HasCombatStrength attacker, CombatStrength attackerCombatStrength) {
        return CombatStrength.ZERO;
    }
}
