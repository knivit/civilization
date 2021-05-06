package com.tsoft.civilization.unit.military.archers;

import com.tsoft.civilization.combat.service.CombatStrength;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;
import lombok.Getter;

import java.util.UUID;

/**
 * Movement: 2;
 * Strength: 4;
 * Ranged Strength: 6;
 * Range: 2;
 * Cost: 70;
 * Requires Resource: none
 * Technology: Archery;
 * Obsolete with: Machinery;
 * Upgrades to: Crossbowman
 *
 * Abilities: May not melee attack, -25% vs. Cities
 * Note: The bombardment range for archers is 2 hexes.
 */
public class Archers extends AbstractUnit {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final CombatStrength baseCombatStrength = CombatStrength.builder()
        .meleeAttackStrength(5)
        .targetBackFireStrength(5)
        .defenseStrength(30)
        .rangedAttackStrength(15)
        .rangedAttackRadius(2)
        .canConquerCity(false)
        .build();

    @Getter
    private final int baseProductionCost = 40;

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
    public void initPassScore() {
        setPassScore(5);
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
}
