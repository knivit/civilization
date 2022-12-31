package com.tsoft.civilization.tile.resource.luxury;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;

/**
 * Game Info
 *
 * Luxury resource added in Civilization V: Gods & Kings.
 *     Base yield:
 *         1 Food
 *         1 Gold
 *     Modifiers:
 *         +1 Food and +1 Production from Mine improvement
 *         +1 Faith with Earth Mother Pantheon
 *
 * Strategy
 *
 * 2Salt is quite a unique resource - it is food-oriented, thus giving a bonus of 1 Food and 1 Gold instead of the usual 2 Gold,
 * but it is accessed with a Mine! What's more, the Mine's bonus is 1 Food and 1 Production (increased by +1 with Chemistry)
 * instead of the usual 1 Production! So, the combined yield of a Salt resource on a plains terrain is 3 Food, 3 Production and 1 Gold,
 * a combination quite unusual for a luxury resource.
 *
 * Salt is often found on inhospitable terrains such as deserts and tundra, making it quite valuable for civilizations
 * such as Russia or Arabia. Use it to supply Food to a nearby city, as well as trade it as a luxury.
 *
 * Salt also gains 1 Faith with the Earth Mother belief.
 *
 * Civilopedia entry
 *
 * One of the necessities of human life, salt, is found throughout the world in both natural and man-made environments.
 * Used for thousands of years as a means of preserving meat, salt was crucial in efforts to store food prior to the invention
 * of artificial refrigeration. A common method of producing and harvesting salt for consumption is through the use of evaporation.
 * By creating large ponds or pools of saltwater, over time the natural evaporation process will remove the water and leave
 * behind the salt residue for collection.
 */
public class Salt extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.SALT;

    private static final ResourceBaseState BASE_STATE = ResourceCatalog.getBaseState(ResourceType.SALT);

    private static final ResourceCategory RESOURCE_CATEGORY = ResourceCategory.LUXURY;

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
        return tile.isIn(Plains.class, Desert.class, Tundra.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return true;
    }
}
