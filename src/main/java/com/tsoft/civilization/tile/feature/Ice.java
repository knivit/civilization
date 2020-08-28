package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.web.view.tile.feature.IceView;

import java.util.UUID;

/**
 * Impassable except to Air and Submarine units.
 */
public class Ice extends TerrainFeature<IceView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final IceView VIEW = new IceView();

    @Override
    public TileType getTileType() {
        return TileType.SEA;
    }

    @Override
    public Supply getSupply() {
        return Supply.EMPTY_SUPPLY;
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
