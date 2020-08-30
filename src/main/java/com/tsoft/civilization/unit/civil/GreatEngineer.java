package com.tsoft.civilization.unit.civil;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.web.view.unit.GreatEngineerView;
import com.tsoft.civilization.world.Civilization;

/**
 * Movement: 2;
 * Strength: 0;
 * Ranged Strength: 0
 *
 * Notes: Can sacrifice himself to construct a Manufactory improvement, to hurry production of a unit,
 * building or wonder, or trigger a Golden Age.
 */
public class GreatEngineer extends AbstractUnit<GreatEngineerView> {
    public static final String CLASS_UUID = "7fcdb183-8a4a-4c6f-9b11-febfe46fe7e9";
    public static final GreatEngineer INSTANCE = new GreatEngineer();

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