package com.tsoft.civilization.tile.feature.ice;

import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.economic.Supply;

import java.util.UUID;

/**
 * Impassable except to Air and Submarine units.
 */
public class Ice extends AbstractFeature {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final IceView VIEW = new IceView();

    @Override
    public TerrainType getTileType() {
        return TerrainType.SEA;
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
        return 0;
    }

    @Override
    public int getMaxStrength() {
        return 0;
    }

    @Override
    public IceView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
