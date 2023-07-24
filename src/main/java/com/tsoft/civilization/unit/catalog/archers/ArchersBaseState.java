package com.tsoft.civilization.unit.catalog.archers;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.UnitBaseState;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.unit.UnitMilitaryType;

import static com.tsoft.civilization.combat.skill.earth.combat.DefenseAgainstRangedAttackSkill.DEFENSE_AGAINST_RANGED_ATTACK_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.HillVantageCombatSkill.HILL_VANTAGE_COMBAT_SKILL;
import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;
import static java.util.Arrays.asList;

public class ArchersBaseState {

    public UnitBaseState getBaseState() {
        return UnitBaseState.builder()
            .category(UnitCategory.MILITARY_RANGED)
            .unitMilitaryType(UnitMilitaryType.ARCHERY_UNITS)
            .goldCost(200)
            .productionCost(40)
            .passScore(1)
            .goldUnitKeepingExpenses(3)
            .combatStrength(CombatStrength.builder()
                .rangedAttackStrength(7)
                .rangedAttackRadius(2)
                .defenseStrength(5)
                .build())
            .movementSkills(asList(BASE_MOVEMENT_SKILL))
            .combatSkills(asList(HILL_VANTAGE_COMBAT_SKILL, DEFENSE_AGAINST_RANGED_ATTACK_SKILL))
            .healingSkills(asList(BASE_HEALING_SKILL))
            .build();
    }
}
