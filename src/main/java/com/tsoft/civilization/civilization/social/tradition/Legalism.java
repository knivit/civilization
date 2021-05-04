package com.tsoft.civilization.civilization.social.tradition;

import com.tsoft.civilization.civilization.social.AbstractSocialPolicy;
import com.tsoft.civilization.world.Year;
import lombok.Getter;

import java.util.UUID;

/**
 * Legalism is a social policy in Civilization V. It is part of the Tradition tree and requires Oligarchy.
 *
 * The introduction of a strict code of laws to govern the populace and its interaction with the central power puts an end
 * to many abuses of power and translates immediately into a more stable society. The people express their gratitude by undertaking
 * great works of culture in the empire's main cities - thus the first four of these receive a free cultural building.
 *
 * Game Info
 * ---------
 * Provides a free culture building in your first 4 cities.
 *
 * Strategy
 * --------
 * Legalism is a great Policy for early development. In most early cases, the empire's first four cities will get a free Monument,
 * and start expanding borders immediately! Not only that, but the Production Production can be contributed to build other
 * buildings and/or units, instead of losing time to build Monuments. This is often an early game Policy
 * (rushed towards by opening the tree, then adopting Oligarchy), unless the leader intends to build one of the early Wonders with Aristocracy.
 *
 * Detailed information on policy effect
 * -------------------------------------
 * The "free culture building" in each of the four cities will be the first in the culture chain that hasn't yet been constructed
 * in the respective city – the chain being the Monument, Amphitheater, Opera House, Museum, and Broadcast Tower, in that order
 * (the Temple replaces the Amphitheater in the base game). If the technology necessary to unlock that building hasn't been unlocked,
 * it will appear once the research is finished.
 *
 * For example, consider a city which has built a Monument already, in a civilization without Drama and Poetry. When unlocking Legalism,
 * normally an Amphitheater would appear. However this takes place before the technology has been researched and without the technology,
 * it cannot normally be built. As a result, no Amphitheater will appear immediately after the policy unlock, and will instead appear
 * when Drama and Poetry is researched.
 *
 * Buildings received from Legalism do not cost maintenance, but if a city already has any other culture buildings when the policy
 * is enacted, the cost still applies to those buildings. If later culture buildings are not required, it may be advisable to rely
 * on the free buildings from the policy and not build culture buildings, which will save gold in the long run. Alternatively,
 * if the Monument is built early, maintenance will be instead saved on the more expensive Amphitheater, but there will be no
 * immediate benefit until technology catches up.
 *
 * Civilopedia entry
 * -----------------
 * Legalism is a policy in which the relationship between the ruler and his or her subjects is laid out in a series of laws,
 * which both sides must adhere to strictly. If the law is fair and is properly enforced, the people will be content and the
 * ruler's position will be secure. While this may seem to cover most governments that follow the rule of law, legalism almost
 * achieved the status of a state religion in China during the Warring State Period.
 */
public class Legalism extends AbstractSocialPolicy {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int era = Year.ANCIENT_ERA;

    @Getter
    private final String requirement = Oligarchy.CLASS_UUID;
}
