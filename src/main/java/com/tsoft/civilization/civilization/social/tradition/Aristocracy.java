package com.tsoft.civilization.civilization.social.tradition;

import com.tsoft.civilization.civilization.social.AbstractSocialPolicy;
import com.tsoft.civilization.world.Year;
import lombok.Getter;

import java.util.UUID;

/**
 * Aristocracy is a social policy in Civilization V. It is part of the Tradition tree.
 *
 * The development of a higher class is essential to a feudal society's refinement. After its formation, citizens become more
 * enthusiastic when building Wonders. The general populace also becomes somewhat more able to cope with overcrowding in big cities.
 *
 * Game Info
 * ---------
 * +15% Production when building Wonders.
 * +1 Happiness for every 10 20xPopulation5.png Citizens in a City.
 *
 * Strategy
 * --------
 * The main bonus of Aristocracy is the Wonder-building boost, and it should determine your strategy. Aristocracy is a stand-alone
 * level 1 Policy, not connected to any other policies in the tree. This makes it the choice of the player when exactly to adopt it.
 *
 * If you want to build Wonders early (note that this includes national wonders such as the National College), you can adopt it
 * first of all Tradition policies; if not you may adopt it last to complete the tree. Or anywhere in between, when the right moment arises.
 * The second bonus doesn't benefit you much in the early game (what city reaches 10 Population before turn 100?), but it becomes
 * useful later on in the game.
 *
 * Civilopedia entry
 * -----------------
 * Aristocracy is a form of government in which a group of the most prominent citizens rule. Membership to this group may be hereditary,
 * or it may be through merit or wealth or all three. The term is derived from the Greek word aristokratia, meaning "the rule of the best."
 * There is a very thin line between an aristocracy and an oligarchy, and which form a government actually is might very well
 * depend upon one's point of view: the rulers might consider themselves aristocrats, while everybody else sees them as oligarchs.
 */
public class Aristocracy extends AbstractSocialPolicy {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int era = Year.ANCIENT_ERA;

    @Getter
    private final String requirement = Tradition.CLASS_UUID;

}
