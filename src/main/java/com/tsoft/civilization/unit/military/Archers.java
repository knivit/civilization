package com.tsoft.civilization.unit.military;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.web.view.unit.ArchersView;
import com.tsoft.civilization.world.Civilization;

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
public class Archers extends AbstractUnit<ArchersView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();
    public static final Archers INSTANCE = new Archers();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
            .setMeleeAttackStrength(5)
            .setTargetBackFireStrength(5)
            .setStrength(30)
            .setRangedAttackStrength(15)
            .setRangedAttackRadius(2)
            .setCanConquerCity(false);

    private static final ArchersView VIEW = new ArchersView();

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.MILITARY_RANGED;
    }

    @Override
    protected CombatStrength getBaseCombatStrength() {
        return COMBAT_STRENGTH;
    }

    @Override
    public void initPassScore() {
        setPassScore(5);
    }

    @Override
    public ArchersView getView() {
        return VIEW;
    }

    @Override
    public int getProductionCost() {
        return 40;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return (civilization.getYear().getEra() == Year.ANCIENT_ERA) &&
                (civilization.isResearched(Technology.ARCHERY));
    }

    @Override
    public int getGoldCost() {
        return 200;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
