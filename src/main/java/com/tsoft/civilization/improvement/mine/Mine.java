package com.tsoft.civilization.improvement.mine;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.improvement.*;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.feature.hill.Hill;

import java.util.UUID;

/**
 * Mine
 *
 * Type: Worker improvement
 * Requires: Mining
 * Constructed on: Hills
 * Effect
 *   +1 Production.
 *   +1 additional Food for Salt.
 *   +1 additional Production for Coal, Aluminum, and Uranium.
 *   +1 additional Production with Chemistry.
 *   +1 additional Production with Five-Year Plan Order tenet
 *
 * Game Info
 * Basic production improvement. Requires Mining.
 *
 * Access:
 *   Strategic: Iron, Coal, Aluminum, and Uranium strategic resources
 *   Luxuries:  Gold, Silver, Copper, Gems, and Salt luxury resources
 *
 * Strategy
 * The Mine grants access to several strategic and luxury resources. It can also be built on hills near cities
 * to improve their Production potential.
 *
 * Civilopedia entry
 * Mines are among the most important improvements in Civilization V. Not only do they increase
 * a hex's productivity output - and high productivity allows quicker construction of units, buildings, and wonders -
 * but they also provide access to some of the most important resources in the game: iron, coal, and uranium, to name a few.
 */
public class Mine extends AbstractImprovement {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final ImprovementBaseState BASE_STATE = ImprovementCatalog.getBaseState(ImprovementType.MINE);

    private static final AbstractImprovementView VIEW = new MineView();

    public Mine(AbstractTerrain tile) {
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
        return civilization.isResearched(Technology.MINING);
    }

    @Override
    public boolean acceptTile(AbstractTerrain tile) {
        return tile.hasFeature(Hill.class);
    }
}
