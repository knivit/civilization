package com.tsoft.civilization.building;

import com.tsoft.civilization.L10n.building.L10nBuilding;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.event.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

/**
 * Mnt. - required Maintenance costs in gold
 *
 * Building	            Technology	        Cost	Mnt.	Benefit	                    Specialists		Notes
 * Monument	            -	                40	    1	    +2 Culture	                -
 * Granary	            Pottery	            60	    1	    +2 Food	                    -	 	        +1 Food for each worked source of Wheat, Bananas and Deer
 * Barracks	            Bronze Working	    75	    1	    -	                        -	 	        +15 XP for all Units.
 * Shrine	            Pottery	            40	    1	    +1 Faith
 * Circus	            Trapping	        75	    2	    +2 Happiness	            -	 	        Must have improved Horses or Ivory.
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
 * Burial Tomb*	        Philosophy	        100	    0	    +2 Happiness, +2 Culture	1 Artist	 	Egyptian UB*, replaces Temple; Should this city be captured, the amount of Gold plundered by the enemy is doubled.
 * Colosseum	        Construction	    100	    1	    +2 Happiness	            -	 	        Cannot provide more Happiness than Citizens
 * Courthouse	        Mathematics	        100	    4	    -	                        -	 	        Eliminates extra Unhappiness from Occupation.
 * Lighthouse	        Optics	            75	    1	    -	                        -	 	        +1 Food from Ocean tiles, +1 Food from worked Fish. City must be on the coast.
 * Mud Pyramid Mosque*	Philosophy	        100	    0	    +4 Culture	                1 Artist	 	Songhai UB*, replaces Temple; Requires no Gold maintenance
 * Stable	            Horseback Riding	100	    1	    -	                        -	 	        +15% Production toward Mounted units. +1 Production for each worked Horses, Sheep and Cattle. Must have at least one of these resources improved with a Pasture.
 * Amphitheater	        Drama & Poetry	    100	    2	    +3 Culture	                1 Artist	 	Requires Monument
 * Temple	            Philosophy	        100	    2	    +2 Faith	                1 Artist	 	Requires Shrine
 * Aqueduct	            Engineering	        100	    1	    -	                        -	 	        40% of Food is carried over after a new Citizen is born.
 * Armory	            Machinery	        160	    1	    -	                        -	 	        +15 XP for all Units. Requires Barracks or Krepost.
 * Bazaar*	            Currency	        120	    0	    +2 Gold	                    1 Merchant	 	Arab UB, replaces Market*; +25% Gold, Provides 1 extra copy of each improved luxury resource near this city. +2 Gold for each Oil and Oasis.
 * Castle	            Chivalry	        160	    0	    +4 Defense	                -	 	        Requires Walls.
 * Forge	            Metal Casting	    120	    1	    -	                        -	 	        +15% Production towards Land Units. +1 Production for each worked Iron. Requires Iron.
 * Garden	            Theology	        120	    1	    -	                        -	 	        +25% Great People generation in this city. Must be next to River or Lake.
 * Harbor	            Compass	            120	    3	    -	                        -	 	        Forms a Trade Route with Capital. +1 Production from sea Resources. Must be on the coast.
 * Longhouse*	        Metal Casting	    100	    2	    +2 Production	            1 Engineer	 	Iroquois UB*, replaces Workshop. +1 Production for Forest.
 * Market	            Currency	        120	    0	    +2 Gold	                    1 Merchant	 	+25% Gold.
 * Mint	                Currency	        120	    0	    -	                        -	 	        +2 Gold for reach source of Gold or Silver. Requires one of these resources mined nearby.
 * Mughal Fort*	        Chivalry	        150	    0	    +2 Culture, +6 Defense	    -	 	        Indian UB*, replaces Castle. Requires Walls.
 * University	        Education	        160	    2	    +33% Science	            2 Scientist	 	+2 Science from worked Jungle tiles.
 * Wat*	                Education	        160	    2	    +3 Culture, +33% Science	2 Scientist	 	Siamese UB*, replaces University.
 * Workshop	            Metal Casting	    120	    2	    +2 Production	            1 Engineer	 	+10% Production
 * Arsenal	            Rifling	            300	    0	    +6 Defense	                -	 	        Requires Castle or Mughal Fort
 * Bank	                Banking	            200	    0	    +25% Gold	                1 Merchant	 	Requires Market.
 * Hanse*	            Banking	            200	    0	    +25% Gold	                1 Merchant	 	German UB*, replaces Bank. +5% Production for each City-State Trade Route. Requires Market.
 * Constabulary	        Banking	            160	    1	    -	                        -	 	        Reduces enemy spy stealing rate by 25%.
 * Military Academy	    Military Science	300	    1	    -	                        -	 	        +15 XP for all Units. Requires Armory.
 * Museum	            Archaeology	        300	    3	    +6 Culture	                2 Artist	 	Requires Opera House.
 * Observatory	        Astronomy	        200	    0	    +50% Science	            -	        	City must be next to a Mountain.
 * Opera House	        Acoustics	        200	    2	    +4 Culture	                1 Artist	 	Requires Temple or Mud Pyramid Mosque.
 * Public School	    Scientific Theory	300	    3	    +3 Science	                1 Scientist	 	+1 Science for every 2 Citizens. Requires University.
 * Ceilidh Hall*	    Acoustics	        200	    2	    +3 Happiness, +4 Culture	-	 	Celtic UB*, replaces Opera House. Requires Amphitheater.
 * Coffee House*	    Economics	        250	    2	    +2 Production, +5% Prod-on	1 Engineer	 	Austrian UB*, replaces Windmill. +25% Great Person generation in this city.
 * Satrap's Court*	    Banking	            200	    0	    +2 Gold, +2 Happiness	 	 	            Persian UB*, replaces Bank. +25% Gold. Requires Market.
 * Seaport	            Navigation	        250	    2	    -	                        -	 	        +15% Production towards Naval units. +1 Gold and +1 Production from each worked sea Resource. Must be on the coast.
 * Theatre	            Printing Press	    200	    2	    +3 Happiness	            -	 	        Can't provide more Happiness than Population. Requires Colosseum.
 * Windmill	            Economics	        250	    2	    +2 Production	            1 Engineer	 	+10% Production towards Buildings. City must not be on a Hill.
 * Broadcast Tower	    Radio	            500	    3	    +3 Culture	                -	 	        +33% Culture. Requires Museum.
 * Factory	            Steam Power	        360	    3	    +4 Production	            2 Engineer	 	+10% Production. Requires Workshop or Longhouse, consumes 1 Coal.
 * Hospital	            Biology	            360	    2	    +5 Food	                    -
 * Military Base	    Telegraph	        500	    0	    +12 Defense	                -	 	        Requires Arsenal.
 * Police Station	    Electricity	        300	    1	    -	                        -	 	        Reduces enemy spy stealing rate by 25%. Requires Constabulary.
 * Stock Exchange	    Electricity	        500	    0	    +33% Gold	                2 Merchant	 	Requires Bank.
 * Hydro Plant	        Plastics	        500	    3	    -	                        -	 	        +1 Production for each River tile; must be next to a River. Consumes 1 Aluminum.
 * Medical Lab	        Penicillin	        500	    3	    -	                        -	 	        25% of Food is carried over after a new Citizen is born.
 * Nuclear Plant	    Nuclear Fission	    360	    3	    +5 Production	            -	 	        +15 % Production. Consumes 1 Uranium. City must most contain a Solar Plant.
 * Research Lab	        Plastics	        500	    3	    +4 Science	                1 Scientist	 	+50% Science. Requires Public School.
 * Recycling Center	    Ecology	            500	    3	    -	                        -	 	        Provides 2 Aluminum. Maximum of 5 of these buildings in your empire.
 * Solar Plant	        Ecology	            360	    3	    +5 Production	            -	 	        +15% Production, must be next to Desert and not contain a Nuclear Plant.
 * Spaceship Factory	Robotics	        360	    3	    -	                        -	 	        +50% Production towards Spaceship Parts. Requires Factory. Consumes 1 Aluminum.
 * Stadium	            Mass Media	        500	    2	    +4 Happiness	            -	 	        Can't provide more Happiness than Citizens. Requires Theatre.
 * Bomb Shelther	    Telecommunications	300	    1	    -	                        -	 	        Reduces population loss from nuclear attack by 75%.
 * Cathedral	(Cathedral Belief)	        200	    -	    +1 Happ, +3 Cult, +1 Faith	1 Artist	 	Can only be built in cities following a religion with the Cathedrals belief. Construct this building by purchasing it with Faith.
 * Monastery	(Monastery Belief)	        150	    -	    +2 Culture, +2 Faith	    -	 	        Each source of Incense and Wine worked by this City produce +1 Faith and +1 Culture.
 * Mosque	(Mosque Belief)	                200	    -	    +1 Happ, +2 Cult, +3 Faith	-	 	        Can only be built in cities following a religion with the Mosques belief. Construct this building by purchasing it with Faith.
 * Pagoda	(Pagoda Belief)	                200	    -	    +2 Happ, +2 Cult, +2 Faith	-	 	        Can only be built in cities following a religion with the Pagodas belief. Construct this building by purchasing it with Faith.
 */
