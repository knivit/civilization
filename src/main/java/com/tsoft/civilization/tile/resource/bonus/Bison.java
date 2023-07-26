package com.tsoft.civilization.tile.resource.bonus;

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
 * Bonus resource from Conquest of the New World Deluxe scenario. Added to main game in a patch released on 27 October 2014.[1]
 *
 *     Base yield:
 *         1 Food
 *     Modifiers:
 *         +1 Production from Camp improvement
 *
 * Strategy
 *
 * Bison are found in plains and grassland regions, providing Food that allows cities in these regions
 * to grow even more rapidly. Improve them with Camps as soon as you're able, and you'll turn nearby cities into
 * Population and Production centers.
 *
 * Civilopedia entry
 *
 * The North American bison, which once covered the Great Plains in massive herds, was the primary source for the meat,
 * sinew, hide and bone necessary for survival by many native tribes. Hunted to near extinction by Americans in the late 1800s,
 * in recent years the buffalo has made a comeback due to federal and state wildlife programs.
 */
public class Bison extends AbstractResource {

    private static final ResourceBaseState BASE_STATE = ResourceBaseState.builder()
        .supply(Supply.builder().food(1).build())
        .build();

    @Override
    public ResourceType getType() {
        return ResourceType.BISON;
    }

    @Override
    public ResourceCategory getCategory() {
        return ResourceCategory.BONUS;
    }

    @Override
    public ResourceBaseState getBaseState() {
        return BASE_STATE;
    }

    @Override
    public boolean acceptTile(AbstractTerrain tile) {
        return tile.isIn(Grassland.class, Plains.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.TRAPPING);
    }
}
