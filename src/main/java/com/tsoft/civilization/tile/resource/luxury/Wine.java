package com.tsoft.civilization.tile.resource.luxury;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
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
 *         +1 Gold from Plantation
 *         +1 Culture and +1 Faith from Goddess of Festivals Pantheon
 *         +1 Culture and +1 Faith from Monastery
 *
 * Strategy
 *
 * Wine is found in "warm" climates, primarily on open plains. It is improved with a Plantation.
 *
 * Wine is one of two luxuries, along with Incense, which is important for religion.
 * First, the Goddess of Festivals belief nets a bonus 1 Culture and Faith on the tile;
 * and second, the Monastery grants an additional +1 Culture and Faith.
 * Combining the effects of these two bonuses, a single improved Wine Wine resource may yield 1 Food (after researching Fertilizer),
 * 3 Gold, 2 Culture, and 2 Faith, plus whatever base yield the terrain has.
 *
 * Unfortunately, apart from Culture and Faith, Wine blocks the standard improvements and so isn't good for regular yields.
 *
 * Civilopedia entry
 *
 * Wine is an alcoholic beverage made of fermented grapes. Humans have been happily creating wine for some 8,000 years.
 * The practice is believed to have originated in the area near the modern Iranian-Georgian border.
 * In many cultures, wine is believed to have mystical powers and is used in countless religious ceremonies.
 * France is the (self-proclaimed) center of wine production on the planet, but she is facing stiff competition
 * from other countries in Europe, as well as the USA, South Africa, South America, and Australia.
 */
public class Wine extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.WINE;

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
        return tile.isIn(Plains.class, Grassland.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.CALENDAR);
    }
}
