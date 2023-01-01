package com.tsoft.civilization.tile.terrain;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.tile.resource.ResourceType;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.ocean.Ocean;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;
import com.tsoft.civilization.tile.feature.FeatureList;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.feature.*;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.util.Point;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

/**
 * Terrain (Civ5)
 * EDIT
 *
 * SHARE
 * BackArrowGreen.png Back to Civilization V
 * BackArrowGreen.png Back to Game concepts
 * Blue arrow right.png Go to the list of improvements
 *
 *
 * Terrain is a term that describes the land in Civilization V and its features. The land is broken up into hexagonal tiles, which represents distinct areas of the terrain. Units appear on the terrain and are affected by it as they move about and fight on it, and 20xPopulation5.png Citizens in cities can work the tiles to produce tile yields. Terrain can be claimed by empires as part of their Borders.
 *
 * Base Terrain
 * ------------
 * Base terrain is the type of ground a certain tile contains, and determines its base properties. Other terrain features,
 * like a forest or a resource, might exist on top of a tile's base terrain. These features can modify a tile's properties,
 * but its base terrain can never be changed. Sometimes these additional features can be removed, at which point the tile will
 * revert to its base properties.
 *
 * There are several types of base terrains - plains, grassland, desert, tundra and snow. Check the table below for more info
 * on the properties of each terrain type.
 *
 * Terrain Categories
 * ------------------
 * Base terrain, along with the presence or absence of certain terrain features, are used to categorize tiles into several
 * different types. Тhese categories are often used in game info and tooltips:
 * * Open Terrain: This represents flat, level terrain and includes all terrain without a hill, forest, or jungle.
 *       As such, marshes are considered open terrain.
 * * Rough Terrain: This represents land with objects obstructing free movement; it includes all terrain that does include a hill,
 *       forest, or jungle. Note that marshes are not rough terrain.
 * * Lake: A body of water inside a landmass. It consists of one to ten water tiles that are enclosed by land tiles on all sides.
 *       Note that if more water tiles are enclosed (including some ocean tiles), they are considered an "inner sea" and not a lake!
 * * Coast: The shallow waters typically located along the shoreline of a landmass. All naval units can traverse coast tiles.
 *       Also, all ocean resources are found on these tiles.
 * * Ocean: Deeper waters typically far removed from any landmass. Only middle and late game naval units can pass through them.
 * * Fresh Water: This represents all land tiles that border a river, lake, or oasis. Important for some game features.
 *
 * Terrain Features
 * ----------------
 * Certain special features often appear on base terrain, modifying its properties, or altering its use in the game.
 * Some of them may be removed, while others are permanent, essentially forming more types of base terrain.
 *
 * Vegetation
 * ----------
 * Many areas of the land are covered in dense vegetation. They are known as forests, or jungles if located in warmer climates.
 * Both forests and jungles usually cover vast expanses of land (more than 5 tiles, sometimes as many as 20-30 adjacent tiles).
 * Large forest expanses are quite often found on tundra terrain, close to the snow borders of the far north and south;
 * while jungles are found in more central areas (tropics, the Equator). Forests are considered more "tame" areas,
 * while jungles teem with wildlife and have little use for civilization. Both features present obstacles to unit movement.
 *
 * Marshes are another special feature of the land - these are lowlands where underground water gathers to form large pools
 * of stale, shallow lakes. Birds and low plants grow there, but generally the terrain becomes almost useless because of the
 * unstable ground. Marshes may be found in many climates and also present obstacles to unit movement.
 *
 * All three types of vegetation may be removed by Workers to reveal the base terrain underneath and free the land for other uses.
 *
 * River
 * -----
 * Rivers are of great importance for civilization historically, and they have special functions in the game as well.
 * Rivers flow between tiles (which means that there is no special river tile), but they provide all tiles that border them
 * with some special features. For example, they determine the formation of flood plains in a desert area (all desert tiles
 * adjacent to a river become flood plains), provide access to fresh water to all adjacent tiles, and allow the construction
 * of a number of buildings in cities adjacent to them. In vanilla Civilization V and Gods & Kings, rivers also provide 1 Gold potential
 * to all adjacent tiles. In Brave New World, they don't have this bonus anymore, but they provide additional Gold bonus for
 * Land Trade Routes opened to cities built next to them.
 *
 * Rivers present great obstacles to unit movement - each land unit passing a river will lose all its remaining MPs for its turn,
 * unless there is a usable bridge over it or the unit has the Amphibious promotion.
 *
 * Hill
 * ----
 * Relief features which rise high above the general level of surrounding land are known as hills. These are found on any
 * base terrain type (including snow) and are quite useful for mining operations. Units that are standing on hills have better
 * vision while they occupy them. Hills can either be solitary (occupying one tile), or form a highland of several adjacent hills.
 * Hills also present obstacles to unit movement.
 *
 * Mountain
 * --------
 * Tall relief features, mountains rise high enough for their peaks to be covered permanently with snow and become an impassable barrier.
 * Most often they occupy only single tiles, but sometimes they extend through 3-4 nearby tiles, forming mountain ranges.
 * These can be great obstacles to movement, because all mountain tiles are completely impassable (save the late game air units,
 * and the Carthaginian civilization, provided that they've generated at least one Great General), and force land units to circle around them.
 *
 * Mountains have almost no use for civilization because of their harsh climate and difficult access. However, cities built right next to Mountains may construct some special buildings and World Wonders. Also, the Incan civilization gets additional Food Food from their Terrace Farms if built next to mountains.
 *
 * Ice
 * In the far north and south of the world, the so-called "polar regions," temperatures are so low that water is permanently frozen. These ice tiles are completely impassable, save Submarines and Nuclear Submarines.
 *
 * Resources
 * Finally, many tiles may have resources on them - special commodities with great importance for civilization. Search these tiles out and try to include them in your empire's territory as soon as possible to gain a considerable advantage over the others.
 *
 * Relations between and Game Implications of Base Terrain and Features
 * Base terrains and features in the game combine according to logical real-world relations. Knowing them might allow a player to anticipate the presence/absence of certain features even before discovering them with his scouts. Here are some common relations:
 *
 * Plains and grassland base terrain are the most common types in the central regions of the world. However, they may also be found intruding into harsher climates to the north/south.
 * Deserts are also found in most regions of the world, but most often in the central, level parts. Some other features combine with deserts:
 * Oases are only found on desert tiles, usually far from any other features. They allow access to fresh water to all surrounding tiles.
 * Rivers may pass through deserts, and when they do, they form the unique terrain type flood plains. This terrain may be thus found only in desert areas.
 * Forests and jungles rarely appear in a single tile; they typically form larger expanses of foliage. In the north/south regions of the world, forests often cover vast expanses of tundra tiles, while jungles are more common in the central regions. Often, there are marshes among the jungles, making it even more difficult to move through these parts. So, when you encounter what looks like a solid three-to-four-tile forest, know that this is probably going to be a major obstacle for movement.
 * Rivers, lakes, and oases provide fresh water to all nearby tiles. Settling cities next to them is highly recommended for the additional buildings you can build, as well as some bonuses for tile improvements, such as Farm, which will yield lots of Food Food early in the game.
 * Rivers always end in the sea, or a lake. So, whenever you discover a river, be sure you'll eventually discover some water tiles at the end of the river. Just follow the flow direction!
 * All land tiles (regardless of terrain type) are surrounded by water tiles. Coasts may cover one to three tiles away from the land, after which the water becomes ocean. Sometimes tiles of land may appear inside the ocean, away from a larger landmass - these are called islands. They also have some water tiles around. Many times coasts from big landmasses and nearby islands form a single coastal region which allows free circulation of early game naval units. In some rare cases, such coastal regions may even connect two major continents.
 * Atolls only appear in coastal tiles
 * Sea resources also appear only in coastal tiles
 * Tundra base terrain is found far to the north/south of the world, never in central parts. If you find this terrain, know that you're nearing the polar regions of the map.
 * The far northern and southern regions of the world are the polar regions - they are always cold and inhospitable to life. Land in these regions is of the snow base terrain type (thus unsuitable for settling), and water forms ice, blocking sailing around the land. Most often continents which reach the far north/south may not be circumnavigated because of the ice blocking the path. Also, you can't circle the world from north to south because of the ice - not even with a submarine unit.
 * Terrain and Unit Mobility
 * The effectiveness and mobility of combat units is affected by the type of terrain they occupy. As stated above, many terrain features make movement more difficult, while at the same time providing additional options for cover and defense for combat. Such effects aren't found on water tiles, which all provide equal conditions for movement and combat. For more information, see Movement and Combat.
 *
 * Terrain and Combat
 * Finally, some terrain types automatically affect any combat waged there. Jungles, forests, and hills all give a defending unit many options to hide, which confers them a +25% combat bonus when defending. Marshes, flood plains, and oases have the exact opposite effect: they're open spaces which at the same time present difficulties to movement and reaction, in turn bringing a disadvantage to any unit defending there (-15% combat penalty).
 *
 * Nuclear Fallout
 * Few things in our world are so terrible as the results of a nuclear strike! Nuclear fallout is a terrain feature that represents these results in-game. It appears as a terrain feature on (usually all) tiles within the 2-tile blast radius of the nuke, making them completely unworkable and unproductive. In addition, entering a fallout tile without any movement infrastructure (Road or Railroad) will end a unit's movement. Fallout can be removed by a Worker with its special action Scrub Fallout - this will make the tile usable again. Note that this action doesn't repair any improvements on it - this will require a separate action!
 *
 * Terrain Potential
 * Each terrain tile may have production potential of some type, which may be used by an adjacent city working its tile. Most commonly, this potential includes Food Food, Production Production, or Gold Gold. Natural Wonders also have potential, including other types of stats.
 *
 * The particular potential a tile has depends on the features present on it, and follow this logic:
 *
 * Base terrain + Resources
 * OR
 *
 * Relief/terrain features + Resources
 * What this means is that a plains base terrain with a Wine resource of it will have a potential of (1 Food Food and 1 Production Production from the base terrain) + 2 Gold Gold from the Wine. However, a plains base terrain with a jungle on it and a Bananas resource will have 2 Food Food from the jungle (which overrides the base terrain) + 1 Food Food from the resource. Later, if you decide to cut down the jungle, the tile's potential will change to (1 Food Food and 1 Production Production) + 1 Food Food!
 *
 * It is important to note that all terrain/relief features alter the base terrain potential values. For example, a hill in the desert will have 2 Production Production potential, even though plain desert has no potential of any kind!
 *
 * Tile potential will be used by any city founded nearby, if the tiles in question are worked by its 20xPopulation5.png Citizens. Note also that certain buildings built in the city which controls the tiles, and certain special abilities may alter the tile potential. Often we get cases when the same tile may be controlled by two different cities. If these two cities have different buildings which somehow affect yield from the tile, then its final potential may change all the time according to which city controls it currently. Also, your Workers may build improvements on tiles to add more potential.
 *
 * Below is a table with all terrain types and features and their potential:
 *
 * Name	                Base Resource	            Improvements	                            Modifiers
 *                      Food	Production	Gold	Food	Production	Gold                    Movement	Melee strength
 * Coast                1	    0	        0**	    Lighthouse (+1)	N/A	N/A	                        1	0%
 * Desert	            0	    0	        0	    Farm (+1, +2, +2)	N/A	Trading Post (+1, +2)
 *                                                  River (+1; true prior to BNW)	                1	0%
 * Grassland	        2	    0	        0	    Farm (+1, +2, +2)	N/A	Trading Post (+1, +2)
 *                                                  River (+1; true prior to BNW)	        1	0%
 * Hill	                0	    2	        0	    Farm (+1, +2; must be next to fresh water)
 *                                                  Mine (+1, +2)
 *                                                  Trading Post (+1, +2)
 *                                                  River (+1; true prior to BNW)	        2†	+25%
 * Mountain             0	    0	        0	    N/A	N/A	N/A	N/A	+25%
 * Ocean	            1	    0	        0**	    Lighthouse (+1)	N/A	N/A	1	0%
 * Plains	            1	    1	        0	    Farm (+1, +2, +2)	N/A
 *                                                  Trading Post (+1, +2)
 *                                                  River (+1; true prior to BNW)	               1	0%
 * Snow	                0	    0	        0	    N/A	N/A	N/A	1	0%
 * Tundra               1	    0	        0	    Farm (+1, +2; must be next to fresh water)	N/A
 *                                                  Trading Post (+1, +2)
 *                                                  River (+1; true prior to BNW)	                1	0%
 * Forest*	            1	    1***	    0	    Clear-cut (+1, 0, -1)
 *                                                  Clear-cut (+1, 0, -1)
 *                                                  Lumber Mill (+1, +2)
 *                                                  Trading Post (+1, +2)
 *                                                  River (+1; true prior to BNW)	                2‡	+25%
 * Jungle*	            2	    0	        0	    Clear-cut (-1, -2)
 *                                                  Clear-cut (+2, +1)
 *                                                  Trading Post (+1, +2)
 *                                                  River (+1; true prior to BNW)	                2‡	+25%
 * Marsh*	            1	    0	        0	    Drain (+1)
 *                                                  Drain (0)
 *                                                  Trading Post (+1, +2)
 *                                                  River (+1; true prior to BNW)	                2	-15%
 * Atoll	            2	    1	        0**	    Lighthouse (+1)	N/A	N/A	                        1	0%
 * Flood plains	        2	    0	        0	    Farm (+1, +2)	N/A	Trading Post (+1, +2)
 *                                                  River (+1; true prior to BNW)	                1	-10%
 * Ice	                0	    0	        0	    N/A	N/A	N/A	                                    N/A	0%
 * Lakes	            2	    0	        0**	    N/A	N/A	N/A	                                    N/A	0%
 * Oasis                3	    0	        1****	N/A	N/A	N/A	                                    1	-10%
 * * The terrain tooltip indicates what forest/jungle/marsh tiles will turn into when cut/slashed/drained (respectively); very useful for planning
 * ** Gold Gold potential was 1 prior to Brave New World (see below)
 * *** 2 with the Iroquois unique building Longhouse
 * **** 3 with the Arabian unique building Bazaar
 * † Incan units ignore movement cost for hills
 * ‡ Iroquois units move through friendly forest and jungle tiles as if a road was present
 *
 * Brave New World changes
 * -----------------------
 * In the Brave New World expansion pack, Gold Gold potential has been removed from almost all terrain types, with the exception of the oasis.
 * This was probably done to shift the gold-producing process away from terrain and more towards the new Trade Routes system.
 * This makes conquering land with resources and/or Natural Wonders even more important in the early game, and you also need
 * to develop your trading network really quickly, or you'll find yourself running in the red.
 *
 * Natural Wonders
 * ---------------
 * There are some unique, incredible terrain features spread across the world. They are known as Natural Wonders and are special
 * in every sense of the word!
 *
 * Each Natural Wonder is available only once in the world and provides unique benefits to whoever controls it, or even
 * discovers its location, so send out your explorers in search for these features as soon as you're able!
 */
