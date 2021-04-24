package com.tsoft.civilization.world.scenario;

import com.tsoft.civilization.civilization.Civilization;

public class BarbariansScenario implements Scenario {

    /**
     * https://civilization.fandom.com/wiki/Barbarian_(Civ5)
     *
     * Barbarians are loosely-organized bands of "wild" people that form automatically throughout the game,
     * based in encampments in land that isn't owned by any civilization or a city-state.
     *
     * Tech Progression
     * ----------------
     * Unlike in previous games, barbarians will advance in technologies beyond the Medieval Era,
     * being capable of producing units equal to that of what the most advanced civilization can produce at the time.
     * This is especially true if Raging Barbarians is turned on, with hostile barbarians being produced with
     * new technologies almost the turn the first civilization finishes researching it.
     * It may be theorized that these aren't old-fashioned barbarians anymore, but rather bandits...or in the later eras,
     * "freedom fighters", terrorists, or drug cartels.
     *
     * Behavior and Activities
     * -----------------------
     * Barbarians do not make cities like civilizations can - their "cities" are their encampments,
     * which produce only military units. The rate of encampment creation depends on the difficulty level,
     * becoming greater on higher difficulties. It is increased even further if the option Raging Barbarians is selected.
     *
     * Barbarians are always hostile to all civilizations and city-states, and will spawn fighting units in each encampment
     * until it is destroyed. Encampments appear with a unit already in them, and all subsequent units created
     * will start roaming the lands immediately (unless the encampment has been left undefended for some reason,
     * in which case the new unit will stay right in the encampment). Unlike civilization units, barbarian units
     * do not use up strategic resources, so they can produce any base land unit, and many naval units,
     * regardless of their typical cost.
     *
     * Possible barbarian units include the ones unavailable to any playable civilization,
     * such as the Brute (which has the same strength as a Warrior) and the Hand-Axe.
     * Encampments located on a coastal tile also have the ability to produce Galleys and other naval units.
     *
     * The barbarians' main activity is harassing cities. They will move in and try to pillage improvements,
     * attacking the most important ones first (those accessing a luxury resource, for example).
     * If injured, they have the ability to heal every time they pillage an improvement.
     * With enough units nearby barbarians will begin to attack cities; if brought to zero HP,
     * a city will be pillaged and may lose Gold, Population, and buildings.
     *
     * Barbarians also go after civilians and try to capture them (by moving into the same tile they occupy).
     * Captured civilians are then moved to the nearest encampment, where they are held captive until someone
     * clears the encampment. The civilian unit is then freed, and if the captured civilian did not originally
     * belong to the player who clears the encampment, they get to choose whether to take it for themselves,
     * or return it to its original owner (which will net a nice boost in diplomatic relations with their leader).
     *
     * Barbarian units roam the countryside (and the seas), all the time, attacking at will.
     * However, they won't attack stronger units, or ones that are currently in a strong position
     * (such as on a hill or fortified). When there are multiple targets, they usually attack the weakest unit,
     * taking into account their health. There will be one barbarian unit at each encampment at all times,
     * guarding it in a fortified stance. Depending on the difficulty setting, though,
     * this unit may go out a short way to make an opportunistic attack or capture an unwary civilian unit.
     *
     * Finally, in Brave New World, barbarians will also try to pillage any trade route they can reach.
     * The route is immediately terminated and, what's even worse, the trade unit that was on it is captured
     * and turned into a new barbarian unit! This could have results ranging from purely annoying to near-disastrous,
     * especially in the early game.
     *
     * Earning Experience When Fighting Barbarians
     * -------------------------------------------
     * Units earn experience normally when fighting barbarians, but they can't earn more than two promotions through
     * that experience. This means that fighting barbarians can be a good source of experience for units early in the game,
     * but later on, when they've earned many promotions, or when new units are constructed with two promotions,
     * barbarians become more of an annoyance than an opportunity.
     *
     * Strategy
     * --------
     * It is very important to defend against barbarians, especially during the early game - they, rather than other
     * civilizations, will usually be the main danger in this period. Always try to keep a military unit in every city,
     * keep it alert for roaming barbarians, and move it to protect Workers and improvements when they are spotted.
     * When moving Settlers, it is safest to move them together with a military unit. Get ranged units as soon as possible
     * to start clearing encampments safely.
     *
     * Usually, units enjoy a combat bonus against barbarian units. This bonus, however, diminishes almost to nothing
     * at the higher difficulty levels. The bonus can be increased at all levels by adopting the Honor Social Policy,
     * which gives units an additional bonus against barbarians. Also, civilizations with this Policy will gain
     * a fixed amount of Culture Culture points per each barbarian unit killed (based on the Combat strength of the unit).
     *
     * Certain special abilities allow civilizations to "convert" barbarians to their side. Germany has a chance
     * to get barbarian units to join, if they are defeated while defending their encampment. Similarly, the Ottomans
     * have a 50% chance to recruit a barbarian naval unit (and earn Gold) by moving a ship next to it.
     * While city-states react to this as if the barbarian unit was destroyed, no Culture will be earned even
     * if the Honor policy tree is active. For the Aztecs, which gain Culture for every enemy killed,
     * activating the Honor tree doubles the Culture Culture gained.
     * In Brave New World, the Reformation Belief "Heathen Conversion" allows Missionaries to convert Barbarian units
     * if they are adjacent.
     *
     * After trade routes are established, be on the lookout for barbarians near the route. In the beginning of the game
     * a lot of land remains unexplored and dark, which is the ideal situation for spawning barbarian encampments.
     * Their units can pillage a route before anyone is aware of the threat - they can even turn a land trade unit into
     * a mounted barbarian unit. So, unless otherwise occupied, dedicate a couple of units to patrol between
     * the source and target city of the trade route.
     *
     * City-states will occasionally post seek-and-destroy quests against barbarian encampments in the early game.
     * Try to complete them, because of all quest types they give the largest single influence boost,
     * and are an easy way to make friends (and even allies) with city-states. To do this, try to form a task force
     * of one melee and one ranged unit. Move them to the more distant city-states; destroy those encampments closer
     * to home territory with the units guarding the cities. Barbarian encampments are often pretty close to civilized
     * territory, so when one is discovered, friendly units may be in a good position to attack it.
     * Wait until a city-state marks it with a quest, or destroying the encampment will not yield any influence.
     * If other civilizations have military units nearby, they may swoop in and seize the encampment after it has been
     * weakened by a turn or two of attacks. If this happens, the Gold reward and Influence boost will go to them.
     * It is especially annoying, for example, when your ranged unit finally kills the Barbarian defending an Encampment
     * only for another civilization's Scout to walk in and collect the prize before you can.
     * Try to avoid this by blocking them as much as possible, or if necessary, waiting until they leave the area.
     * However, know that nearby city-states may themselves resolve to attack the Encampment after many turns
     * have passed without anyone taking care of it.
     *
     * Trivia
     * ------
     * Even when the "No Barbarians" option is turned on, a raiding party of three Barbarian mobile units can spawn
     * if the Unhappiness in a civilization is above 10.
     */

    @Override
    public ScenarioApplyResult apply(Civilization civilization) {
        // Create an encampment

        // Create a military unit in it

        return null;
    }
}
