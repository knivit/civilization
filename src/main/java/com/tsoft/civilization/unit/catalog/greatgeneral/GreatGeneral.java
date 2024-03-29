package com.tsoft.civilization.unit.catalog.greatgeneral;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

/**
 * Movement: 2; Strength: 0; Ranged Strength: 0
 *
 * Notes: Great Generals are produced when a civilization has repeated victories in combat.
 * His presence gives +25% combat bonus to friendly units within 2 tiles. He can sacrifice
 * himself to create a Citadel (a super-Fort), or to trigger a Golden Age. The Citadel can
 * only be built in your own territory (unlike a regular Fort, which can be built in unclaimed territory.
 */
public class GreatGeneral {

    public static final String CLASS_UUID = UnitName.GREAT_GENERAL.name();

    private static final UnitBaseState BASE_STATE = new GreatGeneralBaseState().getBaseState();

    private static final GreatGeneralView VIEW = new GreatGeneralView();

    public String getClassUuid() {
        return CLASS_UUID;
    }

    public UnitBaseState getBaseState() {
        return BASE_STATE;
    }

    public GreatGeneralView getView() {
        return VIEW;
    }

    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }
}
