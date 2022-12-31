package com.tsoft.civilization.tile.resource.luxury;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.plains.Plains;

/**
 * Game Info
 *
 * Luxury resource.
 *     Base yield:
 *         2 Gold
 *     Modifiers:
 *         +1 Gold from Plantation improvement
 *
 * Strategy
 *
 * Cotton is found in "hot" climates, most commonly in or near deserts. It is accessed via a Plantation.
 *
 * Civilopedia entry
 *
 * Cotton is a plant that has been cultivated for centuries. It produces a fibrous "boll" around the seed which can be spun
 * into yarn or thread and used to make cloth for clothing, ship sails, and other valuable material. First cultivated
 * in India some seven thousand years ago, cotton is still the most widely used natural material for clothing manufacture today.
 */
public class Cotton extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.COTTON;

    private static final ResourceBaseState BASE_STATE = ResourceCatalog.getBaseState(ResourceType.COTTON);

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
        return tile.isIn(Grassland.class, Plains.class, Desert.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.CALENDAR);
    }
}
