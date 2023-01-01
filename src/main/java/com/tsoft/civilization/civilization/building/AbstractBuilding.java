package com.tsoft.civilization.civilization.building;

import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.world.HasId;
import com.tsoft.civilization.world.HasView;
import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.civilization.city.construction.CanBeBuilt;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.HasHistory;
import com.tsoft.civilization.world.World;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

/**
 * Cost - production cost
 * Mnt. - required Maintenance costs in gold
 *
 * Building	            Technology	        Cost	Mnt.	Benefit	                    Specialists		Notes
 * ----------------------------------------------------------------------------------------------------------
 * Ancient Era
 * -----------
 * Monument	            -	                40	    1	    +2 Culture	                -
 * Granary	            Pottery	            60	    1	    +2 Food	                    -	 	        +1 Food for each worked source of Wheat, Bananas and Deer
 * Barracks	            Bronze Working	    75	    1	    -	                        -	 	        +15 XP for all Units.
 * Shrine	            Pottery	            40	    1	    +1 Faith
 * Circus	            Trapping	        75	    2	    +2 local Happiness	        -	 	        Must have improved Horses or Ivory.
 * Library	            Writing	            75	    1	 	-                                           +1 Science for every 2 Citizens in this City.
 * Walls	            Masonry	            75	    0	    +4 Defense	                -
 * Water Mill	        The Wheel	        75	    2	    +2 Food, +1 Production	    -	 	        City must be next to a River.
 * Stone Works	        Calendar	        75	    1	    +1 Production, +1 Happiness	-	 	        +1 Production for each worked Marble and Stone. Must have at least one of these resources improved with a Quarry. Must not be in Plains.
 * Stele*	            -	                40	    1	    +2 Culture, +2 Faith	    -	 	        Ethiopian UB*, replaces Monument.
 * Pyramid*	            Potter	            40	    1	    +2 Faith, +2 Science	    -	 	        Mayan UB*, replaces Shrine.
 * Paper Maker*	        Writing	            75	    1	    +2 Gold	                    -	 	        Chinese UB*, replaces Library; +1 Science for every 2 Citizens in this City.
 * Walls of Babylon*	Masonry	            65	    0	    +6 Defense	                -	 	        Babylonian UB*, replaces Walls.
 * Krepost*	            Bronze Working	    75	    1	    -	                        -	 	        Russian UB*, replaces Barracks; +15 XP for all Units. -25% Culture and Gold costs of acquiring new tiles in this city.
 * Floating Gardens*	The Wheel	        75	    1	    +2 Food, +1 Production	    -	 	        Aztec UB*, replaces Water Mill. +15% Food, +2 Food each Lake tile. City must be next to fresh water.
 *
 * Classical Era
 * -------------
 * Amphitheater	        Drama & Poetry	    100	    2	    +3 Culture	                1 Artist	 	Requires Monument
 * Aqueduct	            Engineering	        100	    1	    -	                        -	 	        40% of Food is carried over after a new Citizen is born.
 * Colosseum	        Construction	    100	    1	    +2 local Happiness          -	 	        Cannot provide more Happiness than Citizens
 * Courthouse	        Mathematics	        100	    4	    -	                        -	 	        Eliminates extra Unhappiness from Occupation.
 * Lighthouse	        Optics	            75	    1	    -	                        -	 	        +1 Food from Ocean tiles, +1 Food from worked Fish. City must be on the coast.
 * Market	            Currency	        120	    0	    +2 Gold	                    1 Merchant	 	+25% Gold.
 * Bazaar*	            Currency	        120	    0	    +2 Gold	                    1 Merchant	 	Arab UB, replaces Market*; +25% Gold, Provides 1 extra copy of each improved luxury resource near this city. +2 Gold for each Oil and Oasis.
 * Mint	                Currency	        120	    0	    -	                        -	 	        +2 Gold for reach source of Gold or Silver. Requires one of these resources mined nearby.
 * Stable	            Horseback Riding	100	    1	    -	                        -	 	        +15% Production toward Mounted units. +1 Production for each worked Horses, Sheep and Cattle. Must have at least one of these resources improved with a Pasture.
 * Temple	            Philosophy	        100	    2	    +2 Faith	                1 Artist	 	Requires Shrine
 * Burial Tomb*	        Philosophy	        100	    0	    +2 local Happiness, +2 Culture	1 Artist 	Egyptian UB*, replaces Temple; Should this city be captured, the amount of Gold plundered by the enemy is doubled.
 * Mud Pyramid Mosque*	Philosophy	        100	    0	    +4 Culture	                1 Artist	 	Songhai UB*, replaces Temple; Requires no Gold maintenance
 *
 * Medieval Era
 * ------------
 * Armory	            Machinery	        160	    1	    -	                        -	 	        +15 XP for all Units. Requires Barracks or Krepost.
 * Castle	            Chivalry	        160	    0	    +4 Defense	                -	 	        Requires Walls.
 * Forge	            Metal Casting	    120	    1	    -	                        -	 	        +15% Production towards Land Units. +1 Production for each worked Iron. Requires Iron.
 * Garden	            Theology	        120	    1	    -	                        -	 	        +25% Great People generation in this city. Must be next to River or Lake.
 * Harbor	            Compass	            120	    3	    -	                        -	 	        Forms a Trade Route with Capital. +1 Production from sea Resources. Must be on the coast.
 * Longhouse*	        Metal Casting	    100	    2	    +2 Production	            1 Engineer	 	Iroquois UB*, replaces Workshop. +1 Production for Forest.
 * Mughal Fort*	        Chivalry	        150	    0	    +2 Culture, +6 Defense	    -	 	        Indian UB*, replaces Castle. Requires Walls.
 * University	        Education	        160	    2	    +33% Science	            2 Scientist	 	+2 Science from worked Jungle tiles.
 * Wat*	                Education	        160	    2	    +3 Culture, +33% Science	2 Scientist	 	Siamese UB*, replaces University.
 * Workshop	            Metal Casting	    120	    2	    +2 Production	            1 Engineer	 	+10% Production
 * Arsenal	            Rifling	            300	    0	    +6 Defense	                -	 	        Requires Castle or Mughal Fort
 * Bank	                Banking	            200	    0	    +25% Gold	                1 Merchant	 	Requires Market.
 * Hanse*	            Banking	            200	    0	    +25% Gold	                1 Merchant	 	German UB*, replaces Bank. +5% Production for each City-State Trade Route. Requires Market.
 * Constabulary	        Banking	            160	    1	    -	                        -	 	        Reduces enemy spy stealing rate by 25%.
 * Observatory	        Astronomy	        200	    0	    +50% Science	            -	        	City must be next to a Mountain.
 * Opera House	        Acoustics	        200	    2	    +4 Culture	                1 Artist	 	Requires Temple or Mud Pyramid Mosque.
 *
 * Renaissance Era
 * ---------------
 * Theatre	            Printing Press	    200	    2	    +3 Happiness	            -	 	        Can't provide more Happiness than Population. Requires Colosseum.
 * Ceilidh Hall*	    Acoustics	        200	    2	    +3 local Happ, +4 Culture	-	 	Celtic UB*, replaces Opera House. Requires Amphitheater.
 * Coffee House*	    Economics	        250	    2	    +2 Production, +5% Prod-on	1 Engineer	 	Austrian UB*, replaces Windmill. +25% Great Person generation in this city.
 * Satrap's Court*	    Banking	            200	    0	    +2 Gold, +2 local Happiness	 	            Persian UB*, replaces Bank. +25% Gold. Requires Market.
 * Seaport	            Navigation	        250	    2	    -	                        -	 	        +15% Production towards Naval units. +1 Gold and +1 Production from each worked sea Resource. Must be on the coast.
 * Windmill	            Economics	        250	    2	    +2 Production	            1 Engineer	 	+10% Production towards Buildings. City must not be on a Hill.
 * Broadcast Tower	    Radio	            500	    3	    +3 Culture	                -	 	        +33% Culture. Requires Museum.
 * Military Base	    Telegraph	        500	    0	    +12 Defense	                -	 	        Requires Arsenal.
 * Medical Lab	        Penicillin	        500	    3	    -	                        -	 	        25% of Food is carried over after a new Citizen is born.
 * Nuclear Plant	    Nuclear Fission	    360	    3	    +5 Production	            -	 	        +15 % Production. Consumes 1 Uranium. City must most contain a Solar Plant.
 *
 * Industrial Era
 * --------------
 * Factory	            Steam Power	        360	    3	    +4 Production	            2 Engineer	 	+10% Production. Requires Workshop or Longhouse, consumes 1 Coal.
 * Hospital	            Biology	            360	    2	    +5 Food	                    -
 * Hydro Plant	        Plastics	        500	    3	    -	                        -	 	        +1 Production for each River tile; must be next to a River. Consumes 1 Aluminum.
 * Military Academy	    Military Science	300	    1	    -	                        -	 	        +15 XP for all Units. Requires Armory.
 * Museum	            Archaeology	        300	    3	    +6 Culture	                2 Artist	 	Requires Opera House.
 * Police Station	    Electricity	        300	    1	    -	                        -	 	        Reduces enemy spy stealing rate by 25%. Requires Constabulary.
 * Public School	    Scientific Theory	300	    3	    +3 Science	                1 Scientist	 	+1 Science for every 2 Citizens. Requires University.
 * Stock Exchange	    Electricity	        500	    0	    +33% Gold	                2 Merchant	 	Requires Bank.
 *
 * Modern Era
 * ----------
 * Research Lab	        Plastics	        500	    3	    +4 Science	                1 Scientist	 	+50% Science. Requires Public School.
 * Stadium	            Mass Media	        500	    2	    +4 Happiness	            -	 	        Can't provide more Happiness than Citizens. Requires Theatre.
 *
 * Atomic Era
 * ----------
 * Recycling Center	    Ecology	            500	    3	    -	                        -	 	        Provides 2 Aluminum. Maximum of 5 of these buildings in your empire.
 * Solar Plant	        Ecology	            360	    3	    +5 Production	            -	 	        +15% Production, must be next to Desert and not contain a Nuclear Plant.
 *
 * Information Era
 * ---------------
 * Bomb Shelther	    Telecommunications	300	    1	    -	                        -	 	        Reduces population loss from nuclear attack by 75%.
 * Spaceship Factory	Robotics	        360	    3	    -	                        -	 	        +50% Production towards Spaceship Parts. Requires Factory. Consumes 1 Aluminum.
 *
 * Religious
 * ---------
 * Cathedral	(Cathedral Belief)	        200	    -	    +1 Happ, +3 Cult, +1 Faith	1 Artist	 	Can only be built in cities following a religion with the Cathedrals belief. Construct this building by purchasing it with Faith.
 * Monastery	(Monastery Belief)	        150	    -	    +2 Culture, +2 Faith	    -	 	        Each source of Incense and Wine worked by this City produce +1 Faith and +1 Culture.
 * Mosque	(Mosque Belief)	                200	    -	    +1 Happ, +2 Cult, +3 Faith	-	 	        Can only be built in cities following a religion with the Mosques belief. Construct this building by purchasing it with Faith.
 * Pagoda	(Pagoda Belief)	                200	    -	    +2 Happ, +2 Cult, +2 Faith	-	 	        Can only be built in cities following a religion with the Pagodas belief. Construct this building by purchasing it with Faith.
 */
