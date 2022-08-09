package com.tsoft.civilization.combat.skill.earth.heal;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractHealingSkill;
import com.tsoft.civilization.combat.skill.L10nSkill;
import com.tsoft.civilization.combat.skill.SkillLevel;
import com.tsoft.civilization.improvement.city.City;
import lombok.Getter;

public class BaseHealingSkill implements AbstractHealingSkill {

    public static final BaseHealingSkill BASE_HEALING_SKILL = new BaseHealingSkill();

    @Getter
    private final L10n localizedName = L10nSkill.BASE_HEALING_SKILL;

    @Override
    public CombatDamage heal(HasCombatStrength unit, CombatDamage combatDamage, SkillLevel level) {
        if (unit.getUnitCategory().isCity()) {
            return cityHeal((City) unit, combatDamage, level);
        }

        return unitHeal(unit, combatDamage, level);
    }

    // Cities heal automatically each turn (being constantly repaired by their inhabitants),
    // making them even harder to capture. The amount healed is about 10 - 15% of its total health
    private CombatDamage cityHeal(City city, CombatDamage combatDamage, SkillLevel level) {
        return heal(city.getCitizenCount() * level.getValue(), combatDamage);
    }

    private CombatDamage unitHeal(HasCombatStrength unit, CombatDamage combatDamage, SkillLevel level) {
        return heal(level.getValue(), combatDamage);
    }

    private CombatDamage heal(double heal, CombatDamage combatDamage) {
        double damage = combatDamage.getDamage();

        if (damage > 0) {
            if (heal > damage) {
                heal = damage;
            }

            return CombatDamage.builder()
                .damage(damage - heal)
                .build();
        }

        return combatDamage;
    }
}
