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

    private UnitCategory category;
    private int goldCost;
    private int goldUnitKeepingExpenses;
    private int productionCost;
    private int passScore;
    private CombatStrength combatStrength;
    private List<AbstractMovementSkill> movementSkills;
    private List<AbstractCombatSkill> combatSkills;
    private List<AbstractHealingSkill> healingSkills;
}