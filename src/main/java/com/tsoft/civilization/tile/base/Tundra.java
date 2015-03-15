package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.TileSupply;
import com.tsoft.civilization.web.view.tile.base.TundraView;

import java.util.UUID;

public class Tundra extends AbstractTile<TundraView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final TundraView VIEW = new TundraView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public TileSupply getBaseSupply() {
        return new TileSupply(1, 0, 0);
    }

    @Override
    public boolean canBuildCity() {
        return true;
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
