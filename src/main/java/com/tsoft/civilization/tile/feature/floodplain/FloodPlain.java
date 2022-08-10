package com.tsoft.civilization.tile.feature.floodplain;

import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.terrain.TerrainType;

import java.util.UUID;

/**
 * Production Modifier: +2 Food
 */
public class FloodPlain extends AbstractFeature {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final FloodPlainView VIEW = new FloodPlainView();

    @Override
    public TerrainType getTileType() {
        return TerrainType.EARTH_PLAIN;
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().food(2).build();
    }

    @Override
    public boolean canBuildCity() {
        return true;
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
    public FloodPlainView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
