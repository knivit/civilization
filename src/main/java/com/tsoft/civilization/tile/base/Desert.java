package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.web.view.tile.base.DesertView;

import java.util.UUID;

public class Desert extends AbstractTile<DesertView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final DesertView VIEW = new DesertView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return new Supply();
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
    public DesertView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
