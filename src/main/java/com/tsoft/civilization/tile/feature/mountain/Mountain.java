package com.tsoft.civilization.tile.feature.mountain;

import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.economic.Supply;

import java.util.UUID;

/**
 * Production: 0
 * Movement Cost: Impassable except to Air units; Defensive Bonus: +25%
 */
public class Mountain extends TerrainFeature {
    public final static Mountain STUB = new Mountain();

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final MountainView VIEW = new MountainView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_ROUGH;
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
