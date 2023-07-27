package com.tsoft.civilization.tile.resource.luxury;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.tile.feature.jungle.Jungle;
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
 * Spices are found in jungles or forests. They are accessed with a Plantation, so you'll have to cut down the foliage first.
 *
 * Civilopedia entry
 *
 * Spices are plant-derived substances used to preserve and enhance the flavor, color or texture of food. Some spices
 * have religious significance as well, and some are purported to have medicinal benefits. Common spices include pepper,
 * mustard, cinnamon, saffron, and ginger. Generally, spices must be dried and ground before being applied to food;
 * this distinguishes them from herbs like sage or oregano, which are green and leafy and which may be used fresh or dried.
 */
public class Spices extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.SPICES;

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
        return tile.hasFeature(Jungle.class, Forest.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.CALENDAR);
    }
}
