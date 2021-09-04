package com.tsoft.civilization.unit.civil.settlers;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;
import lombok.Getter;

import java.util.UUID;

import static com.tsoft.civilization.combat.skill.earth.combat.AttackOnPlainTerrainSkill.ATTACK_ON_PLAIN_TERRAIN_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.AttackOnRoughTerrainSkill.ATTACK_ON_ROUGH_TERRAIN_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.DefenseAgainstRangedAttackSkill.DEFENSE_AGAINST_RANGED_ATTACK_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.HillVantageCombatSkill.HILL_VANTAGE_COMBAT_SKILL;
import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;

/**
 * Movement: 2;
 * Strength: 0;
 * Ranged Strength: 0;
 * Cost: 106 hammers;
 * Requires Resource: none
 * Technology: (none)
 *
 * Abilities: Found City (B): This order causes the Settler to found a city at its current location.
 * The Settler is consumed in this process.
 * Notes: Growth of the City is stopped while this Units is being built. Settlers may only be built
 * in Cities with at least 2 Citizens. This is the only unit in the game with regional variation:
 * in addition to regional costume, the European version has a mule, the Native American version has a Llama,
 * the Asian version has a water buffalo, and the African/Middle Eastern version has a camel.
 */
public class Settlers extends AbstractUnit {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int baseProductionCost = 25;

    @Getter
    private final int basePassScore = 5;

    private static final SettlersView VIEW = new SettlersView();

    public Settlers(Civilization civilization) {
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
        return new SkillMap<>(
            ATTACK_ON_PLAIN_TERRAIN_SKILL, SkillLevel.ONE,
            ATTACK_ON_ROUGH_TERRAIN_SKILL, SkillLevel.ONE,
            HILL_VANTAGE_COMBAT_SKILL, SkillLevel.ONE,

            DEFENSE_AGAINST_RANGED_ATTACK_SKILL, SkillLevel.ONE
        );
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
    public SettlersView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() < Year.MODERN_ERA;
    }

    @Override
    public int getGoldCost(Civilization civilization) {
        return 200;
    }
}
