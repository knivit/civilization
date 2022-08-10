package com.tsoft.civilization.improvement.road;

import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.improvement.AbstractImprovementView;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;

import java.util.UUID;

/**
 * Road
 *
 * Type: Worker improvement
 * Requires: The Wheel
 * Constructed on: Any passable land tile; may pass through tiles containing other improvements
 * Effect
 * Faster movement for friendly or neutral units.
 * Creates trade route (vanilla and GodsKings5 clear.png) or 20xCityConnection5.png City Connection (BNW-only.png).
 *   -1 Gold per turn as a maintenance cost.
 * Faster road movement with Machinery.
 * Bridges enabled with Engineering.
 *
 * Game Info
 * Basic transport infrastructure. Requires The Wheel.
 *
 * Effect:
 * Increased movement speed for units
 * Provides a City Connection when connecting to the civilization's Capital Capital
 * Allows crossing of rivers without losing MPs after researching Engineering
 * Additional movement speed increase after researching Machinery
 */
public class Road extends AbstractImprovement {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final AbstractImprovementView VIEW = new RoadView();

    public Road(AbstractTerrain tile) {
        super(tile);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.WHEEL);
    }

    @Override
    public boolean acceptTile(AbstractTerrain tile) {
        return tile.getTileType() != TerrainType.SEA;
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().gold(-2).build();
    }

    @Override
    public AbstractImprovementView getView() {
        return VIEW;
    }

    @Override
    public CombatStrength getBaseCombatStrength(int era) {
        return CombatStrength.builder()
            .defenseStrength(10)
            .build();
    }
}
