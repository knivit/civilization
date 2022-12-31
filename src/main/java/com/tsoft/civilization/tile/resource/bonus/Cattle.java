package com.tsoft.civilization.tile.resource.bonus;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;

/**
 * Game Info
 *
 * Bonus resource.
 *
 *     Base yield:
 *         1 Food
 *     Modifiers:
 *         +1 Production from Pasture
 *         +1 Production from Stable
 *
 * Strategy
 *
 * Cattle are found only on open grassland terrain, and as such are a great Food booster, providing 3 Food
 * without any improvement. They allow you to build a Stable in a nearby city; when combined with a Pasture,
 * the tile also yields +2 Production
 *
 * Civilopedia entry
 *
 * Some of the earliest domesticated animals in history, cows, oxen and other bovines have been used for livestock,
 * dairy, and as draft animals. In addition, cattle skin has been used for leather, and its dung as a fuel source.
 * Many tribal civilizations count a person's wealth by how many cattle he or she possesses.
 */
public class Cattle extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.CATTLE;

    private static final ResourceBaseState BASE_STATE = ResourceCatalog.getBaseState(ResourceType.CATTLE);

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
        return tile.isIn(Grassland.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.ANIMAL_HUSBANDRY);
    }
}
