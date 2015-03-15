package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.TileSupply;
import com.tsoft.civilization.web.view.tile.base.LakeView;

import java.util.UUID;

public class Lake extends AbstractTile<LakeView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final LakeView VIEW = new LakeView();

    @Override
    public TileType getTileType() {
        return TileType.SEA;
    }

    @Override
    public TileSupply getBaseSupply() {
        return new TileSupply(2, 0, 1);
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
    public LakeView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
