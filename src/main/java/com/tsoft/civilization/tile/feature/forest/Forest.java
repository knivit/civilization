package com.tsoft.civilization.tile.feature.forest;

import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.tile.TileType;

import java.util.UUID;

/**
 * Production: 1 Food, 1 Hammer
 * Movement Cost: 2; Defensive Bonus: +25%
 * Notes: Tiles with forests covering them always yield 1 Food and 1 Production, regardless of the underlying terrain type.
 * Forests can be cleared by Workers (with the Advent of Mining).
 */
public class Forest extends AbstractFeature {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final ForestView VIEW = new ForestView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_ROUGH;
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().food(1).production(1).build();
    }

    @Override
    public boolean isBlockingTileSupply() {
        return true;
    }

    @Override
    public boolean canBuildCity() {
        return true;
    }

    @Override
    public int getDefensiveBonusPercent() {
        return 25;
    }

    @Override
    public int getMaxStrength() {
        return 5;
    }

    @Override
    public ForestView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
