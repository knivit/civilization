package com.tsoft.civilization.tile.feature.atoll;

import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.TileType;

import java.util.UUID;

/**
 * Atoll
 * Production Modifiers: Food 1, Production +1
 * Movement Cost: 1
 */
public class Atoll extends TerrainFeature<AtollView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final AtollView VIEW = new AtollView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().food(1).production(1).build();
    }

    @Override
    public boolean canBuildCity() {
        return false;
    }

    @Override
    public int getDefensiveBonusPercent() {
        return 0;
    }

    @Override
    public int getMaxStrength() {
        return 5;
    }

    @Override
    public AtollView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
