package com.tsoft.civilization.unit.catalog.greatscientist;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

/**
 * Movement: 2; Strength: 0; Ranged Strength: 0
 *
 * Notes: Can construct the Academy improvement, or discover a Technology, or trigger a Golden Age.
 * All these consume the unit. The technology can be chosen from a list of any technologies
 * that you could normally research at that point.
 */
public class GreatScientist {

    public static final String CLASS_UUID = UnitName.GREAT_SCIENTIST.name();

    private static final UnitBaseState BASE_STATE = new GreatScientistBaseState().getBaseState();

    private static final GreatScientistView VIEW = new GreatScientistView();

    public String getClassUuid() {
        return CLASS_UUID;
    }

    public UnitBaseState getBaseState() {
        return BASE_STATE;
    }

    public GreatScientistView getView() {
        return VIEW;
    }

    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }
}
