package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.Supply;
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
    public Supply getBaseSupply() {
        return new Supply().setFood(1).setGold(1);
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
