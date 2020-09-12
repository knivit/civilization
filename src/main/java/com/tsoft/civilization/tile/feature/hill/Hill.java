package com.tsoft.civilization.tile.feature.hill;

import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.TileType;

import java.util.UUID;

/**
 * Basic Production: 0 Food, 2 Hammers
 * Movement Cost: 2; Defensive Bonus: +25%
 * Notes: Hills yield 0 Food and 2 Hammers, regardless of the underlying terrain type.
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
        return Supply.builder().production(2).build();
    }

    @Override
    public boolean isBlockingTileSupply() {
        return true;
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
