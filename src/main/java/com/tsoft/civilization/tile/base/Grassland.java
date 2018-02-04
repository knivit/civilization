package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.web.view.tile.base.GrasslandView;

import java.util.UUID;

public class Grassland extends AbstractTile<GrasslandView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final GrasslandView VIEW = new GrasslandView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return new Supply().setFood(2);
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
