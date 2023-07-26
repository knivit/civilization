package com.tsoft.civilization.civilization.building.catalog.granary;

import com.tsoft.civilization.civilization.building.*;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.Year;

/**
 * Granary
 * -------
 * Building of the Ancient Era
 *
 * Cost
 * ----
 * Production	    Maintenance
 * 60 Production	1 Gold
 * Required Technology: Pottery
 * Effects
 *   +2 Food
 *   +1 Food Each source of wheat, bananas and deer worked by the city produces
 *
 * Basic growth-enhancing building of the Ancient Era.
 * Allows Food to be moved from this city along trade routes inside your civilization.
 *
 * Strategy
 * --------
 * The Granary increases the Food Food production of a city, speeding up the city's growth, especially if it has one of
 * the above-mentioned resources nearby (which need not be improved with a Farm).
 * It's always useful when you want to create a large city, and for speeding up growth of smaller ones.
 * It's also incredibly useful to help cities with poor access to fertile land.
 *
 * A granary is a building constructed to store grain for human or herd animal consumption (or both).
 * The earliest granaries yet discovered were found in a pre-pottery Neolithic settlement in the Jordan Valley
 * of the Middle East, and date back to approximately 9500 BC.
 * These first granaries measured about 10-foot by 10-foot square. They featured raised floors which allowed air
 * to circulate more freely and provided some protection for the grain from mice and other menaces.
 */
public class Granary extends AbstractBuilding {

    public static final String CLASS_UUID = BuildingType.GRANARY.name();

    private static final BuildingBaseState BASE_STATE = new GranaryBaseState().getBaseState();

    private static final AbstractBuildingView VIEW = new GranaryView();

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public BuildingBaseState getBaseState() {
        return BASE_STATE;
    }

    @Override
    public AbstractBuildingView getView() {
        return VIEW;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }

    @Override
    public boolean requiredEraAndTechnology(Civilization civilization) {
        return civilization.getYear().isAfter(Year.ANCIENT_ERA) &&
            civilization.isResearched(Technology.POTTERY);
    }
}