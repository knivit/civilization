package com.tsoft.civilization.tile.terrain.grassland;

import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Basic Production: 2 Food
 * Movement Cost: 1; Defensive Bonus: -33%
 */
public class Grassland extends AbstractTerrain {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final GrasslandView VIEW = new GrasslandView();

    @Getter
    private final boolean canBuildCity = true;

    @Override
    public TerrainType getTileType() {
        return TerrainType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(2).build();
    }

    public int getDefensiveBonusPercent() {
        return -33;
    }

    @Override
    public GrasslandView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
