package com.tsoft.civilization.unit.civil.greatgeneral;

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
 * Movement: 2; Strength: 0; Ranged Strength: 0
 *
 * Notes: Great Generals are produced when a civilization has repeated victories in combat.
 * His presence gives +25% combat bonus to friendly units within 2 tiles. He can sacrifice
 * himself to create a Citadel (a super-Fort), or to trigger a Golden Age. The Citadel can
 * only be built in your own territory (unlike a regular Fort, which can be built in unclaimed territory.
 */
public class GreatGeneral extends AbstractUnit {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int baseProductionCost = 1;

    @Getter
    private final int basePassScore = 2;

    private static final GreatGeneralView VIEW = new GreatGeneralView();

    public GreatGeneral(Civilization civilization) {
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
    public GreatGeneralView getView() {
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
