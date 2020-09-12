package com.tsoft.civilization.tile.feature.marsh;

import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.TileType;

import java.util.UUID;

/**
 * Production Modifier: Food -1.
 * Movement Cost: 2
 * Notes: Sugar resources can occur here, but the marsh needs to be removed by Workers
 * (ability gained with Masonry) before a Plantation can be built.
 * Some improvements such as farms can be built directly on a marsh, but with the -1 food penalty.
 */
public class Marsh extends TerrainFeature<MarshView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final MarshView VIEW = new MarshView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_MARSH;
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().food(-1).build();
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
    public MarshView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
