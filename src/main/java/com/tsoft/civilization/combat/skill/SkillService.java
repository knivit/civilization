package com.tsoft.civilization.combat.skill;

import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.unit.AbstractUnit;

import java.util.Map;

public class SkillService {

    public CombatStrength calcCombatStrength(HasCombatStrength unit, SkillMap<AbstractCombatSkill> skills) {
        CombatStrength strength = unit.getBaseCombatStrength(unit.getCivilization());

        for (Map.Entry<AbstractCombatSkill, SkillLevel> skill : skills) {
            if (SkillType.ACCUMULATOR.equals(skill.getKey().getSkillType())) {
                SkillLevel level = skill.getValue();
                CombatStrength addon = skill.getKey().getCombatStrength(unit, level);
                strength = strength.add(addon);
            }
        }

        return strength;
    }

    public CombatStrength calcCombatStrengthForAttack(HasCombatStrength attacker, SkillMap<AbstractCombatSkill> attackerSkills,
                                                      HasCombatStrength victim, SkillMap<AbstractCombatSkill> victimSkills) {
        CombatStrength attackerStrength = calcCombatStrength(attacker, attackerSkills);

        // modify according to victim's skill against the attacker
        CombatStrength modifier = CombatStrength.ZERO;
        for (Map.Entry<AbstractCombatSkill, SkillLevel> skill : victimSkills) {
            if (SkillType.ATTACKER_MODIFIER.equals(skill.getKey().getSkillType())) {
                SkillLevel level = skill.getValue();
                CombatStrength addon = skill.getKey().getCombatStrength(attacker, attackerStrength, level);
                modifier = modifier.add(addon);
            }
        }

        return attackerStrength.add(modifier);
    }

    public CombatDamage applyHealingSkills(HasCombatStrength unit, SkillMap<AbstractHealingSkill> skills) {
        CombatDamage damage = unit.getCombatDamage();

        for (Map.Entry<AbstractHealingSkill, SkillLevel> skill : skills) {
            SkillLevel level = skill.getValue();
            damage = skill.getKey().heal(unit, damage, level);
        }

        return damage;
    }

    public int calcPassScore(AbstractUnit unit, SkillMap<AbstractMovementSkill> skills) {
        int passScore = unit.getBasePassScore(unit.getCivilization());

        for (Map.Entry<AbstractMovementSkill, SkillLevel> skill : skills) {
            SkillLevel level = skill.getValue();
            passScore += skill.getKey().getPassScore(unit, level);
        }

        return passScore;
    }
}
