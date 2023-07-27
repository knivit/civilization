package com.tsoft.civilization.tile.resource.strategic;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.snow.Snow;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;

/**
 * Game Info
 *
 * Advanced late-game strategic resource. Used for building nuclear weapons and Nuclear Plants.
 *
 * Strategy
 *
 * Uranium is the last strategic resource in the game, discovered with the Atomic Theory technology.
 * Its use comes in the Atomic Era - it allows you to build Atomic Bombs and Nuclear Missiles. Uranium
 * also allows construction of the Nuclear Plant, a building which helps speed up city Production.
 *
 * Finally, it allows you to build Giant Death Robots, which are the most powerful direct combat units in the game.
 * So, you can choose to either guard your Uranium for military purposes, or construct Nuclear Plants to
 * greatly improve Production in your cities if your civilization isn't particularly warlike.
 *
 * Uranium is a rare resource, but it's found on a great variety of terrains. It's improved by a Mine.
 *
 * Upon discovering Atomic Theory - and especially if you are the first to discover it - you should try to ensure
 * that you and your allies control the majority of the Uranium on the map, as the ability to produce nuclear weapons
 * has the potential to dramatically affect the game to the point of crippling entire civilizations. Likewise,
 * if you have strained relations with AI leaders, you should either try to befriend them or deprive them of their
 * Uranium before it can be used to make nuclear weapons.
 *
 * City-states cannot complete the Manhattan Project, so those that have access to Uranium will never use it to make
 * nuclear weapons. Other civilizations, however, can gain access to a city-state's Uranium by conquering it or becoming its ally.
 *
 * Civilopedia entry
 *
 * Uranium is a radioactive natural mineral that, once processed and enriched, can be used to generate enormous energy
 * or blow things up. Discovered in the 18th century, it wasn't until the 20th, during the terrible crucible of World War II,
 * that American scientists learned how to craft the mildly dangerous material into the most deadly explosive that history
 * has ever seen. When used to power nuclear generators, uranium is seen as a "clean" energy, since it doesn't release
 * the huge amounts of air pollution created by coal or petroleum. On the other hand, used nuclear fuel is incredibly
 * toxic and poisons land, air, water, and/or any living organisms that encounter it for hundreds of years,
 * so this is a problem too. Remember that uranium is a strategic resource, and thus it is consumed as you construct
 * the associated units and buildings.
 */
public class Uranium extends AbstractResource {

    public static final ResourceType RESOURCE_TYPE = ResourceType.URANIUM;

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
        return tile.isIn(Desert.class, Plains.class, Tundra.class, Snow.class) || tile.hasFeature(Hill.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.ATOMIC_THEORY);
    }
}