@Slf4j
@EqualsAndHashCode(of = "location")
public abstract class AbstractTerrain {

    @Getter @Setter
    private Point location;

    @Getter @Setter
    private City city;

    @Getter @Setter
    private AbstractResource resource;

    @Getter @Setter
    private AbstractImprovement improvement;

    private final FeatureList features = new FeatureList();

    public abstract String getClassUuid();
    public abstract Supply getBaseSupply();
    public abstract AbstractTerrainView getView();

    public abstract TerrainType getTileType();
    public abstract boolean isCanBuildCity();
    public abstract int getDefensiveBonusPercent();

    public Supply getTotalSupply(Civilization civilization) {
        Supply supply = getBaseSupply();

        if (improvement != null) {
            supply = supply.add(improvement.getBaseSupply(civilization));
        }

        if (resource != null) {
            supply = supply.add(resource.getBaseSupply(civilization));
        }

        return supply;
    }

    public FeatureList getFeatures() {
        return features;
    }

    public <F extends AbstractFeature> F getFeature(Class<F> featureClass) {
        return features.getByClass(featureClass);
    }

    public boolean hasFeature(Class<? extends AbstractFeature> ... features) {
        if (features != null) {
            for (Class<? extends AbstractFeature> feature : features) {
                if (this.features.getByClass(feature) != null) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasResources(ResourceType ... resourceTypes) {
        if (resource == null) {
            return false;
        }

        for (ResourceType resourceType : resourceTypes) {
            if (resource.getType().equals(resourceType)) {
                return true;
            }
        }

        return false;
    }

    public void addFeature(AbstractFeature feature) {
        features.add(feature);
    }

    public void removeFeature(AbstractFeature feature) {
        features.remove(feature);
    }

    public boolean isOcean() {
        return isIn(Ocean.class);
    }

    public boolean isTundra() {
        return isIn(Tundra.class);
    }

    public boolean isGrassland() {
        return isIn(Grassland.class);
    }

    public boolean isIn(Class<? extends AbstractTerrain> ... classes) {
        if (classes != null) {
            for (Class<? extends AbstractTerrain> clazz : classes) {
                if (clazz.equals(getClass())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public String toString() {
        String features = this.features.stream().map(e -> e.getClass().getSimpleName()).collect(Collectors.joining(", "));
        return getClass().getSimpleName() + location +
            ", features=[" + features + "]" +
            ", improvement=" + improvement +
            ", resource=" + resource;
    }
}
