package com.tsoft.civilization.unit.civil.greatengineer;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;
import lombok.Getter;

import java.util.UUID;

import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;

/**
 * Movement: 2;
 * Strength: 0;
 * Ranged Strength: 0
 *
 * Notes: Can sacrifice himself to construct a Manufactory improvement, to hurry production of a unit,
 * building or wonder, or trigger a Golden Age.
 */
public class GreatEngineer extends AbstractUnit {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int baseProductionCost = 1;

    @Getter
    private final int basePassScore = 2;

    private static final GreatEngineerView VIEW = new GreatEngineerView();

    public GreatEngineer(Civilization civilization) {
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
    public GreatEngineerView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public int getProductionCost(Civilization civilization) {
        return -1;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }

    @Override
    public int getGoldCost(Civilization civilization) {
        return -1;
    }
}
