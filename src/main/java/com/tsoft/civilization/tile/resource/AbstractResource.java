package com.tsoft.civilization.tile.resource;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Basics
 * ------
 * Resources are special commodities found in limited quantities on the map. When a resource is "worked" (or accessed),
 * it provides a bonus for your empire; this is accomplished either through constructing a specific improvement with a Worker
 * (on land) or expending a Work Boat (for sea tiles). Bonuses vary from increased yield (Production Production, Food Food, etc.)
 * to a number of units of Iron, Uranium, Gold or some other strategic or luxury resources necessary for producing specific units
 * and buildings, and for providing more Happiness to your citizens.
 *
 * Resources are extremely important in the game, and the main reason for expansion and territorial wars. Plan your early expansion
 * carefully to take control of as many resources as you can!
 *
 * Civilization V contains three kinds of resources: bonus resources, strategic resources and luxury resources.
 * This article explains their differences and lists the resources of each category.
 *
 * Of the three types of resources, bonus resources are the most common, providing only tile yield benefits.
 * Strategic and luxury resources' effects, on the other hand, are much more far-reaching, and are sufficiently precious
 * to be traded across empires (besides having tile yield benefits). For this purpose, all of them are counted in units
 * across your empire (regardless of their source location). To gain strategic access to, and add the count of a particular
 * strategic or luxury resource to your trade network, you need not only to have it in your territory, but also to construct
 * a particular improvement on it. You don't need to work its tile with a Citizen to have access to its strategic and trading benefits!
 * You do, however, need to work it to access its tile benefits.
 *
 * Finally, the utilization of strategic resources depends highly on technological development. While the use of resources
 * like Spices and Wine is apparent from the dawn of ages, this is not true for the use of Uranium Uranium, for example.
 * Because of this, Strategic resources don't even appear on the map before you research the technology necessary to make
 * use of them! These technologies are widely spread across the eras, starting in the Ancient Era with Animal Husbandry
 * (which reveals Horses), and ending in the Atomic Era with Atomic Theory (which reveals Uranium). Likewise, early strategic
 * resources such as Iron or Horses may become less useful towards the end of the game. These changes and the discovery
 * of more and more useful resources makes for a dynamic game and may force you to expand, conquer, or negotiate in locations
 * you never thought would be useful before.
 *
 * Bonus Resources
 * ---------------
 * If there is a bonus resource on hills you cannot construct a Mine there.
 *
 * These resources are the most widespread resources on the map. They aren't special in any strategic sense, and are thus
 * not shared across your empire and neither can they be traded. However, they provide substantial additional output for
 * the tile they are on when worked, especially when improved. Since their benefit lies only in their tile output bonus,
 * it only makes sense to use a bonus resource by having a city sufficiently close to it (within three tiles), which can
 * then work its tile. Also, several special buildings can be constructed in the controlling city to further increase bonus resources yields.
 *
 * Bonus resources	            Output bonus
 * Bananas	Wheat	            Food
 * Cattle	Sheep	            Food
 * Bison BNW-only.png	Deer	Food
 * Fish	                        Food
 * Stone	                    Production
 * These are the buildings which increase bonus resources' output:
 *
 * Granary: +1 Food per Wheat, Bananas and Deer resource
 * Lighthouse: +1 Food per Fish resource (stacks with the general +1 Food to all ocean tiles nearby; additional +1 Production to all Sea Resources in Brave New World)
 * Stone Works: +1 Production per Marble and Stone resource
 * Stable: +1 Production per Horses, Sheep and Cattle resource
 *
 * Strategic Resources
 * -------------------
 * Strategic resources are those that are of economic and military importance for your empire. Certain military units and
 * buildings require a strategic resource before they can be built. For example, a Horseman requires 1 Horses resource,
 * while a Factory requires 1 Coal resource.
 *
 * If these objects are removed from a civilization's control - whether by losing a unit in battle, gifting it to a
 * City-State, or by any other means - the strategic resource consumed by the object is released for future use.
 * Strategic resources never actually leave play.
 *
 * If a civilization loses access to some of the strategic resources they are currently using, and find themselves using
 * more than they're producing or trading, then military units depending upon the resource in question will fight with
 * a large combat penalty until the situation is remedied.
 *
 * Each improved strategic resource tile provides a certain quantity of that resource to the empire that controls it,
 * regardless of whether it is worked by a city or not. This quantity varies by resource, tile-to-tile, and
 * according to resource abundance in Game Options.
 * Note that they also provide output bonuses, which are useful only if the tile IS worked. Unused strategic resources
 * may be traded to other empires.
 *
 * Strategic resources	    Output bonus
 * Horses	Iron	        Production
 * Coal	Oil	                Production
 * Aluminum	Uranium     	Production
 * The Stable building provides +1 additional Production from each Horses resource.
 * The Forge building provides +1 additional Production from each Iron resource.
 *
 * Luxury Resources
 * ----------------
 * Luxury resources are fancy goods that everyone values highly, but your wealthier citizens especially relish (and often clamor for).
 * They are the most diverse of all resources, and are used in all sorts of human activities - cooking, weaving,
 * and in many other crafts. Their practical effect is to provide additional Happiness: +4 for each luxury
 * resource which your empire has secured at least one source of. Unlike strategic resources, each improved luxury resource
 * tile provides only one unit of the resource. If an empire has secured more than one unit of the same luxury resource,
 * the Happiness effect is the same as possessing only one, so you should consider using the surplus to trade with other empires.
 *
 * Again, you don't need to work an improved luxury resource tile in order to gain the resource, but you do need to work it (i.e. send a 20xPopulation5.png Citizen to the tile) to gain its output bonuses. Unlike other resources, Luxury resources add Gold Gold yield to tiles, which makes them even more important strategically, allowing you to gain Gold Gold directly from terrain (and not having to trade for it). Note that luxury resources may be traded to other empires, if they're produced by you; however, in the event that you've gained them via trading or from a CityStateIcon5.png City-State ally, you can't trade them away.
 *
 * Luxury resources	Output bonus
 * Cotton	Spices	Sugar	GoldGold
 * Furs	Ivory	Silk	GoldGold
 * Dyes	Incense	Wine	GoldGold
 * Copper GodsKings5 clear.png	Gold Gold	Silver Silver	GoldGold
 * Marble Marble†	Pearls Pearls	Truffles Truffles GodsKings5 clear.png	GoldGold
 * Jewelry Jewelry* GodsKings5 clear.png		Porcelain Porcelain* GodsKings5 clear.png	GoldGold
 * Nutmeg Nutmeg** BNW-only.png	Cloves Cloves** BNW-only.png	Pepper Pepper** BNW-only.png	GoldGold
 * Crab Crab GodsKings5 clear.png	Salt Salt GodsKings5 clear.png	Whales Whales	GoldFood
 * Citrus Citrus GodsKings5 clear.png		Cocoa Cocoa BNW-only.png	GoldFood
 * Gems Gems		GoldGoldGold
 * † Marble Marble provides +15% Wonder production for Ancient and Classical Wonders
 * * Unique to Mercantile neutral.png Mercantile City-States
 * ** Unique to Indonesia, only obtainable through the Spice Islanders unique ability
 *
 * Some buildings also increase the bonuses of luxury resources:
 *
 * Harbor (vanilla and Gods & Kings): +1 Production Production to all Sea Resources (This bonus is transferred to the Lighthouse in Brave New World.)
 * Mint: +2 Gold Gold per Gold Gold and Silver Silver resource
 * Monastery: +1 Culture Culture per Incense Incense and Wine Wine resource (additional +1 Faith Faith in Gods & Kings; can only be built via a particular Religious Belief)
 * Seaport: +1 Production Production and Gold Gold to all Sea Resources
 * Stone Works: +1 Production Production per Marble Marble and Stone Stone resource
 * GodsKings5 clear.png Introduced in Gods & Kings.
 * BNW-only.png Introduced in Brave New World.
 *
 * Strategy
 * Settling a city on top of a luxury or strategic resource tile gives the strategic benefits as soon as you have the appropriate technology for improving it. It also gives additional Production Production, Food Food and Gold Gold it usually provides right to your city tile, meaning that you'll always be using these benefits (since the city tile is always worked).
 *
 * You won't be able to use the potential benefits of Improvements built on the resource, but you WILL be able to build the bonus city buildings related to the resource and their bonus will apply. For example, with a city settled on a Stone Stone resource, the Stone Works building is available (and it is expected that the city tile's Production Production yield will increase accordingly after it is constructed). However the city will not gain the additional 1 or 2 Production Production (and possible Faith Faith from Stone Circles pantheon belief) associated with a Quarry.
 *
 * CityStateIcon5.png City-States allied to you give your empire free units of the resources they have. Note that these units are added to your pool for internal use, but are not available for trade with other civilizations!
 *
 * Interaction with Great tile improvements
 * Great People create incredible tile Improvements, with strong yields. Generally it would not be advantageous to forgo their yield and replace these Improvements with standard Resource Improvements, and as a result, these special improvements will connect any revealed and future strategic resources on them to the trading network as a courtesy. Unlike cities, Great tile improvements do not connect luxury resources!
 *
 * However, Mines and Pasture resource improvements, the two main Improvements placed on strategic resources, get two further bonuses when adding up yield (on top of the resource's bonus yield and the improvement's own yield). The first is based on the resource they improve and the second is based on later technologies. Great tile improvements do not receive these additional bonus yields, unlike resource improvements.
 *
 * As an example, consider some Coal Coal resources on hills in the Modern Era:
 *
 * Hills have a base terrain yield of 2 Production and Coal provides 1 Production. This gives a total of 3 Production without improvements.
 * A Mine provides 1 Production, the resource-dependent combination of mine and Coal provides an additional Production unit, and it receives another Production unit from Chemistry technology. Thus the Mine provides 3 Production Production.
 * An Academy provides Science, but it will connect the Coal to the empire. Moreover, it doesn't get any boosts. Therefore, it does not provide any production at all.
 * When the Coal Coal is improved with a Mine, the total production yield is 6 Production. When an Academy is placed, the total yield is 3 Production and some Science Science.
 * Therefore, intentionally placing an Great Tile Improvement on a strategic resource can be a waste of yields. However, there may be situations where it is advantageous to connect the resource with a Great improvement, such as obtaining Iron Iron or Horses Horses to rush units into production, or maximizing yield given a small 20xPopulation5.png Population. Furthermore, resource-dependent yields only appear on later strategic resources such as Uranium Uranium and Oil Oil that are revealed much later. Generally,
 *
 * Workers are more readily available and a few turns of construction is less consequential; and
 * Great People have already been spent by this point and the choice to avoid the resource will already have been 'made'.
 * Hence while a good exercise in understanding the process of calculating and understanding tile yields, the distinction between the types of improvements tends to be more academic in nature, and have less practical applications than it may appear to.
 */
@Slf4j
@EqualsAndHashCode(of = "id")
public abstract class AbstractResource implements HasSupply {

    @Getter
    private String id = UUID.randomUUID().toString();

    public abstract ResourceType getType();
    public abstract ResourceCategory getCategory();
    public abstract ResourceBaseState getBaseState();
    public abstract boolean acceptEraAndTechnology(Civilization civilization);
    public abstract boolean acceptTile(AbstractTerrain tile);

    public String getClassUuid() {
        return getType().name();
    }

    public String getName() {
        return getClass().getName();
    }

    @Override
    public Supply getBaseSupply(Civilization civilization) {
        Supply baseSupply = getBaseState().getSupply();
        double modifier = ResourceBaseModifiers.getModifier(civilization);
        return baseSupply.applyModifier(modifier);
    }

    @Override
    public Supply calcIncomeSupply(Civilization civilization) {
        return getBaseSupply(civilization);
    }

    @Override
    public Supply calcOutcomeSupply(Civilization civilization) {
        return Supply.EMPTY;
    }
}
