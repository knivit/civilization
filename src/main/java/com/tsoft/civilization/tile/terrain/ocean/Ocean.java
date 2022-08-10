package com.tsoft.civilization.tile.terrain.ocean;

import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Ocean
 * -----
 * Base terrain
 * Yields
 *   1 Food
 *   0 Gold with BNW-only.png,
 *   else 1 Gold
 *
 * Unit Effects
 *   1 Movement cost
 *
 * Possible features found
 * Ice	Atoll
 *
 * Game Info
 * Base terrain, forming the world ocean far (1-3 tiles away) from land tiles.
 *
 * Strategy
 * --------
 * Ocean tiles benefit from the +1 Food bonus of the Lighthouse, if they're in range of the city with it.
 *
 * Moving through Ocean tiles is impossible before the Renaissance Era (unless you are playing as Polynesia).
 *
 * Civilopedia entry
 * -----------------
 * Ocean hexes are deep-water hexes. They provide food and gold to a city once the city has the technology to access them.
 */
public class Ocean extends AbstractTerrain {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final OceanView VIEW = new OceanView();
    private boolean isDeepOcean;

    @Getter
    private final boolean canBuildCity = false;

    public boolean isDeepOcean() {
        return isDeepOcean;
    }

    public void setDeepOcean(boolean isDeepOcean) {
        this.isDeepOcean = isDeepOcean;
    }

    @Override
    public TerrainType getTileType() {
        return TerrainType.SEA;
    }

    @Override
    public Supply getBaseSupply() {
        return Supply.builder().food(1).gold(1).build();
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