@Slf4j
@EqualsAndHashCode(of = "id")
public abstract class AbstractBuilding implements HasId, HasView, CanBeBuilt, HasSupply, HasHistory {
    private final String id = UUID.randomUUID().toString();

    private final City city;
    private boolean isDestroyed;

    public abstract String getClassUuid();
    public abstract BuildingBaseState getBaseState();
    public abstract AbstractBuildingView getView();
    public abstract boolean checkEraAndTechnology(Civilization civilization);
    public abstract boolean requiresEraAndTechnology(Civilization civilization);

    protected AbstractBuilding(City city) {
        this.city = city;
    }

    @Override
    public String getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    public AbstractTerrain getTile(Point location) {
        return city.getCivilization().getTilesMap().getTile(location);
    }

    public World getWorld() {
        return city.getCivilization().getWorld();
    }

    public Civilization getCivilization() {
        return city.getCivilization();
    }

    public BuildingCategory getBuildingCategory() {
        return getBaseState().getCategory();
    }

    @Override
    public int getBaseProductionCost(Civilization civilization) {
        int cost = getBaseState().getProductionCost();
        double modifier = BuildingBaseModifiers.getModifier(civilization);
        return (int)Math.round(cost * modifier);
    }

    public int getGoldCost(Civilization civilization) {
        int cost = getBaseState().getGoldCost();
        double modifier = BuildingBaseModifiers.getModifier(civilization);
        return (int)Math.round(cost * modifier);
    }

