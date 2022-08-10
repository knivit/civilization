package com.tsoft.civilization.tile.feature.atoll;

import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.terrain.TerrainType;

import java.util.UUID;

/**
 * Atoll
 * -----
 * Terrain feature
 *
 * Yields
 *   2 Food
 *   1 Production
 *
 * Unit Effects
 *   1 Movement cost
 *
 * Terrain feature found in coastal regions.
 *
 * Base yield: 2 Food, 1 Production
 *
 * Strategy
 * --------
 * Atolls benefit from the Lighthouse's +1 Food bonus. Note, however, that they aren't considered a resource,
 * so they don't get additional benefits!
 *
 * Atolls count as water for movement purposes.
 *
 * In Brave New World, the Japanese civilization gets an additional bonus yield of +2 Culture from Atolls.
 *
 * Civilopedia entry
 * -----------------
 * An atoll is a formation of coral which creates an island. Atolls are typically ring-shaped, encompassing a central
 * shallow lagoon, and the islands are typically low and small. Atolls are one of the most common island types in the Pacific,
 * the other being islands of volcanic origin. Atolls are akin to oases, in that they are often the only points of land in hundreds
 * of square miles of pelagic expanse. Marine life and birds make ready use of atolls, and though humans have inhabited some,
 * atolls are often hostile for larger land animals. Their low profile means that storms regularly engulf them, and there is
 * often little shade from the powerful sun. As a result of his experiences and observations on the Beagle Voyage,
 * Charles Darwin was the first Westerner to correctly hypothesize how atolls were formed.
 */
public class Atoll extends AbstractFeature {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final AtollView VIEW = new AtollView();

    @Override
    public TerrainType getTileType() {
        return TerrainType.EARTH_PLAIN;
    }

    @Override
    public Supply getSupply() {
        return Supply.builder().food(2).production(1).build();
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
