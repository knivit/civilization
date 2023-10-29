package com.tsoft.civilization.unit.catalog.greatartist;

import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

/**
 * Movement: 2;
 * Strength: 0;
 * Ranged Strength: 0
 *
 * Notes: Can sacrifice itself to construct a Landmark improvement, trigger a Golden Age, or "culture bomb"
 * a tile within 1 space of your territory, putting that tile and all adjacent tiles into your territory.
 * The Culture Bomb target tile cannot be in foreign territory, though it can be in unclaimed territory.
 */
public class GreatArtist {

    public static final String CLASS_UUID = UnitName.GREAT_ARTIST.name();

    private static final UnitBaseState BASE_STATE = new GreatArtistBaseState().getBaseState();

    private static final GreatArtistView VIEW = new GreatArtistView();

    public String getClassUuid() {
        return CLASS_UUID;
    }

    public UnitBaseState getBaseState() {
        return BASE_STATE;
    }

    public GreatArtistView getView() {
        return VIEW;
    }

    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() != Year.ANCIENT_ERA;
    }
}
