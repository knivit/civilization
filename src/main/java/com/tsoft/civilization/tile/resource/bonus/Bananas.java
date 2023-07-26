package com.tsoft.civilization.tile.resource.bonus;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.jungle.Jungle;
import com.tsoft.civilization.tile.resource.*;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;

/**
 * Game Info
 *
 * Bonus resource.
 *
 *     Base yield:
 *         1 Food
 *     Modifiers:
 *         +2 Food, -1 Production from Plantation
 *         +1 Food from Granary
 *         +1 Food with Sun God Religious belief
 *
 * Strategy
 *
 * Bananas are only found in jungles. This makes them kind of difficult to find, but it also gives you a unique opportunity:
 * you can use the tile without any development whatsoever! Their initial yield is +3 Food (thanks to the jungle tile),
 * and a Granary adds another point of Food; on top of that, you can get a +2 Science boost from the jungle tile
 * when you build a University! Certain religious beliefs can add even more yield, turning a single resource tile into a plethora of opportunities.
 *
 * You can also decide to develop the Bananas with their proper improvement, the Plantation,
 * which will further improve their Food yield. However, to do so you'll need to chop down the jungle and revert the tile
 * to its base terrain, losing the Science bonus. So, think carefully about what you need for your nearby city:
 * a big Food booster, or a more diversified yield.
 *
 * Finally, Forts can be constructed on Bananas Bananas without removing the jungle.
 *
 * Civilopedia entry
 *
 * A banana is a popular tropical fruit. It, and its cousins the plantains, are an important part of many tropical peoples' diets.
 * Originating in Southeast Asia, the banana is now cultivated around the world.
 */
public class Bananas extends AbstractResource {

    private static final ResourceBaseState BASE_STATE = ResourceBaseState.builder()
        .supply(Supply.builder().food(1).build())
        .build();

    @Override
    public ResourceType getType() {
        return ResourceType.BANANAS;
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
        return tile.hasFeature(Jungle.class);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.CALENDAR);
    }
}
