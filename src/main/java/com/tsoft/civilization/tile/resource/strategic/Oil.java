package com.tsoft.civilization.tile.resource.strategic;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.feature.marsh.Marsh;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.snow.Snow;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;

/**
 * Game Info
 *
 * Late-game strategic resource. Used by most of the powerful late industrial-early modern era units.
 *
 * Strategy
 *
 * Oil is the first late-game strategic resource (revealed by the Biology technology), and is quite unique for a number
 * of reasons. First, it is the only strategic resource found on both land and sea. Second, it is the only resource which
 * requires two unique new improvements to access: the Oil Well for land-based sources, and the Offshore Platform for
 * sea-based ones. Finally, its sources are in weird locations - deserts, snow, and tundra terrain or anywhere on coast tiles.
 * Of these, Oil is most often found in deserts, which gives desert-based civilizations a unique advantage -
 * they can be all but sure that they will reveal multiple sources already within their territory. Otherwise, be ready
 * to expand immediately after you reveal the Oil sources.
 *
 * Unimproved, the Oil resource itself doesn't provide a big Production bonus (only +1), but both of the improvements
 * add a whopping +3 Production. This is a total of +4 Production over the base tile yield, which makes the tile really productive.
 * Sea-based sources of Oil are even more useful, because of the additional bonuses the Lighthouse and the Seaport provide
 * (+2 Production and +1 Gold, apart from the Offshore Platform's own bonus).
 *
 * Oil is used for most early late-game machines on land, sea and air, such as armored units and airplanes,
 * so militaristic civilizations should make sure they have plenty of Oil to continue their wars.
 * Civilopedia entry
 *
 * Oil is a liquid fuel found in large and small deposits across the world. Although occasionally used by some civilizations
 * as far back as four thousand years ago to create asphalt or naptha or burned as a light source, modern use can be traced
 * back to 1857 when the first modern refinery was constructed in Alsace, France. At about the same time wells were being
 * drilled across Europe and in North America. By the early 20th century oil was in great demand, largely due to its use
 * in internal combustion engines as used by ships, planes, and automobiles. Today much of international politics is driven
 * by growing tension between those who have abundant sources of oil, and those who do not. Remember that oil is a strategic
 * resource, and thus it is consumed as you construct the associated units and buildings.
 */
public class Oil extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.OIL;

    private static final ResourceBaseState BASE_STATE = ResourceBaseState.builder()
        .supply(Supply.EMPTY)
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
        return tile.isIn(Desert.class, Tundra.class, Snow.class) || tile.hasFeature(Coast.class, Marsh.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.BIOLOGY);
    }
}
