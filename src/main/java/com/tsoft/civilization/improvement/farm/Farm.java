package com.tsoft.civilization.improvement.farm;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.AbstractImprovementView;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.economic.Supply;

import java.util.UUID;

/**
 * Basic agricultural improvement. Available from the start of the game.
 *
 * Constructed on:
 * Plains, grassland, desert
 * Hill and tundra (only if adjacent to fresh water)
 * Wheat bonus resource
 * Effect:
 *   +1 Food
 *   +1 additional Food on fresh water tiles after researching Civil Service
 *   +1 additional Food on non-fresh water tiles after researching Fertilizer
 * StrategyEdit
 * Farms can be constructed on almost any land tiles to improve a hex's Food output.
 * Having access to fresh water enhances a Farm's effect on tiles earlier because Civil Service is available well before Fertilizer.
 */
public class Farm extends AbstractImprovement {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
        .setStrength(0);

    private static final AbstractImprovementView VIEW = new FarmView();

    public Farm(Civilization civilization, Point location) {
        super(civilization, location);
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().gold(-2).build();
    }

    @Override
    public AbstractImprovementView getView() {
        return VIEW;
    }

    @Override
    public CombatStrength getBaseCombatStrength() {
        return COMBAT_STRENGTH;
    }
}
