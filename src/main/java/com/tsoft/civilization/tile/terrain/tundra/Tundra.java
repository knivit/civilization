package com.tsoft.civilization.tile.terrain.tundra;

import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Basic Production: 1 Food
 * Movement Cost: 1
 */
public class Tundra extends AbstractTerrain {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final TundraView VIEW = new TundraView();

    @Getter
    private final boolean canBuildCity = true;

    @Override
    public TerrainType getTileType() {
        return TerrainType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(1).build();
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
