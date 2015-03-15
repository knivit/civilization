package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.TileSupply;
import com.tsoft.civilization.web.view.tile.base.OceanView;

import java.util.UUID;

public class Ocean extends AbstractTile<OceanView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final OceanView VIEW = new OceanView();

    @Override
    public TileType getTileType() {
        return TileType.SEA;
    }

    @Override
    public TileSupply getBaseSupply() {
        return new TileSupply(0, 0, 0);
    }

    @Override
    public boolean canBuildCity() {
        return false;
    }

    @Override
    public int getDefensiveBonusPercent() {
        return 0;
    }

    @Override
    public OceanView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
