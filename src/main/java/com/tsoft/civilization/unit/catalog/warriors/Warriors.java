package com.tsoft.civilization.unit.catalog.warriors;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

/**
 * Movement: 2; Strength: 6; Ranged Strength: 0; Cost: 40; Requires Resource: none
 * Technology: (none); Obsolete with: Metal Casting; Upgrades to: Swordsman (80 gold)
 *
 * Notes: You normally start the game with a Settler and one Warrior unit.
 * The Warrior has a fairly decent combat strength, and can be expected to be useful deeper
 * into the Ancient and even Classical eras than in previous games.
 */
public class Warriors {

    public static final String CLASS_UUID = UnitName.WARRIORS.name();

    private static final UnitBaseState BASE_STATE = new WarriorsBaseState().getBaseState();

    private static final WarriorsView VIEW = new WarriorsView();

    public String getClassUuid() {
        return CLASS_UUID;
    }

    public UnitBaseState getBaseState() {
        return BASE_STATE;
    }

    public WarriorsView getView() {
        return VIEW;
    }

    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }
}
