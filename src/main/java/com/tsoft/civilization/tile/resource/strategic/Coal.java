package com.tsoft.civilization.tile.resource.strategic;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.plains.Plains;

/**
 * Game Info
 *
 * Mid-game strategic resource. Used by Factories and Ironclads.
 *
 * Strategy
 *
 * Coal is the first new strategic resource to become available after a long time with only Horses and Iron.
 * Coal is accessed with a Mine and used for two very specific things: Ironclads, which are a transition between
 * ships with sails and modern engine-based ships; and the construction of Factories.
 *
 * Of these, the second is much more important, firstly because Factories are a major Production booster for a city,
 * and what's more, they're required for a civilization to adopt an Ideology early. Since each Factory consumes 1 Coal,
 * you will need at least 3 Coal to be among the first to adopt an Ideology, and reap the free bonus Tenets.
 *
 * Along with Uranium, Coal is perhaps the rarest of all strategic resources - it is found in locations
 * that are difficult to access, like jungles, and its sources are much fewer than Horses or Iron.
 * It is often the case that you will realize that you have practically no Coal within easy access,
 * or that the sources within your reach are much closer to another empire, and you will have to expand additionally to get it.
 * You should be prepared for that come the Industrial Era, or you will be forced to trade for Coal for the rest of the game.
 * Luckily, once you build a Factory, there doesn't seem to be any negative effect if you start consuming more Coal than you produce.
 *
 * Its second use, the Ironclads, are only a temporary drain - when you replace them with the later Destroyer you will
 * liberate the Coal they used for more Factories.
 *
 * Civilopedia entry
 *
 * Coal is ancient plant matter that has decomposed and compressed over the millennia into a highly combustible black compound.
 * Found in substantial quantities across virtually the entire planet, coal has been one of the most important sources of
 * energy to mankind throughout history. Coal is not a clean fuel and its extraction and burning is a major source of
 * pollution around the world. Scientists are frantically looking for the elusive "clean coal" technology, but that remains
 * as of yet undiscovered. Remember that coal is a strategic resource, and thus it is consumed as you construct
 * the associated units and buildings.
 */
public class Coal extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.COAL;

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
        return tile.isIn(Plains.class, Grassland.class) || tile.hasFeature(Hill.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.INDUSTRIALIZATION);
    }
}
