package com.tsoft.civilization.unit.catalog.greatengineer;

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
import com.tsoft.civilization.unit.action.move.PassCost;
import com.tsoft.civilization.unit.action.move.PassCostList;
import com.tsoft.civilization.unit.action.move.PassCostTable;

import static com.tsoft.civilization.combat.skill.earth.combat.DefenceAgainstAttackSkill.DEFENSE_AGAINST_ATTACK_SKILL;
import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;
import static com.tsoft.civilization.technology.Technology.NAVIGATION;
import static com.tsoft.civilization.unit.action.move.PassCost.UNPASSABLE;

public class GreatEngineerBaseState {

    public UnitBaseState getBaseState() {
        return UnitBaseState.builder()
            .category(UnitCategory.CIVIL)
            .passCostTable(new PassCostTable()
                .add(Desert.class, PassCostList.of(new PassCost(null, 1)))
                .add(Grassland.class, PassCostList.of(new PassCost(null, 1)))
                .add(Lake.class, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 2)))
                .add(Ocean.class, PassCostList.of(new PassCost(null, UNPASSABLE), new PassCost(NAVIGATION, 2)))
                .add(Plains.class, PassCostList.of(new PassCost(null, 1)))
                .add(Snow.class, PassCostList.of(new PassCost(null, 1)))
                .add(Tundra.class, PassCostList.of(new PassCost(null, 1))))
            .combatStrength(CombatStrength.builder()
                .defenseStrength(5)
                .build())
            .movementSkills(SkillList.of(BASE_MOVEMENT_SKILL))
            .combatSkills(SkillList.of(DEFENSE_AGAINST_ATTACK_SKILL))
            .healingSkills(SkillList.of(BASE_HEALING_SKILL))
            .build();
    }
}
