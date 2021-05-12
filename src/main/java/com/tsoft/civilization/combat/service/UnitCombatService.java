package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.unit.AbstractUnit;
import lombok.Getter;

public class UnitCombatService {

    private final SkillService skillService = new SkillService();

    private final AbstractUnit unit;

    @Getter
    private CombatDamage combatDamage = CombatDamage.builder()
        .damage(0)
        .build();

    private final SkillMap<AbstractCombatSkill> combatSkills;
    private final SkillMap<AbstractHealingSkill> healingSkills;

    public UnitCombatService(AbstractUnit unit) {
        this.unit = unit;

        int era = unit.getWorld().getEra();
        combatSkills = unit.getBaseCombatSkills(era);
        healingSkills = unit.getBaseHealingSkills(era);
    }

    public CombatStrength calcCombatStrength() {
        CombatStrength strength = skillService.calcCombatStrength(unit, combatSkills);
        return strength.minus(combatDamage);
    }

    public void addCombatDamage(CombatDamage damage) {
        combatDamage = combatDamage.add(damage);
    }

    public SkillMap<AbstractCombatSkill> getCombatSkills() {
        return combatSkills.unmodifiable();
    }

    public SkillMap<AbstractHealingSkill> getHealingSkills() {
        return healingSkills.unmodifiable();
    }

    public void addCombatSkill(AbstractCombatSkill skill) {
        combatSkills.put(skill, SkillLevel.ONE);
    }

    public void addHealingSkill(AbstractHealingSkill skill) {
        healingSkills.put(skill, SkillLevel.ONE);
    }

    public void startEra() {
        // TODO update old unit with a new type
    }

    public void startYear() {

    }

    public void stopYear() {
        combatDamage = skillService.applyHealingSkills(unit, healingSkills);
    }
}
