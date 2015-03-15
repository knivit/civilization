package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.world.economic.TileSupply;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.web.view.tile.feature.AtollView;

import java.util.UUID;

public class Atoll extends AbstractFeature<AtollView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final AtollView VIEW = new AtollView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_PLAIN;
    }

    @Override
    public TileSupply getSupply() {
        return new TileSupply(1, 1, 0);
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
    public AtollView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
