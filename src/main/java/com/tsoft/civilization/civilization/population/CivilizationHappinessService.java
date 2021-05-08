package com.tsoft.civilization.civilization.population;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.DifficultyLevel;

import java.util.Map;

import static com.tsoft.civilization.world.DifficultyLevel.*;

/**
 *
 * Happiness
 * ---------
 * It is a measure of people's feelings of contentment within an empire. It can also be interpreted as
 * the citizens' "approval" rating of the leader (you). It is counteracted by Unhappiness, people's discontent or
 * disapproval of your rule, which naturally increases as the game progresses. The two stats form a balance,
 * which changes constantly and therefore affects the functioning of a civilization.
 *
 * It is worth noting that "real" problems, like food shortages or slow cultural growth have no bearing
 * on the level of contentment of the people - more important factors are instead the overcrowding of cities,
 * or such visibly vicious acts like conquering foreign cities and razing them, which have to be countered by the state
 * with the old principle: "Keep people entertained and distract them with shiny baubles to make them forget their problems!"
 *
 * Levels of Happiness
 * -------------------
 * There are three levels of Happiness: happy, unhappy, and very unhappy.
 *
 * An empire with Happiness of zero or greater is considered happy. Happy civilizations grow as normal, and each turn's
 * Happiness value is added to the empire's Golden Age counter. We could say that such empires are well governed internally,
 * although that doesn't necessarily mean that they will be successful, or competitive.
 *
 * A civilization with Happiness below zero is unhappy. An unhappy empire causes each city's food surplus to be reduced by 75%,
 * drastically reducing population growth. In Brave New World, there are additional effects; see below for more info.
 *
 * A civilization with Unhappiness of -10 or lower is very unhappy. A very unhappy empire does not grow at all,
 * suffers a Production penalty, receives a nasty combat penalty (-33%) for all its units, and cannot train Settlers.
 * It is unknown about whether existing Settlers can found a city or not.
 *
 * When Unhappiness drops to -20, the civilization's cities go into revolt, and rebels start appearing throughout the empire,
 * based on the number of cities. The rebels are similar to Barbarians, but appear in groups. Once a group of rebels spawns,
 * another group will not appear for a while. Again, for the exact effects of Unhappiness in Brave New World, see below.
 *
 * Happiness Sources
 * -----------------
 * In order to counteract Unhappiness and continue an empire's growth, Happiness must be increased. There are many ways to do so.
 *
 * Luxury resources: For every different luxury resource which a civilization has access to (through possessing an improved luxury resource,
 * trading with another civ, or alliance with a CityStateIcon5.png city-state with access to the resource),
 * the empire receives +4 Global Happiness.
 *
 * Buildings: The Circus, Colosseum, Theatre, BNW-only.png Zoo, and Stadium are the main options, but there are also some other choices,
 * such as the Stone Works. These provide Local Happiness.
 *
 * Wonders: Many wonders provide varying amounts of (often Global) Happiness.
 *
 * Social policies: Almost all Social policy trees have some way of boosting a civilization's Happiness.
 * Common methods include granting Happiness from certain buildings and reducing the Unhappiness generated by population.
 *
 * Natural wonders: Each Natural wonder grants a permanent +1 Global Happiness bonus to all Civs which have discovered it.
 * GodsKings Mercantile city-states: Each mercantile city-state provides +3 Global Happiness
 * to each civilization with which it is at least Friends.
 *
 * Global vs Local Happiness
 * -------------------------
 * Happiness sources are divided loosely into Local and Global. The main difference is that while Global sources contribute
 * to Happiness unconditionally, Local sources can only contribute up to the Population number of the respective city.
 * This means that if a city has 5 Population and sources of local happiness adding up to 8 Happiness, the actual amount
 * that will be contributed to your empire's Happiness will be 5, not 8.
 *
 * The logic behind this distinction is sound: Sources of Local Happiness are the most common entertainment buildings
 * (Circus, Colosseum, etc.), which provide shallow, low-level amusement for the masses. On the other hand, sources of
 * Global Happiness are Luxuries, Social Policies, etc., which all provide deeper, more meaningful reasons for your empire to be happy.
 *
 * Unhappiness Causes
 * ------------------
 * Unhappiness directly subtracts from Happiness, and increases as a civilization grows. There are several sources of Unhappiness.
 *
 * Number of Cities: Each city in an empire adds Unhappiness as follows:
 * World size	        | Duel	| Tiny	| Small	| Standard | Large | Huge
 * Unhappiness per city	| +3	| +3	| +3	| +3	   | +2.4  | +1.8
 *
 * Population: A living person is an unhappy person. Each of a civilization's Citizens automatically generates +1 Unhappiness
 * N.B. This is also true for Engineer Specialists, despite their unhappiness being shown separate to that of the rest of the population.
 *
 * Puppet Cities: Each puppet city in an empire adds the same amount of Unhappiness (Civ5).png Unhappiness that a normal city does.
 *
 * Annexed Cities: Each annexed city in an empire produces more Unhappiness than a normal city, as detailed in the table below.
 * This penalty can only be removed by constructing a Courthouse in the city.[1]
 * World size	                | Duel	| Tiny	| Small	| Standard | Large | Huge
 * Unhappiness per annexed city	| +5	| +5	| +5	| +5	   | +4	   | +3

 * Razing Captured Cities: Razing a city creates the same amount of Unhappiness as annexing a city. This Unhappiness, however,
 * diminishes during the razing process, and disappears entirely once the city is destroyed.
 *
 * Population of Annexed or currently razed Cities: Each citizen in an annexed city, or in a city being razed,
 * generates 1.34 Unhappiness instead of the usual 1.
 *
 * Public Opinion: Only possible once you have adopted an Ideology. If another civilization with differing Ideology has stronger
 * cultural influence on your empire than yours, your Public Opinion starts generating Unhappiness.
 *
 * Brave New World
 * ---------------
 * Happiness has been reworked in the Brave New World expansion pack, with the introduction of the new Ideology mechanics,
 * including the Public Opinion. This is now a new potential source of Unhappiness: in case its level is not "Content",
 * it now adds directly to Unhappiness, and the exact amount of points added depends either on the number of cities,
 * or the number of population, whichever is greater.
 *
 * The first thing on Happiness in Brave New World is the effects of an Unhappy empire have been changed -
 * now each point of 20xMalcontent5.png Unhappiness below 0 gives a penalty of -2% Production and Gold output
 * (applies directly to the output of each city), as well as -2% Combat Strength for all units.
 * Effect on city growth is the same as before (as if you were adding to your Food Basket only 1/4 of the normal amount you would otherwise add).
 *
 * At the Unhappiness level of -10 ("Very Unhappy"), population growth stops completely, you can't train Settlers anymore,
 * and rebellions erupt at regular intervals in the form of "Barbarian" units appearing right near a city of yours,
 * using your most up-to-date units based on your technological progress. Production, Gold and Combat Strength continue to lower steadily.
 *
 * Finally, when your empire's Unhappiness reaches -20, given your Public Opinion is low, some of your cities may start to revolt
 * and change their allegiance to other empires following their Preferred Ideology. The effect is as if the other empire
 * suddenly acquired the city in question. Border cities are most likely to defect, and the civilization they go to is
 * the one with the Preferred Ideology, whose Capital is closest to the city.
 *
 * Clearly, this presents a grave danger for your empire, while at the same time the new gradual worsening of the situation
 * feels more natural (as opposed to simply having either an "Unhappy" or a "Very Unhappy" stage).
 *
 * Strategy
 * --------
 * Keeping your empire happy is difficult, but important. The main thing you need to remember is that your empire gets gradually unhappy,
 * as time goes - whether it is from Population growth, or due to more cities being founded or conquered. To counter this,
 * the first thing you need to do in the first stage of the game, is get access to as many luxury resources as you can get hold onto.
 * Later, you'll have to depend more on buildings and Social Policies, especially when Ideology kicks in. Keep in mind, however,
 * that the Local Happiness produced by buildings (which is the most accessible way to raise Happiness) is limited in most cases
 * to +6 per city (+8 or 9 with access to certain resources, which in turn allows construction of more Happiness-boosting buildings),
 * and you will have to procure additional ways to keep your people happy. Try to think ahead of time so as to avoid crisis periods.
 *
 * Try to keep your empire happy at all times, but don't despair if your empire's Happiness becomes negative for a few turns -
 * it's not the end of the world. Try to negotiate for some more luxuries, or build Happiness-boosting buildings,
 * and think of some long-term strategy to boost your 2Happiness (such as building up to a particular Social Policy or building a particular Wonder). Or, try to eliminate any extra Unhappiness (Civ5).png Unhappiness, for example from Occupied cities.
 *
 * Acquiring the favor of city-states is a useful strategy: not only do city-states provide all their luxury resources to their ally,
 * but Mercantile city-states also provide +3 Happiness to each civilization which is at least Mercantile Friends with them.
 * Cultured city-states can also make useful Cultured friends and Cultured allies since they aid in the acquisition of Social policies
 * (some of which are key to maintaining a happy civilization).
 *
 * In Brave New World, you will have to deal with Public Opinion where your civ will be influenced by other civs with higher Tourism
 * rates than your own. If you aren't playing a culture civ, you might find your Happiness suffering because of this.
 *
 * One solution is to try to improve your tourism rate slightly to overcome this, but you may also have to adopt some of the
 * Happiness-oriented tenets of your Ideology in order to even out these losses. If it gets absolutely unmanageable,
 * you can adopt the Ideology of the person presently leading in tourism, but this will cost you all of your Ideological Tenets
 * thus far, and so it is not advisable unless you are desperate.
 *
 * Buildings That Generate Happiness
 * ---------------------------------
 * Era	        Building	    Happiness	Note
 * Ancient	    Circus	        +2	        City must have horses or ivory nearby
 * Ancient	    Stone works	    +1	        City must have an improved marble or stone resource nearby, city cannot be on plains
 * Classical	Burial tomb	    +2	        Egyptian unique building
 * Classical	Colosseum	    +2
 * Renaissance	Satrap's court	+2	        Persian unique building
 * Renaissance	Ceilidh hall	+3	        Celtic unique building
 * Renaissance	Theatre	        +3	        Requires Colosseum (unavailable in Brave New World)
 * Renaissance	Zoo	            +2	        Requires Colosseum (Brave New World only)
 * Modern	    Stadium	        +4          (BNW-only.png +2)	Requires Theatre or Zoo
 *
 * Wonders that cause happiness
 * ----------------------------
 * Wonder	        Happiness	Requirement
 * Circus Maximus	+5 global	Must have a Colosseum in every city
 * Chichen Itza	    +4 local	Civil Service
 * Notre Dame	    +10 global	Physics
 * Forbidden Palace	-10%        Unhappiness in non-occupied cities	Banking
 * Taj Mahal	    +4 local	Architecture
 * Neuschwanstein	+2 local, +1 global from every Castle	Railroad
 * Eiffel Tower	    +5, +1 per two additional policies adopted (BNW-only.png +5 Global)	Radio
 * Prora         	+2, +1 per two additional policies adopted (both global) Flight (BNW-only.png)
 *
 * Social policies that deal with happiness
 * ----------------------------------------
 * Policy	            Tree	    Era	                     Requirements	    Effect
 * Aristocracy	        Tradition	None	                 None	            +15% Production Production when building Wonders (any Era) and +1 20xHappiness5.png Happiness for every 10 20xPopulation5.png Citizens in a City.
 * Monarchy	            Tradition	None	                 None	            +1 Gold Gold and -1 Unhappiness (Civ5).png Unhappiness for every 2 20xPopulation5.png Citizens in the Capital Capital.
 * Meritocracy	        Liberty	    None	                 Citizenship	    +1 Happiness for each City you own connected to the Capital Capital and -5% Unhappiness (Civ5).png Unhappiness from 20xPopulation5.png Citizens in non-occupied Cities.
 * Military Caste	    Honor	    None	                 Discipline	        Each City with a garrison increases local city 20xHappiness5.png Happiness by 1 and Culture Culture by 2.
 * Professional Army 	Honor	    None	                 Military Caste	    (pre-BNW-only.png) Gold Gold cost of upgrading units reduced 33% and +1 20xHappiness5.png Happiness from every defensive building (Walls, Castle, Arsenal, Military Base)
 * Cultural Diplomacy	Patronage	Classical	             Scholasticism	    Quantity of Resources gifted by City-States increased by 100%. 20xHappiness5.png Happiness from gifted Luxuries increased by 50%.
 * Protectionism	    Commerce	Medieval	             Mercantilism	    +1 Happiness from each Luxury Resource
 *                                                                              BNW-only.png +2 Happiness from each Luxury Resource
 * Naval                Tradition   Exploration	Medieval     None               BNW-only.png +1 Happiness for each Harbor, Seaport, or Lighthouse
 * Humanism         	Rationalism	Renaissance	             None	            (pre-BNW-only.png) +1 Happiness from every University, Observatory and Public School
 * Democracy        	Freedom	    Renaissance	             None	            (pre-BNW-only.png) -50% unhappiness from specialist population
 * Police State     	Autocracy	Industrial	             Militarism	        (pre-BNW-only.png) +3 Happiness from Courthouses, build Courthouses in half the time
 * Note: Adopting Order generates 1 happiness per city (not in BNW-only.png).
 *
 * There are also numerous tenets in each Ideology which boost Happiness, including Universal Healthcare which is available to all three.
 *
 * Ideological tenets that deal with happiness
 * -------------------------------------------
 * Ideological Tenet	Ideology	Level	Effects
 * Universal            Healthcare	All	1	+1 20xHappiness5.png Local Happiness from each National Wonder.
 * Capitalism	        Freedom	    1	    +1 Local Happiness per Mint, Bank and Stock Exchange.
 * Urbanization	        Freedom	    2	    +1 Local Happiness per Water Mill, Hospital and Medical Lab.
 * Universal Suffrage	Freedom	    2	    Unhappiness from Specialists is halved. Golden Ages last 50% longer.
 * Socialist Realism	Order	    1	    +2 20xHappiness5.png Local Happiness from each Monument. Build Monuments in half the usual time.
 * Young Pioneers	    Order	    1	    +1 20xHappiness5.png Local Happiness per Workshop, Factory and Solar/Nuclear/Hydro Plant.
 * Academy of Sciences	Order	    2	    +1 20xHappiness5.png Local Happiness per Observatory, Public School and Research Lab.
 * Fortified Borders	Autocracy	1	    +1 20xHappiness5.png Local Happiness per Castle, Arsenal and Military Base.
 * Militarism	        Autocracy	2	    +2 20xHappiness5.png Local Happiness per Barracks, Armory and Military Academy.
 * Police State	        Autocracy	2	    +3 20xHappiness5.png Local Happiness from each Courthouse. Build Courthouses in half the usual time.
 *
 * Religious beliefs that deal with happiness
 * ------------------------------------------
 * Religious belief	          Belief type	                         Effects
 * Goddess of Love	          Pantheon	                             +1 Happiness from cities with Population of 6+
 * Sacred Waters	          Pantheon	                             +1 Happiness from cities on rivers
 * Ceremonial Burial	      Founder 	                             +1 Happiness for every City following this religion
 *                                                                   BNW-only.png +1 Happiness for every 2 Cities following this religion
 * Peace Loving	              Founder	                             +1 Happiness for every 8 followers of this religion in non-enemy foreign cities
 * Asceticism Follower	      Shrines provide                        +1 Happiness in cities with 3 followers
 * Cathedrals Follower	      Use Faith Faith to purchase Cathedrals (+1 Faith Faith, +1 Culture Culture, +1 Happiness, +1 Great Work of Art slot)
 * Mosques	Follower	      Use Faith Faith to purchase Mosques    (+3 Faith Faith, +2 Culture Culture, +1 Happiness)
 * Pagodas	Follower	      Use Faith Faith to purchase Pagodas    (+2 Faith Faith, +2 Culture Culture, +2 Happiness)
 * Peace Gardens	Follower  Gardens provide                        +2 Happiness
 * Religious Center	Follower  Temples provide                        +2 Happiness in cities with 5 followers
*/
public class CivilizationHappinessService {

