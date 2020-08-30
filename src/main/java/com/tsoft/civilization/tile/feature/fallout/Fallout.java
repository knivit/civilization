package com.tsoft.civilization.tile.feature.fallout;

import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.web.view.tile.feature.FalloutView;

import java.util.UUID;

/**
 * Fallout
 * Production Modifiers: Food -3, Production -3, Gold -3
 * Movement Cost: 2
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
        return Supply.builder().food(-3).production(-3).gold(-3).build();
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
