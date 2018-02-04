package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.web.view.tile.feature.FalloutView;

import java.util.UUID;

/**
 * Fallout must be cleared by a Worker before any improvements can be built.
 */
public class Fallout extends TerrainFeature<FalloutView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final FalloutView VIEW = new FalloutView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getSupply() {
        return new Supply().setFood(-3).setProduction(-3).setGold(-3);
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
    public FalloutView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
