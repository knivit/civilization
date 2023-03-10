package com.tsoft.civilization.unit.catalog.warriors;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.unit.catalog.UnitCatalog;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

import java.util.UUID;

/**
 * Movement: 2; Strength: 6; Ranged Strength: 0; Cost: 40; Requires Resource: none
 * Technology: (none); Obsolete with: Metal Casting; Upgrades to: Swordsman (80 gold)
 *
 * Notes: You normally start the game with a Settler and one Warrior unit.
 * The Warrior has a fairly decent combat strength, and can be expected to be useful deeper
 * into the Ancient and even Classical eras than in previous games.
 */
public class Warriors extends AbstractUnit {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final UnitBaseState BASE_STATE = UnitCatalog.getBaseState(UnitType.WARRIORS);

    private static final AbstractUnitView VIEW = new WarriorsView();

    public Warriors(Civilization civilization) {
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
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }
}