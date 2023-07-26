package com.tsoft.civilization.civilization.building.catalog.walls;

import com.tsoft.civilization.civilization.building.*;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;

/**
 * Walls
 *
 * Building of the Ancient Era
 * Cost
 * Production	Maintenance
 * 75 Production	0 Gold
 *
 * Required Technology: Masonry
 * Effects
 *   +5 Melee strength
 *  +50 HP
 *
 * Game Info
 * Basic defensive building of the Ancient Era.
 *
 * Strategy
 * Walls increase a city's HP and Combat Strength, making the city more difficult to capture.
 * Walls are quite useful for cities located along a civilization's frontier.
 *
 * Civilopedia entry
 *
 * Ever since people have gathered together in groups - towns, cities, whatever - other people have wanted
 * to steal their stuff. From earliest history people have constructed defensive works of mud, wood or stone
 * to keep their enemies away from their food, wealth, and women and children.
 * By medieval times walled villages and cities dotted the European countryside, some accompanied
 * by elaborate moats and ditches. Some of these elaborate works are still visible today.
 * The city of York, England, has extensive walls which date back to Roman times.
 */
public class Walls extends AbstractBuilding {

    public static final String CLASS_UUID = BuildingType.WALLS.name();

    private static final BuildingBaseState BASE_STATE = new WallsBaseState().getBaseState();

    private static final AbstractBuildingView VIEW = new WallsView();

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
            civilization.isResearched(Technology.MASONRY);
    }
}
