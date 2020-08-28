package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.web.view.tile.base.PlainView;

import java.util.UUID;

/**
 * Basic Production: 1 Food, 1 Hammer
 * Movement Cost: 1; Defensive Bonus: -33%
 */
public class Plain extends AbstractTile<PlainView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final PlainView VIEW = new PlainView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(1).production(1).build();
    }

    @Override
    public boolean canBuildCity() {
        return true;
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
