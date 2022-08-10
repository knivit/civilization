package com.tsoft.civilization.tile.feature.fallout;

import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.terrain.TerrainType;

import java.util.UUID;

/**
 * Fallout
 * -------
 * Terrain feature
 * Yields
 *   -3 Food
 *   -3 Production
 *   -3 Gold
 * Unit Effects
 *   -15 Combat modifier
 *    2 Movement cost
 *
 * Game Info
 * ---------
 * Fallout appears in the area-of-effect of a nuclear detonation. It can be removed if scrubbed clean by a Worker.
 *
 * Civilopedia entry
 * -----------------
 * Fallout is the residual radiation left over following a nuclear explosion. The fallout "falls out" of the air as a layer
 * of radioactive particles which are highly dangerous to plants and animals, killing them immediately or damaging their DNA,
 * giving them cancer, other diseases, or unfortunate mutations. Depending upon the type of nuclear explosion, the land may
 * remain poisoned for decades, possibly centuries. Cleanup requires the replacement of the contaminated buildings, soil and vegetation.
 *
 * Fallout must be cleared by a Worker before any improvements can be built.
 */
public class Fallout extends AbstractFeature {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final FalloutView VIEW = new FalloutView();

    @Override
    public TerrainType getTileType() {
        return TerrainType.EARTH_PLAIN;
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().food(-3).production(-3).gold(-3).build();
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
    public FalloutView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
