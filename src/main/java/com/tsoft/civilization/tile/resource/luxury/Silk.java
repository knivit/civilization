package com.tsoft.civilization.tile.resource.luxury;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;

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
 * Silk is found on a variety of terrains, but usually in forests. These need to be cut down, so that a Plantation
 * can be constructed to access the resource.
 *
 * Civilopedia entry
 *
 * Silk is a fiber which is made from the cocoons of silkworms. Silk fabric was first developed in ancient China,
 * according to legend by the empress Lei Zu. Originally reserved for the Chinese nobility, over time it spread throughout China,
 * and via the famous Silk Road of antiquity, into Europe and across the planet. (The earliest record of silk in Europe dates to
 * Egypt in approximately 1000 BC.)
 */
public class Silk extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.SILK;

    private static final ResourceBaseState BASE_STATE = ResourceBaseState.builder()
        .supply(Supply.builder().gold(2).build())
        .build();

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
        return tile.hasFeature(Forest.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.CALENDAR);
    }
}
