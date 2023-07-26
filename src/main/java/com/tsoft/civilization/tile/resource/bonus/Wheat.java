package com.tsoft.civilization.tile.resource.bonus;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.floodplain.FloodPlain;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.plains.Plains;

/**
 * Game Info
 *
 * Bonus resource.
 *
 *     Base yield:
 *         1 Food
 *     Modifiers:
 *         +1 Food from Granary
 *         +1 Food with Sun God Religious belief
 *
 * Strategy
 *
 * Wheat is found in plains and desert regions, making it very important for civilizations native to deserts like Arabia or Morocco.
 * It is the only bonus resource which can be improved right from the start of the game, because its improvement is the Farm,
 * which is available without any need of technology research! Its only downside is that when found on a desert,
 * its tile won't yield a whole lot, because a desert tile has no natural yield of any kind. Still, it's something.
 *
 * Use a Granary (which adds 1 Food Food to the tile) to unlock the full potential of Wheat Wheat. Also,
 * the Sun God religious belief adds another Food point.
 *
 * Civilopedia entry
 *
 * Originally cultivated in the Fertile Crescent of the Middle East, wheat is one of the most important grains in the human diet.
 * A remarkably versatile grain, wheat can be turned into flour and made into bread, pasta, cereal or cake, and
 * it can be fermented and made into a variety of alcoholic beverages.
 */
public class Wheat extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.WHEAT;

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
        return tile.isIn(Plains.class, Desert.class) || tile.hasFeature(FloodPlain.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.AGRICULTURE);
    }
}
