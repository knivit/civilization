package com.tsoft.civilization.unit.catalog.warriors;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.UnitBaseState;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.unit.UnitMilitaryType;

import static com.tsoft.civilization.combat.skill.earth.combat.AttackOnPlainTerrainSkill.ATTACK_ON_PLAIN_TERRAIN_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.AttackOnRoughTerrainSkill.ATTACK_ON_ROUGH_TERRAIN_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.CanConquerCitySkill.CAN_CONQUER_CITY_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.DefenseAgainstRangedAttackSkill.DEFENSE_AGAINST_RANGED_ATTACK_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.HillVantageCombatSkill.HILL_VANTAGE_COMBAT_SKILL;
import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;
import static java.util.Arrays.asList;

public class WarriorsBaseState {

    public UnitBaseState getBaseState() {
        return UnitBaseState.builder()
            .category(UnitCategory.MILITARY_MELEE)
            .unitMilitaryType(UnitMilitaryType.MELEE_UNITS)
            .goldCost(200)
            .productionCost(40)
            .passScore(1)
            .goldUnitKeepingExpenses(3)
            .combatStrength(CombatStrength.builder()
                .meleeAttackStrength(10)
                .meleeBackFireStrength(5)
                .defenseStrength(20)
                .build())
            .movementSkills(asList(BASE_MOVEMENT_SKILL))
            .combatSkills(asList(ATTACK_ON_PLAIN_TERRAIN_SKILL, ATTACK_ON_ROUGH_TERRAIN_SKILL, HILL_VANTAGE_COMBAT_SKILL, CAN_CONQUER_CITY_SKILL, DEFENSE_AGAINST_RANGED_ATTACK_SKILL))
            .healingSkills(asList(BASE_HEALING_SKILL))
            .build();
    }
}
