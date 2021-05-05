package com.tsoft.civilization.tile.tile.grassland;

import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileType;
import com.tsoft.civilization.economic.Supply;

import java.util.UUID;

/**
 * Basic Production: 2 Food
 * Movement Cost: 1; Defensive Bonus: -33%
 */
public class Grassland extends AbstractTile {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final GrasslandView VIEW = new GrasslandView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(2).build();
    }

    @Override
    public boolean canBuildCity() {
        return true;
    }

    public int getDefensiveBonusPercent() {
        return -33;
    }

    @Override
    public GrasslandView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
