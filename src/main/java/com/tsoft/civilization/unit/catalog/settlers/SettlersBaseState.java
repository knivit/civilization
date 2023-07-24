package com.tsoft.civilization.unit.catalog.settlers;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.UnitBaseState;
import com.tsoft.civilization.unit.UnitCategory;

import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;
import static java.util.Arrays.asList;

public class SettlersBaseState {

    public UnitBaseState getBaseState() {
        return UnitBaseState.builder()
            .category(UnitCategory.CIVIL)
            .goldCost(200)
            .productionCost(25)
            .passScore(1)
            .goldUnitKeepingExpenses(2)
            .combatStrength(CombatStrength.builder()
                .defenseStrength(0)
                .build())
            .movementSkills(asList(BASE_MOVEMENT_SKILL))
            .healingSkills(asList(BASE_HEALING_SKILL))
            .build();
    }
}
