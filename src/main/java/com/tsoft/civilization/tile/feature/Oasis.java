package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.world.economic.TileSupply;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.web.view.tile.feature.OasisView;

import java.util.UUID;

/**
 * Oasis can't be improved (except with roads)
 */
public class Oasis extends AbstractFeature<OasisView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final OasisView VIEW = new OasisView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public TileSupply getSupply() {
        return new TileSupply(3, 0, 1);
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
    public int getMaxStrength() {
        return 5;
    }

    @Override
    public OasisView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
