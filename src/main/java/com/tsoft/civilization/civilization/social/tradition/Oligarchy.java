package com.tsoft.civilization.civilization.social.tradition;

import com.tsoft.civilization.civilization.social.AbstractSocialPolicy;
import com.tsoft.civilization.world.Year;
import lombok.Getter;

import java.util.UUID;

/**
 * Oligarchy is a social policy in Civilization V. It is part of the Tradition tree.
 *
 * The formation of an Oligarchy is the beginning of feudalism, when a particular group of people native to this part of the
 * country take the power and hold it by any means necessary. This benefits greatly local defense, since this group undertakes
 * the obligation to pay any units from the central army stationed there, and is interested in helping it out by any means necessary.
 * After all, they defend their interests!
 *
 * Game Info
 * ---------
 * Garrisoned units cost no maintenance.
 * Cities with a garrison gain +50% Ranged strength Ranged Combat Strength.
 *
 * Strategy
 * --------
 * Adopting Oligarchy is necessary to progress further down the tree, so it is generally a no-brainer. To make best use of it,
 * keep a unit stationed in each city - this will offset its maintenance cost, freeing up Gold for other, more important purposes.
 * And, of course, it greatly increases the defensive power of a city, which is extremely useful for defense-oriented empires in
 * all stages of the game.
 *
 *
 * Civilopedia entry
 * -----------------
 * Oligarchy is a form of government in which a small segment of society has all of the power. Power might be held by a specific
 * family or tribe, or people of a certain religion or from a special caste. Some city-states in ancient Greece were oligarchies,
 * where all power was in the hands of a few elite families. Apartheid South Africa would be a modern oligarchy,
 * the white minority holding all of the power. Pre-9-11 Iraq too was an oligarchy, with power concentrated in the hands
 * of members of Saddam Hussein's family and tribe.
 */
public class Oligarchy extends AbstractSocialPolicy {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int era = Year.ANCIENT_ERA;

    @Getter
    private final String requirement = Tradition.CLASS_UUID;
}
