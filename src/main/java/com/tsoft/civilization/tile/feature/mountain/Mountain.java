package com.tsoft.civilization.tile.feature.mountain;

import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.economic.Supply;

import java.util.UUID;

/**
 * Production: 0
 * Movement Cost: Impassable except to Air units; Defensive Bonus: +25%
 */
public class Mountain extends AbstractFeature {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final MountainView VIEW = new MountainView();

    @Override
    public TerrainType getTileType() {
        return TerrainType.EARTH_ROUGH;
    }

    @Override
    public Supply getSupply() {
        return Supply.EMPTY;
    }

    @Override
    public boolean canBuildCity() {
        return false;
    }

    @Override
    public int getDefensiveBonusPercent() {
        return 25;
    }

    @Override
    public int getMaxStrength() {
        return 0;
    }

    @Override
    public MountainView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
