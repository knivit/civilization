package com.tsoft.civilization.tile.resource.strategic;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.hill.Hill;
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
 * Basic strategic resource. Used by powerful early-game frontline units and middle-game ships.
 *
 * Strategy
 *
 * As the first strategic resource revealed on the map along with Horses, Iron is very important for planning your
 * initial expansion. Research Iron Working (Bronze Working in Brave New World), then look for the newly revealed
 * resources and try to settle near them.
 *
 * Iron is required for the strongest early-game frontline units and also for Frigates, an important midgame ship.
 * This makes it a vital resource for all early-game expansionist civilizations, like the Huns.
 *
 * Iron is found practically everywhere, which makes it a relatively easy-to-access resource. Still,
 * it's more common in out-of-the-way locations, such as tundra and snow terrain. Iron s extracted via Mines, which makes it easy to access.
 *
 * Like all strategic resources, it nets a good increase in Production for the tile.
 * Its unique benefit is to allow construction of the Forge in a nearby city. This building grants a Production
 * bonus for all land units (not only mounted units), which makes cities with access to Iron Iron natural military
 * training centers. Take advantage of this by constructing military training buildings like the Barracks in the city, as well.
 * Civilopedia entry
 *
 * Iron is one of the most important metals in human history. It is used for construction, for military weapons, and for tools.
 * The first iron used came from pure deposits found in meteorites, and by the 2nd millennium BC some cultures had learned
 * how to extract it from ores found in mines. Iron continues to be an extremely important material in the modern era,
 * though it is most often used in its steel alloy form. Remember that iron is a strategic resource, and thus it is
 * consumed as you construct the associated units and buildings.
 */
public class Iron extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.IRON;

    private static final ResourceBaseState BASE_STATE = ResourceBaseState.builder()
        .supply(Supply.EMPTY)
        .build();

    private static final ResourceCategory RESOURCE_CATEGORY =  ResourceCategory.STRATEGIC;

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
        return tile.isIn(Plains.class, Grassland.class, Desert.class, Tundra.class, Snow.class) || tile.hasFeature(Hill.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.IRON_WORKING);
    }
}
