package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.world.economic.TileSupply;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.web.view.tile.feature.FloodPlainView;

import java.util.UUID;

public class FloodPlain extends AbstractFeature<FloodPlainView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final FloodPlainView VIEW = new FloodPlainView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public TileSupply getSupply() {
        return new TileSupply(2, 0, 0);
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
