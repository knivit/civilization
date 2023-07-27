package com.tsoft.civilization.tile.resource.luxury;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.floodplain.FloodPlain;
import com.tsoft.civilization.tile.feature.marsh.Marsh;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
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
 * Sugar is usually in warm climates, on grassland or flood plains. Sugar is often found on marsh tiles, which have to be
 * cleared so that the access improvement, the Plantation, may be constructed.
 *
 * Civilopedia entry
 *
 * Sugar is an incredibly sweet substance derived from sugar cane (or beets) and used to sweeten food and beverages.
 * It is believed that sugar was first used by Polynesians, and then the practice spread to India and from there eventually
 * into Asia and Europe. Eventually sugar cane was transported to the Caribbean and to North and South America,
 * where it was found to grow incredibly well. Plentiful and cheap, sugar and its sweet cousins, glucose, fructose, etc.,
 * can be found in almost all processed foods across the world, much to the unhappiness of dentists and dieticians everywhere.
 */
public class Sugar extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.SUGAR;

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
        return tile.isIn(Plains.class, Grassland.class) ||
            tile.hasFeature(FloodPlain.class, Marsh.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.CALENDAR);
    }
}
