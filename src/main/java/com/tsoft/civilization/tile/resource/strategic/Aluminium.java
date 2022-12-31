package com.tsoft.civilization.tile.resource.strategic;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;

/**
 * Game Info
 *
 * Advanced late-game strategic resource. Used by powerful late-game units.
 *
 * Strategy
 *
 * Aluminum is discovered about the same time as Oil Oil, with the Electricity technology. Aluminum sources are more
 * common than Oil sources, found in the more conventional locations as a general rule, and they're also much easier
 * to find than Coal sources.
 *
 * Try to plan your late-game expansions so that you ensure access to this important resource. Aluminum
 * is accessed with the Mine and conveys the usual benefits to Production. Aluminum
 * can also be gained by building a Recycling Center.
 *
 * Aluminum is used a bit differently than the military-only Oil: some late-game economic buildings,
 * such as the Hydro Plant, also require it. In the Information Era, many military vehicles start to use Aluminum
 * instead of Oil, vastly increasing its consumption.
 *
 * Lastly, all six parts of the spaceship require Aluminum Aluminum to build. Since each part stops consuming
 * the resource when it is added, it is possible to complete the spaceship with just one unit of Aluminum.
 * Be sure to plan accordingly.
 *
 * Civilopedia entry
 *
 * Aluminum is one of the most abundant elements on Earth. A remarkably useful metal, aluminum is flexible, durable,
 * and highly resistant to corrosion. Although plentiful, in its natural state aluminum is almost always found
 * combined with other materials. It wasn't until the mid-19th century that scientists discovered how to extract
 * and process aluminum in quantity. By the 20th-21st centuries it is widely used in the construction of military,
 * aerospace, and consumer goods. Remember that aluminum is a strategic resource, and thus it is consumed as you
 * construct the associated units and buildings.
 */
public class Aluminium extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.ALUMINIUM;

    private static final ResourceBaseState BASE_STATE = ResourceCatalog.getBaseState(ResourceType.ALUMINIUM);

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
        return tile.isIn(Desert.class, Plains.class, Tundra.class) || tile.hasFeature(Hill.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.ELECTRICITY);
    }
}
