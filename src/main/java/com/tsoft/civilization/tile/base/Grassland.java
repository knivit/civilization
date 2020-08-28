package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.web.view.tile.base.GrasslandView;

import java.util.UUID;

/**
 * Basic Production: 2 Food
 * Movement Cost: 1; Defensive Bonus: -33%
 */
public class Grassland extends AbstractTile<GrasslandView> {
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
