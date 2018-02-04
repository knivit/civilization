package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.web.view.tile.feature.FloodPlainView;

import java.util.UUID;

public class FloodPlain extends TerrainFeature<FloodPlainView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final FloodPlainView VIEW = new FloodPlainView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getSupply() {
        return new Supply().setFood(2);
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
