package com.tsoft.civilization.unit.catalog.greatscientist;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.unit.catalog.UnitCatalog;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

import java.util.UUID;

/**
 * Movement: 2; Strength: 0; Ranged Strength: 0
 *
 * Notes: Can construct the Academy improvement, or discover a Technology, or trigger a Golden Age.
 * All these consume the unit. The technology can be chosen from a list of any technologies
 * that you could normally research at that point.
 */
public class GreatScientist extends AbstractUnit {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final UnitBaseState BASE_STATE = UnitCatalog.getBaseState(UnitType.GREAT_SCIENTIST);

    private static final GreatScientistView VIEW = new GreatScientistView();

    public GreatScientist(Civilization civilization) {
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
    public GreatScientistView getView() {
        return VIEW;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }
}