package com.tsoft.civilization.civilization.social.tradition;

import com.tsoft.civilization.civilization.social.AbstractSocialPolicy;
import com.tsoft.civilization.world.Year;
import lombok.Getter;

import java.util.UUID;

/**
 * The Tradition tree is available right from the Ancient Era. Choosing this policy first will give you the title "Lord"
 * for male leaders and "Lady" for female leaders.
 *
 * Adopting Tradition greatly increases the rate of border expansion (25%) in cities (by diminishing the accumulated
 * Culture needed for successive new tiles) and also grants +3 Culture in the Capital Capital. Unlocks building the Hanging Gardens wonder.
 *
 * Adopting all Policies in the Tradition tree will grant +15% Food Growth in all cities and a free Aqueduct in your first four cities.
 * It also allows the purchase of Great Engineers with Faith Faith starting in Industrial Era.
 */
public class Tradition extends AbstractSocialPolicy {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int era = Year.ANCIENT_ERA;

    @Getter
    private final String requirement = null;
}
