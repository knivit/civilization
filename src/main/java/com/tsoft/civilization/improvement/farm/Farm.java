package com.tsoft.civilization.improvement.farm;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.AbstractImprovementView;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.desert.Desert;
import com.tsoft.civilization.tile.base.grassland.Grassland;
import com.tsoft.civilization.tile.base.plain.Plain;
import com.tsoft.civilization.tile.base.tundra.Tundra;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.economic.Supply;

import java.util.UUID;

/**
 * Basic agricultural improvement. Available from the start of the game.
 *
 * Constructed on: Plains, grassland, desert, Hill and tundra (only if adjacent to fresh water)
 * Wheat bonus resource
 * Effect:
 *   +1 Food
 *   +1 additional Food on fresh water tiles after researching Civil Service
 *   +1 additional Food on non-fresh water tiles after researching Fertilizer
 *
 * Farming is one of the earliest and most important of all human professions, as it allowed mankind
 * to stop migrating and settle in one location without depleting the local resources.
 *
 * Farms can be constructed on almost any land tiles to improve a hex's Food output.
 * Having access to fresh water enhances a Farm's effect on tiles earlier because Civil Service is available well before Fertilizer.
 */
public class Farm extends AbstractImprovement {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final CombatStrength COMBAT_STRENGTH = CombatStrength.builder()
        .defenseStrength(0)
        .build();

    private static final AbstractImprovementView VIEW = new FarmView();

    public Farm(Civilization civilization, Point location) {
        super(civilization, location);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return true;
    }

    @Override
    public boolean acceptTile(AbstractTile tile) {
        if (tile.isIn(Grassland.class, Plain.class, Desert.class, Tundra.class)) {
            if (tile.hasFeature(Hill.class)) {
                // TODO check adjacency to freah water
                return true;
            }
            return true;
        }
        return false;
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
