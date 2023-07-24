package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.unit.AbstractUnit;

public class SkillService {

    public CombatStrength calcCombatStrength(HasCombatStrength unit, SkillList<AbstractCombatSkill> skills) {
        CombatStrength strength = unit.getBaseCombatStrength(unit.getCivilization());

        for (AbstractCombatSkill skill : skills) {
            if (SkillType.ACCUMULATOR.equals(skill.getSkillType())) {
                CombatStrength addon = skill.getCombatStrength(unit);
                strength = strength.add(addon);
            }
        }

        return strength;
    }

    public CombatStrength calcCombatStrengthForAttack(HasCombatStrength attacker, SkillList<AbstractCombatSkill> attackerSkills,
                                                      HasCombatStrength victim, SkillList<AbstractCombatSkill> victimSkills) {
        CombatStrength attackerStrength = calcCombatStrength(attacker, attackerSkills);

        // modify according to victim's skill against the attacker
        CombatStrength modifier = CombatStrength.ZERO;
        for (AbstractCombatSkill skill : victimSkills) {
            if (SkillType.ATTACKER_MODIFIER.equals(skill.getSkillType())) {
                CombatStrength addon = skill.getCombatStrength(attacker, attackerStrength);
                modifier = modifier.add(addon);
            }
        }

        return attackerStrength.add(modifier);
    }

    public CombatDamage applyHealingSkills(HasCombatStrength unit, SkillList<AbstractHealingSkill> skills) {
        CombatDamage damage = unit.getCombatDamage();

        for (AbstractHealingSkill skill : skills) {
            damage = skill.heal(unit, damage);
        }

        return damage;
    }

    public int calcPassScore(AbstractUnit unit, SkillList<AbstractMovementSkill> skills) {
        int passScore = 1;

        for (AbstractMovementSkill skill : skills) {
            passScore += skill.getPassScore(unit);
        }

        return passScore;
    }
}
