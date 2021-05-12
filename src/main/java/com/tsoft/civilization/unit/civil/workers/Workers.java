package com.tsoft.civilization.unit.civil.workers;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.civilization.Civilization;
import lombok.Getter;

import java.util.UUID;

import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;

/**
 * Movement: 2;
 * Strength: 0;
 * Ranged Strength: 0;
 * Cost: 70 hammers;
 * Requires Resource: none
 * Technology: (none)
 *
 * Abilities: May create or repair land-based tile improvements. May clear Forest in 3 turns,
 * Jungle in 6 turns, and Marsh in 5 turns (with appropriate technologies).
 * Notes: Workers can build various tile improvements, build roads and railroads, clear forests and jungle,
 * and drain marshes. They can also embark on water like other land units (with the proper promotion).
 * There is no suspension of city growth during production as there is with the Settler,
 * so you may construct a Worker right away without hampering your new city's starting growth.
 * As civilian units, Workers have no defense and so are captured if attacked by an enemy unit.
 */
public class Workers extends AbstractUnit {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int baseProductionCost = 40;

    @Getter
    private final int basePassScore = 5;

    private static final WorkersView VIEW = new WorkersView();

    public Workers(Civilization civilization) {
        super(civilization);
    }

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.CIVIL;
    }

    @Override
    public CombatStrength getBaseCombatStrength(int era) {
        return CombatStrength.builder()
            .defenseStrength(0)
            .build();
    }

    @Override
    public SkillMap<AbstractCombatSkill> getBaseCombatSkills(int era) {
        return new SkillMap<>();
    }

    @Override
    public SkillMap<AbstractHealingSkill> getBaseHealingSkills(int era) {
        return new SkillMap<>(
            BASE_HEALING_SKILL, SkillLevel.ONE
        );
    }

    @Override
    public SkillMap<AbstractMovementSkill> getBaseMovementSkills() {
        return new SkillMap<>(
            BASE_MOVEMENT_SKILL, SkillLevel.ONE
        );
    }

    @Override
    public WorkersView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return true;
    }

    @Override
    public int getGoldCost(Civilization civilization) {
        return 200;
    }
}
