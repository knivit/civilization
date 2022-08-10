package com.tsoft.civilization.tile.feature.coast;

import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.economic.Supply;

import java.util.UUID;

/**
 * Basic Production: +1 Gold, +1 Food
 * Coastal tiles now often go several hexes out into the water,
 * allowing for better freedom of movement for ancient units that are restricted from deep ocean.
 */
public class Coast extends AbstractFeature {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final CoastView VIEW = new CoastView();

    @Override
    public TerrainType getTileType() {
        return TerrainType.SEA;
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().food(1).gold(1).build();
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
        return 0;
    }

    @Override
    public CoastView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
