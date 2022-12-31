package com.tsoft.civilization.tile.resource.bonus;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;

/**
 * Game Info
 *
 * Bonus resource.
 *
 *     Base yield:
 *         1 Food
 *     Modifiers:
 *         +1 Production from Camp
 *         +1 Food from Granary
 *
 * Strategy
 *
 * Deer are found on forest and tundra tiles, mainly in the polar (northernmost/southernmost) regions of the world.
 * They typically provide a significant fraction of available Food Food in the otherwise barren polar regions of the map,
 * making their presence and exploitation an important factor in sustaining strong cities in these areas. Building a Camp
 * will further enhance the Gold yield of Deer once you have researched Economics, making them valuable to your
 * empire's economy as well.
 *
 * Note that building a Camp does not require clearing the underlying forest. This allows continued exploitation of
 * the additional Production provided by the forest itself, for a base yield of 2 Food and 2 Production
 * from a Camp built on a forested tundra tile with Deer. When the terrain beneath the forest is tundra,
 * one should preserve the forest where possible, since clearing it will result in a net loss of 1 Production.
 * However, if the terrain is plains, then removing the forest will not change the tile yields because both forest
 * and plains give 1 Food Food and 1 Production. Thus, one should always clear the forest of a Deer
 * on a plains tile before constructing the Camp.
 *
 * Civilopedia entry
 *
 * Deer and their relatives - reindeer, moose, roe, etc. - can be found in herds across the planet.
 * They have provided food, leather, bones, gut, and other materials for pre-industrial people for uncounted thousands of years.
 */
public class Deer extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.DEER;

    private static final ResourceBaseState BASE_STATE = ResourceCatalog.getBaseState(ResourceType.DEER);

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
        return tile.hasFeature(Forest.class) || tile.isIn(Tundra.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.TRAPPING);
    }
}
