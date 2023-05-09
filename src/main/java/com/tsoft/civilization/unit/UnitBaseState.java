package com.tsoft.civilization.unit;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.AbstractCombatSkill;
import com.tsoft.civilization.combat.skill.AbstractHealingSkill;
import com.tsoft.civilization.combat.skill.AbstractMovementSkill;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UnitBaseState {

    private final UnitCategory category;
    private final UnitMilitaryType unitMilitaryType;
    private final int goldCost;
    private final int goldUnitKeepingExpenses;
    private final int productionCost;
    private final int passScore;
    private final CombatStrength combatStrength;
    private final List<AbstractMovementSkill> movementSkills;
    private final List<AbstractCombatSkill> combatSkills;
    private final List<AbstractHealingSkill> healingSkills;
}