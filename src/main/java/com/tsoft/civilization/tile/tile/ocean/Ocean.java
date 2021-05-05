package com.tsoft.civilization.tile.tile.ocean;

import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileType;
import com.tsoft.civilization.economic.Supply;

import java.util.UUID;

/**
 * Basic Production: 1 Food, 1 Gold
 * Movement Cost: 1
 */
public class Ocean extends AbstractTile {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final OceanView VIEW = new OceanView();
    private boolean isDeepOcean;

    public boolean isDeepOcean() {
        return isDeepOcean;
    }

    public void setDeepOcean(boolean isDeepOcean) {
        this.isDeepOcean = isDeepOcean;
    }

    @Override
    public TileType getTileType() {
        return TileType.SEA;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(1).gold(1).build();
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
    public OceanView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
