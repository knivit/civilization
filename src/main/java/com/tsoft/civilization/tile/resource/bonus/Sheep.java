package com.tsoft.civilization.tile.resource.bonus;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.plains.Plains;

/**
 * Game Info
 *
 * Bonus resource.
 *
 *     Base yield:
 *         1 Food
 *     Modifiers:
 *         +1 Food from Pasture
 *         +1 Production from Stable
 *
 * Strategy
 *
 * Sheep are found on a variety of terrains, but almost always on hills. Very often they appear in deserts, which
 * makes them important for desert-dwelling civilizations like Persia or Morocco. They're a bit more difficult
 * to develop than Wheat because they require a Pasture, but their yield is more diversified thanks to the
 * Production bonus of the hills they're found on. So, instead of providing a whole lot of Food like Wheat
 * or Bananas  they provide a balanced quantity of both +3 Food Food and +3 Production upon researching Fertilizer.
 *
 * A Stable can be built in the city working a Sheep Sheep resource; it will improve their yield further, adding 1 Production.
 * Civilopedia entry
 *
 * Sheep are extremely tasty creatures that also produce milk, wool and leather. One of the earliest animals to be domesticated,
 * sheep were first tamed in Mesopotamia some ten thousand years ago. Since that period the practice of shepherding has spread
 * widely, and today sheep can be found across the planet. There are over 200 breeds of sheep in existence, each bred for a
 * specific quality: hardiness, size, quantity and quality of wool, milk production, and of course tastiness!
 */
public class Sheep extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.SHEEP;

    private static final ResourceBaseState BASE_STATE = ResourceCatalog.getBaseState(ResourceType.SHEEP);

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
        return tile.isIn(Plains.class) || tile.hasFeature(Hill.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.ANIMAL_HUSBANDRY);
    }
}
