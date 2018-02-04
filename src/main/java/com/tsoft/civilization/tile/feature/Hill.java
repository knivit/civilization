package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.web.view.tile.feature.HillView;

import java.util.UUID;

/**
 * Hills yield 0 Food and 2 Hammers, regardless of the underlying terrain type.
 */
public class Hill extends TerrainFeature<HillView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final HillView VIEW = new HillView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_ROUGH;
    }

    @Override
    public Supply getSupply() {
        return new Supply().setProduction(2);
    }

    @Override
    public boolean isBlockingTileSupply() {
        return false;
    }

    @Override
    public boolean canBuildCity() {
        return true;
    }

    @Override
    public int getDefensiveBonusPercent() {
        return 25;
    }

    @Override
    public int getMaxStrength() {
        return 5;
    }

    @Override
    public HillView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
