package com.tsoft.civilization.improvement.catalog.road;

import com.tsoft.civilization.improvement.*;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.civilization.Civilization;

/**
 * Road
 *
 * Type: Worker improvement
 * Requires: The Wheel
 * Constructed on: Any passable land tile; may pass through tiles containing other improvements
 * Effect
 * Faster movement for friendly or neutral units.
 * Creates trade route (vanilla and GodsKings5 clear.png) or City Connection (BNW-only.png).
 *   -1 Gold per turn as a maintenance cost.
 * Faster road movement with Machinery.
 * Bridges enabled with Engineering.
 *
 * Game Info
 * Basic transport infrastructure. Requires The Wheel.
 *
 * Effect:
 * Increased movement speed for units
 * Provides a City Connection when connecting to the civilization's Capital
 * Allows crossing of rivers without losing MPs after researching Engineering
 * Additional movement speed increase after researching Machinery
 */
public class Road extends AbstractImprovement {

    public static final String CLASS_UUID = ImprovementName.ROAD.name();

    private static final ImprovementBaseState BASE_STATE = new RoadBaseState().getBaseState();

    private static final RoadView VIEW = new RoadView();

    public Road(AbstractTerrain tile) {
        super(tile);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public ImprovementBaseState getBaseState() {
        return BASE_STATE;
    }

    @Override
    public AbstractImprovementView getView() {
        return VIEW;
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.WHEEL);
    }

    @Override
    public boolean acceptTile(AbstractTerrain tile) {
        return tile.getTileType() != TerrainType.SEA;
    }
}
