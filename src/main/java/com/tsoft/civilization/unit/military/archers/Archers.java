package com.tsoft.civilization.unit.military.archers;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;
import lombok.Getter;

import java.util.UUID;

import static com.tsoft.civilization.combat.skill.earth.combat.HillVantageCombatSkill.HILL_VANTAGE_COMBAT_SKILL;
import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.combat.skill.earth.movement.BaseMovementSkill.BASE_MOVEMENT_SKILL;

/**
 * Archer
 * ------
 * Archery unit of the Ancient era.
 *   40 - Production cost
 *    5 - Combat strength
 *    2 - Moves
 *    2 - Range
 *    7 - Ranged strength
 *
 * Required Technology: Archery
 * Upgrades to: Crossbowman (Composite Bowman)
 * Obsolete with: Machinery
 * May not melee attack
 *
 * First ranged unit in the game.
 *
 * Abilities: May not melee attack, -25% vs. Cities
 * Note: The bombardment range for archers is 2 hexes.
 *
 * Strategy
 * --------
 * The Archer is the earliest ranged unit, armed with the first simple bows. Because it can attack enemies with impunity
 * up to 2 hexes away, it is critically important in the first skirmishes with Barbarians, or when attacking enemy cities
 * early in the game.
 *
 * However, the Archer is fairly weak when attacked by other units, especially in melee. Assign it to garrison cities or
 * keep it behind your front lines, protected by melee units. Also, position it on hills whenever possible - these vantage
 * points will allow it to use its full range while also using the natural defense of the terrain.
 *
 * Civilopedia entry
 * -----------------
 * Archery may have been invented as long as 10,000-15,000 years ago, during the Paleolithic Age. It may have been
 * invented in one place and spread, or, more likely, archery was invented in multiple places around the world.
 * In the centuries before gunpowder, most ancient civilizations employed archers in their armies.
 * The Korean armies possessed particularly fine archers during the 600-year Choson period, while the English longbow
 * dominated the European battlefields during the 14th and 15th centuries.
 */
public class Archers extends AbstractUnit {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int baseProductionCost = 40;

    @Getter
    private final int basePassScore = 5;

    @Getter
    private final ArchersView view = new ArchersView();

    @Getter
    private final UnitCategory unitCategory = UnitCategory.MILITARY_RANGED;

    public Archers(Civilization civilization) {
        super(civilization);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public int getGoldCost(Civilization civilization) {
        return 200;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return (civilization.getYear().getEra() == Year.ANCIENT_ERA) &&
            (civilization.isResearched(Technology.ARCHERY));
    }

    @Override
    public CombatStrength getBaseCombatStrength(int era) {
        return CombatStrength.builder()
            .rangedAttackStrength(7)
            .rangedAttackRadius(2)
            .meleeAttackStrength(0)
            .defenseStrength(5)
            .build();
    }

    @Override
    public SkillMap<AbstractCombatSkill> getBaseCombatSkills(int era) {
        return new SkillMap<>(
            HILL_VANTAGE_COMBAT_SKILL, SkillLevel.ONE
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
}