    public int getDefenseStrength(Civilization civilization) {
        int value = getBaseState().getDefenseStrength();
        double modifier = BuildingBaseModifiers.getModifier(civilization);
        return (int)Math.round(value * modifier);
    }

    public int getLocalHappiness(Civilization civilization) {
        int value = getBaseState().getLocalHappiness();
        double modifier = BuildingBaseModifiers.getModifier(civilization);
        return (int)Math.round(value * modifier);
    }

    public int getGlobalHappiness(Civilization civilization) {
        int value = getBaseState().getGlobalHappiness();
        double modifier = BuildingBaseModifiers.getModifier(civilization);
        return (int)Math.round(value * modifier);
    }

    @Override
    public Supply getBaseSupply(Civilization civilization) {
        return getBaseIncomeSupply(civilization).add(getBaseOutcomeSupply(civilization));
    }

    public Supply getBaseIncomeSupply(Civilization civilization) {
        Supply supply = getBaseState().getIncomeSupply();
        return applyBaseModifiers(civilization, supply);
    }

    public Supply getBaseOutcomeSupply(Civilization civilization) {
        Supply supply = getBaseState().getOutcomeSupply();
        return applyBaseModifiers(civilization, supply);
    }

    private Supply applyBaseModifiers(Civilization civilization, Supply supply) {
        if (supply == null) {
            return Supply.EMPTY;
        }

        double modifier = BuildingBaseModifiers.getModifier(civilization);
        return supply.applyModifier(modifier);
    }

    @Override
    public Supply calcIncomeSupply(Civilization civilization) {
        Supply supply = getBaseIncomeSupply(civilization);
        List<AbstractBuildingSkill> supplySkills = getBaseState().getSupplySkills();
        return applySkills(supply, supplySkills);
    }

    @Override
    public Supply calcOutcomeSupply(Civilization civilization) {
        return getBaseOutcomeSupply(civilization);
    }

    public Supply applySkills(Supply supply, List<AbstractBuildingSkill> skills) {
        if (skills != null) {
            for (AbstractBuildingSkill skill : skills) {
                Supply skillSupply = skill.calcSupply(this);
                supply = supply.add(skillSupply);
            }
        }
        return supply;
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }
}
