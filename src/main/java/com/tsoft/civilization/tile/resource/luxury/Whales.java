package com.tsoft.civilization.tile.resource.luxury;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;

/**
 * Game Info
 *
 * Luxury resource.
 *     Base yield:
 *         1 Food
 *         1 Gold
 *     Modifiers:
 *         +1 Food from Fishing Boats improvement
 *         +1 Production from Harbor (vanilla and GodsKings5 clear.png) or Lighthouse (BNW-only.png)
 *
 * Strategy
 *
 * Whales are a relatively common luxury, found in most coastal regions of the world. Instead of +2 Gold,
 * Whales provide a +1 bonus to both Food and Gold.
 *
 * Civilopedia entry
 *
 * The largest mammals alive today, for centuries men have hunted whales for their meat, blubber, and bones.
 * In pre-industrial times men challenged these massive animals from sailing vessels, employing harpoons against
 * the massive beasts in an often deadly fight of man against animal. Now the hunters use modern ships, sonar,
 * and exploding weapons against the whales - with such success that their survival remains in doubt.
 */
public class Whales extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.WHALES;

    private static final ResourceBaseState BASE_STATE = ResourceCatalog.getBaseState(ResourceType.WHALES);

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
        return tile.hasFeature(Coast.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.CALENDAR);
    }
}