    private final Civilization civilization;

    private static final Map<DifficultyLevel, Integer> BASE_HAPPINESS = Map.of(
        SETTLER, 15,
        CHIEFTAIN, 12,
        WARLORD, 12,
        PRINCE, 9,
        KING, 9,
        EMPEROR, 9,
        IMMORTAL, 9,
        DEITY, 9
    );

    public static final Map<DifficultyLevel, Integer> EXTRA_HAPPINESS_PER_LUXURY = Map.of(
        SETTLER, 1,
        CHIEFTAIN, 1,
        WARLORD, 0,
        PRINCE, 0,
        KING, 0,
        EMPEROR, 0,
        IMMORTAL, 0,
        DEITY, 0
    );

    public CivilizationHappinessService(Civilization civilization) {
        this.civilization = civilization;
    }

    public Happiness calc() {
        DifficultyLevel difficultyLevel = civilization.getWorld().getDifficultyLevel();

        Happiness citiesHappiness = civilization.getCityService().calcHappiness();

        int wonders = 0;
        int naturalWonders = 0;
        int socialPolicies = 0;

        return Happiness.builder()
            .baseHappiness(BASE_HAPPINESS.get(difficultyLevel))
            .luxuryResources(citiesHappiness.getLuxuryResources())
            .buildings(citiesHappiness.getBuildings())
            .wonders(wonders)
            .naturalWonders(naturalWonders)
            .socialPolicies(socialPolicies)
            .garrisonedUnits(citiesHappiness.getGarrisonedUnits())
            .build();
    }
}