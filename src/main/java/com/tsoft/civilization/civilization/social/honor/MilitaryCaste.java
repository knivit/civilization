package com.tsoft.civilization.civilization.social.honor;

import com.tsoft.civilization.civilization.social.AbstractSocialPolicy;
import com.tsoft.civilization.world.Year;
import lombok.Getter;

import java.util.UUID;

/**
 * Military Caste
 * --------------
 * Is a social policy in Civilization V. It is part of the Honor tree and requires Discipline.
 *
 * After a Military Caste system is introduced in a society, the strict separation of military and civilians promotes both peace
 * of mind for the civilians and greater concentration for the soldiers. This often finds expression in unique art forms which
 * increase local Culture.
 *
 * Each City with a garrison increases Local City Happiness by 1 and Culture by 2.

 * Strategy
 * --------
 * This Policy functions similarly to Oligarchy in the sense that its bonus appears only if a Unit is stationed in the city.
 * The bonus itself is quite different, though, and more useful in peaceful times than during wars (when you want most of your army away
 * from your cities).
 *
 * One great way to use Military Caste is to station a unit in each new city you settle, so that it starts gaining territory
 * right away (thanks to the +2 Culture Culture bonus, which applies both locally and on empire level). And of course,
 * the extra Happiness is always handy. Bottom line - always keep units garrisoned in all your cities when you're not using
 * these units for other purposes.
 *
 * A more general benefit of Military Caste, especially if you rush it (taking it as a third Policy immediately after Discipline)
 * is to use it as an early game booster for social progress. This will work better the more cities you have
 * (each one adding +2 Culture Culture), although you must remember that each new city also increases the Culture cost of subsequent Policies.
 * You will need to strike a fine balance.
 *
 * Civilopedia entry
 * -----------------
 * In a society with a military caste, all members of the military are from a specific tribe, nationality, religion,
 * or other socio-economic subgroup. Citizens who are not part of the caste are forbidden from joining the military and often
 * from carrying any weapons at all, often because the military caste fears that the citizens might use the weapons against them.
 * Much of feudal Japan was under a military caste system, as was ancient Sparta.
 */
public class MilitaryCaste extends AbstractSocialPolicy {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final int era = Year.ANCIENT_ERA;

    @Getter
    private final String requirement = Honor.CLASS_UUID;
}
