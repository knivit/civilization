package com.tsoft.civilization.tile.tile.plain;

import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileType;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Basic Production: 1 Food, 1 Hammer
 * Movement Cost: 1; Defensive Bonus: -33%
 */
public class Plain extends AbstractTile {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final PlainView VIEW = new PlainView();

    @Getter
    private final boolean canBuildCity = true;

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(1).production(1).build();
    }

    @Override
    public int getDefensiveBonusPercent() {
        return -33;
    }

    @Override
    public PlainView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
