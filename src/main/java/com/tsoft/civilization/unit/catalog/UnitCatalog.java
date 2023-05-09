package com.tsoft.civilization.unit.catalog;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.UnitMilitaryType;
import com.tsoft.civilization.unit.UnitBaseState;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.unit.UnitType;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.combat.skill.earth.combat.AttackOnPlainTerrainSkill.ATTACK_ON_PLAIN_TERRAIN_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.AttackOnRoughTerrainSkill.ATTACK_ON_ROUGH_TERRAIN_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.CanConquerCitySkill.CAN_CONQUER_CITY_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.DefenseAgainstRangedAttackSkill.DEFENSE_AGAINST_RANGED_ATTACK_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.HillVantageCombatSkill.HILL_VANTAGE_COMBAT_SKILL;
import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;
import static com.tsoft.civilization.unit.UnitType.*;
import static java.util.Arrays.asList;

public final class UnitCatalog {

    private static final Map<UnitType, UnitBaseState> UNITS = new HashMap<>() {{
        put(ARCHERS, UnitBaseState.builder()
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
            .build());

        put(GREAT_ARTIST, UnitBaseState.builder()
            .category(UnitCategory.CIVIL)
            .passScore(1)
            .combatStrength(CombatStrength.builder()
                .defenseStrength(5)
                .build())
            .movementSkills(asList(BASE_MOVEMENT_SKILL))
            .healingSkills(asList(BASE_HEALING_SKILL))
            .build());

        put(GREAT_ENGINEER, UnitBaseState.builder()
            .category(UnitCategory.CIVIL)
            .passScore(1)
            .combatStrength(CombatStrength.builder()
                .defenseStrength(5)
                .build())
            .movementSkills(asList(BASE_MOVEMENT_SKILL))
            .healingSkills(asList(BASE_HEALING_SKILL))
            .build());

        put(GREAT_GENERAL, UnitBaseState.builder()
            .category(UnitCategory.CIVIL)
            .passScore(1)
            .combatStrength(CombatStrength.builder()
                .defenseStrength(5)
                .build())
            .movementSkills(asList(BASE_MOVEMENT_SKILL))
            .healingSkills(asList(BASE_HEALING_SKILL))
            .build());

        put(GREAT_MERCHANT, UnitBaseState.builder()
            .category(UnitCategory.CIVIL)
            .passScore(1)
            .combatStrength(CombatStrength.builder()
                .defenseStrength(5)
                .build())
            .movementSkills(asList(BASE_MOVEMENT_SKILL))
            .healingSkills(asList(BASE_HEALING_SKILL))
            .build());

        put(GREAT_SCIENTIST, UnitBaseState.builder()
            .category(UnitCategory.CIVIL)
            .passScore(1)
            .combatStrength(CombatStrength.builder()
                .defenseStrength(5)
                .build())
            .movementSkills(asList(BASE_MOVEMENT_SKILL))
            .healingSkills(asList(BASE_HEALING_SKILL))
            .build());

        put(SETTLERS, UnitBaseState.builder()
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
            .build());

        put(WARRIORS, UnitBaseState.builder()
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
            .build());

        put(WORKERS, UnitBaseState.builder()
            .category(UnitCategory.CIVIL)
            .goldCost(200)
            .productionCost(40)
            .passScore(1)
            .goldUnitKeepingExpenses(2)
            .combatStrength(CombatStrength.builder()
                .defenseStrength(10)
                .build())
            .movementSkills(asList(BASE_MOVEMENT_SKILL))
            .healingSkills(asList(BASE_HEALING_SKILL))
            .build());
    }};

    private UnitCatalog() { }

    public static UnitBaseState getBaseState(UnitType type) {
        return UNITS.get(type);
    }
}
