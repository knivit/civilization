package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.web.view.tile.base.MountainView;

import java.util.UUID;

public class Mountain extends AbstractTile<MountainView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final MountainView VIEW = new MountainView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_ROUGH;
    }

    @Override
    public Supply getBaseSupply() {
        return new Supply();
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
    public MountainView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