@Slf4j
public abstract class AbstractBuilding implements CanBeBuilt {
    private final String id = UUID.randomUUID().toString();
    private final City city;

    private boolean isDestroyed;

    public abstract BuildingType getBuildingType();
    public abstract Supply getSupply(City city);
    public abstract int getGoldCost();
    public abstract int getStrength();
    public abstract AbstractBuildingView getView();
    public abstract String getClassUuid();

    protected AbstractBuilding(City city) {
        this.city = city;
    }

    // Method of a unit from the catalog (they don't have civilization etc)
    public abstract boolean checkEraAndTechnology(Civilization civilization);

    public String getId() {
        return id;
    }

    public City getCity() {
        return city;
    }

    public AbstractTile getTile(Point location) {
        return city.getTilesMap().getTile(location);
    }

    public World getWorld() {
        return city.getWorld();
    }

    public Civilization getCivilization() {
        return city.getCivilization();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed(boolean destroyed) {
        isDestroyed = destroyed;
    }

    public void remove() {
        isDestroyed = true;

        Event event = new Event(Event.INFORMATION, this, L10nBuilding.BUILDING_DESTROYED, getView().getLocalizedName());
        getCivilization().addEvent(event);

        city.destroyBuilding(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractBuilding building = (AbstractBuilding) o;
        return id.equals(building.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
