package com.tsoft.civilization.tile.tile.snow;

import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileType;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Basic Production: (none)
 * Movement Cost: 1; Defensive Bonus: -33%
 */
public class Snow extends AbstractTile {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final SnowView VIEW = new SnowView();

    @Getter
    private final boolean canBuildCity = false;

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.EMPTY;
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
