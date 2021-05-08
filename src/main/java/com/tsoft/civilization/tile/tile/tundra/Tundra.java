package com.tsoft.civilization.tile.tile.tundra;

import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileType;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Basic Production: 1 Food
 * Movement Cost: 1
 */
public class Tundra extends AbstractTile {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final TundraView VIEW = new TundraView();

    @Getter
    private final boolean canBuildCity = true;

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(1).build();
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
