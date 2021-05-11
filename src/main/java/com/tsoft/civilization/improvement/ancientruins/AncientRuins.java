package com.tsoft.civilization.improvement.ancientruins;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.AbstractImprovementView;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.desert.Desert;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.tile.plain.Plain;

import java.util.UUID;

/**
 * Ancient Ruins
 * -------------
 * Ancient Ruins in Civilization V are remnants of extinct civilizations, mementos of past greatness and order amid the chaos
 * of the wilderness. Budding "contemporary" civilizations may learn a lot from these places, which will help them in their early stages.
 * Not to be confused with City Ruins.
 *
 * Game Info
 * In order to "activate" an Ancient Ruin, a player needs to move any land unit (including a civilian unit) onto the tile
 * that contains the ruin. Alternatively, if a civilization's territory expands onto the ruins tile, it will also activate it.
 *
 * When activated, Ancient Ruins give a reward to the owner of the unit. This is a one-time bonus, after which the ruin disappears.
 * The possible rewards from activating an Ancient Ruin are numerous:
 *  * A crudely drawn map of the surroundings of the ruins: Reveals most of the tiles nearby. Note that the center of the
 *    revealed space is not the ruins tile, but somewhere 3-4 tiles away in a random direction.
 *  * Evidence of Barbarian activity: Reveals location of one or more nearby Barbarian encampments.
 *  * Secrets of the ancients: Gives you a free Ancient Era technology. Note that you can only receive a technology
 *    that is available to research at the time you visit the ruins; in other words, all prerequisites for the technology
 *    must be met already. You can't, for example, get Writing if you haven't researched Pottery yet.
 *  * Survivors, which join the civilization: Adds 1 Population to any one of your cities.
 *  * Advanced weaponry: Gives a free upgrade to the unit used to activate the ruins.
 *  * Warrior upgrades to Spearman, and then up the chain.
 *  * Scout upgrades to Archer, and then up the chain.
 *  * Pathfinder upgrades to Composite Bowman, and then up the chain.
 *  * Gold treasure: You receive 50-100 Gold.
 *  * Cultural artifacts: You receive 20 Culture.
 *  * Religious artifacts: You receive 30-40 Faith.
 *  * An ancient prophecy: You receive 80 Faith. Note that the civilization must have founded a Pantheon before this
 *   reward becomes possible and that it stops occurring after you've founded a religion.
 * On the easier difficulties, two additional awards are possible:
 *  * A Settler to settle another city.
 *  *  A Worker to build improvements to terrain.
 * If the game pace is set on Marathon, the amount of Culture and Faith found is greatly increased, but not the Gold.
 *
 * The choice of reward is always random, but it seems that sometimes some ruins are bound to a particular benefit - for example,
 * unit upgrade - and will always bestow it upon activation. Also, some benefits can only be activated after receiving another
 * Ancient Ruin benefit - the Prophecy benefit, for example, may appear only after getting the Religious artifacts from another ruin.
 *
 * Finally, it seems that all Ancient Ruins have the full range of possible rewards; however, you cannot get a reward that you have
 * already obtained from the last two ruins you found. This is best seen when using the Pathfinder's ability, which gives
 * direct access to each particular ruin's list of rewards.
 *
 * Thanks to the new archaeological system in Brave New World, Ancient Ruins will later spawn Antiquity Sites in the locations
 * where ruins were encountered and activated, so Archaeologists may return there later to dig and uncover more valuable secrets.
 *
 * Strategy
 * --------
 * An Ancient Ruin in-game
 *
 * The discovery of Ancient Ruins is vital for the early development of a civilization. The bonuses might seem small,
 * but for the very beginning of the game they are very significant. A single additional citizen may considerably speed up
 * the development of a city in the first 10-30 turns. The Culture bonus may instantly give a Social Policy
 * (or speed up acquisition of the next one).
 * The Faith bonus may allow for starting a Pantheon even without constructing any Faith Faith-generating buildings.
 * Also, upgrading Scouts gives Archers, even at a time the civilization lacks the technology to build any of these yet,
 * while upgrading Warriors yields the more powerful Spearmen, allowing them to fight Barbarians more effectively.
 * And of course, a free technology is always welcome (although it is possible for a technology you've almost finished
 * researching to be completed instantly, wasting some potential).
 *
 * In Brave New World, the Shoshone civilization's unique unit, the Pathfinder, may choose which of the rewards to activate
 * upon entering the Ancient Ruins. Needless to say, this allows choosing the path of development, although as with
 * normal ruins you cannot choose one that you got in your last two ruins.
 *
 * All in all, the discovery and activation of Ancient Ruins will give a civilization a crucial advantage in the early game.
 * It should become an integral part of early game exploration, and it's highly recommended, regardless of which civilization
 * you play as, to consider building a Scout first upon settling the capital, and use it along with the initial Warrior
 * unit to explore as far as possible, before having to turn back to defend from Barbarians.
 *
 * Civilopedia entry
 * -----------------
 * Ancient Ruins are the remains of earlier long-dead civilizations. They are filled with valuable secrets - gold, maps,
 * lost technologies and sometimes even survivors - awaiting discovery by the intrepid explorer.
 * Rich rewards await those who first enter an Ancient Ruin.
 */
public class AncientRuins extends AbstractImprovement {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    public AncientRuins(AbstractTile tile) {
        super(tile);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return false;
    }

    @Override
    public boolean acceptTile(AbstractTile tile) {
        return tile.isIn(Desert.class, Grassland.class, Plain.class);
    }

    @Override
    public Supply getSupply() {
        return null;
    }

    @Override
    public CombatStrength getBaseCombatStrength() {
        return null;
    }

    @Override
    public AbstractImprovementView getView() {
        return null;
    }
}
