package com.tsoft.civilization.unit.catalog.settlers;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

/**
 * Movement: 2;
 * Strength: 0;
 * Ranged Strength: 0;
 * Cost: 106 hammers;
 * Requires Resource: none
 * Technology: (none)
 *
 * Abilities: Found City (B): This order causes the Settler to found a city at its current location.
 * The Settler is consumed in this process.
 * Notes: Growth of the City is stopped while this Units is being built. Settlers may only be built
 * in Cities with at least 2 Citizens. This is the only unit in the game with regional variation:
 * in addition to regional costume, the European version has a mule, the Native American version has a Llama,
 * the Asian version has a water buffalo, and the African/Middle Eastern version has a camel.
 */
public class Settlers extends AbstractUnit {

    public static final String CLASS_UUID = UnitName.SETTLERS.name();

    private static final UnitBaseState BASE_STATE = new SettlersBaseState().getBaseState();

    private static final AbstractUnitView VIEW = new SettlersView();

    public Settlers(Civilization civilization) {
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
    public AbstractUnitView getView() {
        return VIEW;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() < Year.MODERN_ERA;
    }
}
