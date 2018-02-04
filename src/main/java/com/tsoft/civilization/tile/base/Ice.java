package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.web.view.tile.base.IceView;

import java.util.UUID;

/**
 * Impassable except to Air and Submarine units.
 */
public class Ice extends AbstractTile<IceView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final IceView VIEW = new IceView();

    @Override
    public TileType getTileType() {
        return TileType.SEA;
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
    public IceView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
