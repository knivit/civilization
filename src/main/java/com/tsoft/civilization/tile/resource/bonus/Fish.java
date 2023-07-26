package com.tsoft.civilization.tile.resource.bonus;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;

/**
 * Game Info
 *
 * Bonus resource.
 *
 *     Base yield:
 *         1 Food
 *     Modifiers:
 *         +1 Food from Fishing Boats
 *         +1 Food, +1 Production from Lighthouse
 *         +1 Production, +1 Gold from Seaport
 *
 * Strategy
 *
 * As the only sea bonus resource, and one of the few sea resources in general, Fish is very important for all civilizations,
 * but especially those that depend on the sea to thrive. Fish is mainly a source of Food, but with the construction
 * of a Lighthouse and a Seaport along with the Compass technology, the resource gains also Production and Gold output.
 * Its maximum yield when worked is +3 Food, +2 Production, and +2 Gold Gold (with Compass),
 * on top of the +2 Food (and, without Brave New World, +1 Gold) from the terrain itself.
 *
 * Civilopedia entry
 *
 * Fish have been an important source of protein for coastal dwellers for all of history. Fish remain an important foodstuff
 * in the 21st century - so much so that there is increasing danger of depopulating the world's oceans of edible fish entirely.
 */
public class Fish extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.FISH;

    private static final ResourceBaseState BASE_STATE = ResourceBaseState.builder()
        .supply(Supply.builder().food(1).build())
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
        return tile.hasFeature(Coast.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.SAILING);
    }
}
