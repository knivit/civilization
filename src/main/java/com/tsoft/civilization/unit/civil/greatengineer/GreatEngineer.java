package com.tsoft.civilization.unit.civil.greatengineer;

import com.tsoft.civilization.unit.*;
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

    private static final UnitBaseState BASE_STATE = UnitCatalog.getBaseState(UnitType.GREAT_ENGINEER);

    private static final GreatEngineerView VIEW = new GreatEngineerView();

    public GreatEngineer(Civilization civilization) {
        super(civilization);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public UnitBaseState getBaseState() {
        return BASE_STATE;
    }

    @Override
    public GreatEngineerView getView() {
        return VIEW;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }
}
