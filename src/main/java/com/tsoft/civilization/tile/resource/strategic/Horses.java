package com.tsoft.civilization.tile.resource.strategic;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;

/**
 * Game Info
 *
 * Basic strategic resource. Used by many mounted units.
 *
 *     Base yield:
 *         1 Production
 *     Modifiers:
 *         +1 Production from Pasture
 *         +1 additional Food after researching Fertilizer
 *
 * Strategy
 *
 * As the first strategic resource revealed on the map along with Iron Iron, 20xHorse5.png Horses are very important for
 * planning your initial expansion. Research Animal Husbandry, then look for the newly revealed resources and try to settle near them.
 *
 * Horses are required for all mounted units in the game (except for the African Forest Elephant, Horse Archer,
 * Naresuan's Elephant, War Chariot, and War Elephant), so you will be needing this resource quite a lot.
 * Try to ensure access to at least one or two sources of Horses Horses; alternatively, ensure a stable trading partner for them.
 *
 * Horses are usually found on open plains and grassland terrains; however, they are also rarely found on desert and hills.
 * They are accessed via the Pasture, which provides the opportunity to build the Stable in a nearby city. Both of these
 * net a good increase in the tile's Production yield, which is very important in the early game.
 * They also give a bonus to building mounted units in the city - use this strategically.
 *
 * Finally, Horses are one of the two resources that allow construction of a Circus in a city - a useful
 * source of extra Happiness.
 *
 * Civilopedia entry
 *
 * As transport, implement of war, companion and (occasionally) foodstuff, horses are among the most important domesticated
 * animals in human history. Horses were originally domesticated in Central Asia around 4000 BC, and over the next several
 * thousand years the practice spread across Europe, Asia, the Middle East and Africa. Horses were transported to the
 * New World by the Spanish in the 16th century, where their use was quickly and enthusiastically picked up by
 * the native population in the following centuries. Remember that horses are strategic resources, and thus they are
 * consumed as you construct the associated units.
 */
public class Horses extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.HORSES;

    private static final ResourceBaseState BASE_STATE = ResourceBaseState.builder()
        .supply(Supply.builder().production(1).build())
        .build();

    private static final ResourceCategory RESOURCE_CATEGORY = ResourceCategory.STRATEGIC;

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
        return tile.isIn(Plains.class, Grassland.class, Tundra.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.ANIMAL_HUSBANDRY);
    }
}
