package com.tsoft.civilization.tile.terrain.desert;

import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Basic Production: (none)
 * Movement Cost: 1; Defensive Bonus: -33%
 */
public class Desert extends AbstractTerrain {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final DesertView VIEW = new DesertView();

    @Getter
    private final boolean canBuildCity = true;

    @Override
    public TerrainType getTileType() {
        return TerrainType.EARTH_PLAIN;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.EMPTY;
    }

    @Override
    public int getDefensiveBonusPercent() {
        return -33;
    }

    @Override
    public DesertView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
