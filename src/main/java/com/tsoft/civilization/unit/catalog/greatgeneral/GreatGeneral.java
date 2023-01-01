package com.tsoft.civilization.unit.catalog.greatgeneral;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.unit.catalog.UnitCatalog;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

import java.util.UUID;

/**
 * Movement: 2; Strength: 0; Ranged Strength: 0
 *
 * Notes: Great Generals are produced when a civilization has repeated victories in combat.
 * His presence gives +25% combat bonus to friendly units within 2 tiles. He can sacrifice
 * himself to create a Citadel (a super-Fort), or to trigger a Golden Age. The Citadel can
 * only be built in your own territory (unlike a regular Fort, which can be built in unclaimed territory.
 */
public class GreatGeneral extends AbstractUnit {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final UnitBaseState BASE_STATE = UnitCatalog.getBaseState(UnitType.GREAT_GENERAL);

    private static final GreatGeneralView VIEW = new GreatGeneralView();

    public GreatGeneral(Civilization civilization) {
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
    public GreatGeneralView getView() {
        return VIEW;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }
}
