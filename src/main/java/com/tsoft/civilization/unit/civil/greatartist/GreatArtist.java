package com.tsoft.civilization.unit.civil.greatartist;

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
 * Notes: Can sacrifice itself to construct a Landmark improvement, trigger a Golden Age, or "culture bomb"
 * a tile within 1 space of your territory, putting that tile and all adjacent tiles into your territory.
 * The Culture Bomb target tile cannot be in foreign territory, though it can be in unclaimed territory.
 */
public class GreatArtist extends AbstractUnit {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int baseProductionCost = 1;

    @Getter
    private final int basePassScore = 2;

    private static final GreatArtistView VIEW = new GreatArtistView();

    public GreatArtist(Civilization civilization) {
        super(civilization);
    }

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.CIVIL;
    }

    @Override
    public CombatStrength getBaseCombatStrength(int era) {
        return CombatStrength.builder()
            .defenseStrength(20)
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
    public GreatArtistView getView() {
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
