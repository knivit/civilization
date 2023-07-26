package com.tsoft.civilization.tile.resource.bonus;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.snow.Snow;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;

/**
 * Game Info
 *
 * Bonus resource.
 *
 *     Base yield:
 *         1 Production
 *     Modifiers:
 *         +1 Production from Quarry
 *         +1 Production from Stone Works
 *
 * Strategy
 *
 * Stone is the only bonus resource meant specifically to enhance Production, and it does this well.
 * It's found on many terrain types, and when upgraded with a Quarry provides a serious boost to Production
 * (+1 from the resource, +2 additional from the Quarry after researching Chemistry).
 *
 * Stone is one of two resources that allows construction of a Stone Works in a nearby city, which nets another
 * point of Production. It also produces Faith Faith with the Stone Circles Belief.
 *
 * Civilopedia entry
 *
 * Found in the Earth's crust and upper mantle, stone is perhaps the most crucial element in human construction
 * efforts throughout history. There are three major classifications of stone - Sedimentary, Igneous,
 * and Metamorphic - each differing in their origin.
 */
public class Stone extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.STONE;

    private static final ResourceBaseState BASE_STATE = ResourceBaseState.builder()
        .supply(Supply.builder().production(1).build())
        .build();

    private static final ResourceCategory RESOURCE_CATEGORY = ResourceCategory.BONUS;

    @Override
    public ResourceType getType() {
        return RESOURCE_TYPE;
    }

    @Override
    public ResourceCategory getCategory() {
        return RESOURCE_CATEGORY;
    }

    @Override
    public ResourceBaseState getBaseState() {
        return BASE_STATE;
    }

    @Override
    public boolean acceptTile(AbstractTerrain tile) {
        return tile.isIn(Grassland.class, Plains.class, Desert.class, Tundra.class, Snow.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.MASONRY);
    }
}
