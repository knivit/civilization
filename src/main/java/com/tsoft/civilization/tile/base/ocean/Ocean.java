package com.tsoft.civilization.tile.base.ocean;

import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.world.economic.Supply;

import java.util.UUID;

/**
 * Basic Production: 1 Food, 1 Gold
 * Movement Cost: 1
 */
public class Ocean extends AbstractTile<OceanView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final OceanView VIEW = new OceanView();

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
