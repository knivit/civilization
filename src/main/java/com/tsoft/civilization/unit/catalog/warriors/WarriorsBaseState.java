package com.tsoft.civilization.unit.catalog.warriors;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.SkillList;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.lake.Lake;
import com.tsoft.civilization.tile.terrain.ocean.Ocean;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.snow.Snow;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;
import com.tsoft.civilization.unit.UnitBaseState;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.unit.UnitMilitaryType;
import com.tsoft.civilization.unit.service.move.PassCost;
import com.tsoft.civilization.unit.service.move.PassCostList;
import com.tsoft.civilization.unit.service.move.PassCostTable;

import static com.tsoft.civilization.combat.skill.earth.combat.AttackOnPlainTerrainSkill.ATTACK_ON_PLAIN_TERRAIN_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.AttackOnRoughTerrainSkill.ATTACK_ON_ROUGH_TERRAIN_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.CanConquerCitySkill.CAN_CONQUER_CITY_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.DefenseAgainstRangedAttackSkill.DEFENSE_AGAINST_RANGED_ATTACK_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.HillVantageCombatSkill.HILL_VANTAGE_COMBAT_SKILL;
import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;
import static com.tsoft.civilization.technology.Technology.NAVIGATION;
import static com.tsoft.civilization.unit.service.move.PassCost.UNPASSABLE;

public class WarriorsBaseState {

    public UnitBaseState getBaseState() {
        return UnitBaseState.builder()
            .category(UnitCategory.MILITARY_MELEE)
            .unitMilitaryType(UnitMilitaryType.MELEE_UNITS)
            .goldCost(200)
            .productionCost(40)
            .passCostTable(new PassCostTable()
                .add(Desert.class, PassCostList.of(new PassCost(null, 1)))
                .add(Grassland.class, PassCostList.of(new PassCost(null, 1)))
                .add(Lake.class, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 2)))
                .add(Ocean.class, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 2)))
                .add(Plains.class, PassCostList.of(new PassCost(null, 1)))
                .add(Snow.class, PassCostList.of(new PassCost(null, 1)))
                .add(Tundra.class, PassCostList.of(new PassCost(null, 1))))
            .goldUnitKeepingExpenses(3)
            .combatStrength(CombatStrength.builder()
                .meleeAttackStrength(10)
                .meleeBackFireStrength(5)
                .defenseStrength(20)
                .build())
            .movementSkills(SkillList.of(BASE_MOVEMENT_SKILL))
            .combatSkills(SkillList.of(ATTACK_ON_PLAIN_TERRAIN_SKILL, ATTACK_ON_ROUGH_TERRAIN_SKILL, HILL_VANTAGE_COMBAT_SKILL, CAN_CONQUER_CITY_SKILL, DEFENSE_AGAINST_RANGED_ATTACK_SKILL))
            .healingSkills(SkillList.of(BASE_HEALING_SKILL))
            .build();
    }
}
