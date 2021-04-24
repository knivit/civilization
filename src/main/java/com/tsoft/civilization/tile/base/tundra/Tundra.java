package com.tsoft.civilization.tile.base.tundra;

import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.economic.Supply;

import java.util.UUID;

/**
 * Basic Production: 1 Food
 * Movement Cost: 1
 */
public class Tundra extends AbstractTile {
    public static final Tundra STUB = new Tundra();

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final TundraView VIEW = new TundraView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(1).build();
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
    public TundraView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
