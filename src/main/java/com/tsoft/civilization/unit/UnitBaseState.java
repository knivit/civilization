package com.tsoft.civilization.unit;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.AbstractHealingSkill;
import com.tsoft.civilization.combat.skill.AbstractMovementSkill;
import com.tsoft.civilization.combat.skill.SkillList;
import com.tsoft.civilization.unit.service.move.PassCostTable;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UnitBaseState {

    private final UnitCategory category;
    private final UnitMilitaryType unitMilitaryType;
    private final int goldCost;
    private final int goldUnitKeepingExpenses;
    private final int productionCost;
    private final PassCostTable passCostTable;
    private final CombatStrength combatStrength;
    private final SkillList<AbstractMovementSkill> movementSkills;
    private final SkillList<AbstractCombatSkill> combatSkills;
    private final SkillList<AbstractHealingSkill> healingSkills;
}