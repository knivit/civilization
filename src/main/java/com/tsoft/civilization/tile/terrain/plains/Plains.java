package com.tsoft.civilization.tile.terrain.plains;

import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Basic Production: 1 Food, 1 Hammer
 * Movement Cost: 1; Defensive Bonus: -33%
 */
public class Plains extends AbstractTerrain {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final PlainsView VIEW = new PlainsView();

    @Getter
    private final boolean canBuildCity = true;

    @Override
    public TerrainType getTileType() {
        return TerrainType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(1).production(1).build();
    }

    @Override
    public int getDefensiveBonusPercent() {
        return -33;
    }

    @Override
    public PlainsView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
