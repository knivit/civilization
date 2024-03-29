package com.tsoft.civilization.tile.feature.jungle;

import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.terrain.TerrainType;

import java.util.UUID;

/**
 * Production Modifier: -1 Hammer
 * Movement Cost: 2; Defensive Bonus: +25%
 * Notes: Jungle can be cleared by Workers (with the advent of Bronze Working).
 * Jungle no longer has a negative modifier to Food production.
 */
public class Jungle extends AbstractFeature {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final JungleView VIEW = new JungleView();

    @Override
    public TerrainType getTileType() {
        return TerrainType.EARTH_ROUGH;
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().production(-1).build();
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
