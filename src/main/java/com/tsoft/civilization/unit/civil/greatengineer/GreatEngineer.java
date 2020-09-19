package com.tsoft.civilization.unit.civil.greatengineer;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

import java.util.UUID;

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
    public static final GreatEngineer STUB = new GreatEngineer();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
            .setStrength(0);

    private static final GreatEngineerView VIEW = new GreatEngineerView();

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.CIVIL;
    }

    @Override
    protected CombatStrength getBaseCombatStrength() {
        return COMBAT_STRENGTH;
    }

    @Override
    public void initPassScore() {
        setPassScore(2);
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
    public int getProductionCost() {
        return -1;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }

    @Override
    public int getGoldCost() {
        return -1;
    }
}
