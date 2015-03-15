package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.TileSupply;
import com.tsoft.civilization.web.view.tile.base.PlainView;

import java.util.UUID;

public class Plain extends AbstractTile<PlainView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final PlainView VIEW = new PlainView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public TileSupply getBaseSupply() {
        return new TileSupply(1, 1, 0);
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
