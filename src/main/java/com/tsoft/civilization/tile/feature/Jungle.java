package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.web.view.tile.feature.JungleView;

import java.util.UUID;

public class Jungle extends TerrainFeature<JungleView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final JungleView VIEW = new JungleView();

    @Override
    public TileType getTileType() {
        return TileType.EARTH_ROUGH;
    }

    @Override
    public Supply getSupply() {
        return new Supply().setProduction(-1);
    }

    @Override
    public boolean canBuildCity() {
        return true;
    }

    @Override
    public int getDefensiveBonusPercent() {
        return 25;
    }

    @Override
    public int getMaxStrength() {
        return 5;
    }

    @Override
    public JungleView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
