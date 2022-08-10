package com.tsoft.civilization.tile.feature.oasis;

import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.terrain.TerrainType;

import java.util.UUID;

/**
 * Production Modifier: +3 Food, +1 Gold
 * Oasis can't be improved (except with roads)
 */
public class Oasis extends AbstractFeature {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final OasisView VIEW = new OasisView();

    @Override
    public TerrainType getTileType() {
        return TerrainType.EARTH_PLAIN;
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().food(3).gold(1).build();
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
    public OasisView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
