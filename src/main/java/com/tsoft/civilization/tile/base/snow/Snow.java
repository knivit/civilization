package com.tsoft.civilization.tile.base.snow;

import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.economic.Supply;

import java.util.UUID;

/**
 * Basic Production: (none)
 * Movement Cost: 1; Defensive Bonus: -33%
 */
public class Snow extends AbstractTile {
    public static final Snow STUB = new Snow();

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final SnowView VIEW = new SnowView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.EMPTY;
    }

    @Override
    public boolean canBuildCity() {
        return false;
    }

    @Override
    public int getDefensiveBonusPercent() {
        return -33;
    }

    @Override
    public SnowView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
