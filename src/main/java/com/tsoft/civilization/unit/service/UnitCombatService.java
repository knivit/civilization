package com.tsoft.civilization.unit.service;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatExperience;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.unit.AbstractUnit;
import lombok.Getter;

public class UnitCombatService {

    private final SkillService skillService = new SkillService();

    private final AbstractUnit unit;

    @Getter
    private CombatDamage combatDamage = CombatDamage.ZERO;

    @Getter
    private CombatExperience combatExperience = CombatExperience.ZERO;

    private final SkillList<AbstractCombatSkill> combatSkills;
    private final SkillList<AbstractHealingSkill> healingSkills;

    public UnitCombatService(AbstractUnit unit) {
        this.unit = unit;

        Civilization civilization = unit.getCivilization();
        combatSkills = unit.getBaseCombatSkills(civilization);
        healingSkills = unit.getBaseHealingSkills(civilization);
    }

    public CombatStrength calcCombatStrength() {
        CombatStrength strength = skillService.calcCombatStrength(unit, combatSkills);

        return strength
            .minus(combatDamage)
            .add(combatExperience);
    }

    public void addCombatDamage(CombatDamage damage) {
        combatDamage = combatDamage.add(damage);
    }

    public void setCombatExperience(CombatExperience combatExperience) {
        this.combatExperience = combatExperience;
    }

    public SkillList<AbstractCombatSkill> getCombatSkills() {
        return combatSkills;
    }

    public SkillList<AbstractHealingSkill> getHealingSkills() {
        return healingSkills;
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
