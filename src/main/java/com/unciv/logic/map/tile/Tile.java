package com.unciv.logic.map.tile;

import com.badlogic.gdx.math.Vector2;
import com.unciv.Constants;
import com.unciv.logic.map.TileMap;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tile implements IsPartOfGameInfoSerialization {

    TileMap tileMap;

    RuleSet ruleset;  // a tile can be a tile with a ruleset, even without a map.

    TileInfoImprovementFunctions improvementFunctions = new TileInfoImprovementFunctions(this);

    TileStatFunctions stats = new TileStatFunctions(this);

    private boolean isCityCenterInternal = false;

    City owningCity;

    @Transient
    private Terrain baseTerrainObject;

    // These are for performance - checked with every tile movement and "canEnter" check, which makes them performance-critical
    @Transient
    boolean isLand = false;

    @Transient
    boolean isWater = false;

    @Transient
    boolean isOcean = false;

    MapUnit militaryUnit;
    MapUnit civilianUnit;
    List<MapUnit> airUnits = new ArrayList<MapUnit>();

    public Vector2 position = Vector2.Zero;
    String baseTerrain;

    @Setter
    List<String> terrainFeatures = new ArrayList<>();

    /** Should be immutable - never be altered in-place, instead replaced */
    Set<String> exploredBy = new HashSet<>();

    @Transient
    @Setter
    List<Terrain> terrainFeatureObjects = new ArrayList<>();

    @Transient
    /** Saves a sequence of a list */
    @Setter
    List<Terrain> allTerrains;

    @Transient
    @Setter
    Terrain lastTerrain;

    @Transient
    @Setter
    UniqueMap terrainUniqueMap = new UniqueMap();

    @Transient
    /** Between 0.0 and 1.0 - For map generation use only */
    Double humidity;

    @Transient
    /** Between -1.0 and 1.0 - For map generation use only */
    Double temperature;

    String naturalWonder;
    String resource;

    void setResource(String value) {
        tileResourceCache = null;
        resource = value;
    }

    Integer resourceAmount;
    String improvement;
    String improvementInProgress;
    boolean improvementIsPillaged;

    var roadStatus = RoadStatus.None;
    boolean roadIsPillaged;
    String roadOwner = ""; // either who last built the road or last owner of tile
    int turnsToImprovement;

    public boolean isHill() {
        return Constants.hill.equals(baseTerrain) || terrainFeatures.contains(Constants.hill);
    }

    boolean hasBottomRightRiver;
    boolean hasBottomRiver;
    boolean hasBottomLeftRiver;

    TileHistory history = new TileHistory();

    private var continent = -1;

    Float latitude;
    get() = HexMath.getLatitude(position)
    val longitude: Float
    get() = HexMath.getLongitude(position)

    public void setOwningCity(City city) {
        if (city != null) {
            if (roadStatus != RoadStatus.None && roadOwner != "") {
                // remove previous neutral tile owner
                getRoadOwner()!!.neutralRoads.remove(this.position);
            }
            roadOwner = city.civ.civName; // only when taking control, otherwise last owner
        } else if (roadStatus != RoadStatus.None && owningCity != null) {
            // Razing City! Remove owner
            roadOwner = "";
        }
        owningCity = city;
        isCityCenterInternal = getCity()?.location == position;
    }

    public Tile clone() {
        Tile toReturn = new Tile();
        toReturn.tileMap = tileMap;
        toReturn.ruleset = ruleset;
        toReturn.isCityCenterInternal = isCityCenterInternal;
        toReturn.owningCity = owningCity;
        toReturn.baseTerrainObject = baseTerrainObject;
        toReturn.isLand = isLand;
        toReturn.isWater = isWater;
        toReturn.isOcean = isOcean;
        if (militaryUnit != null) toReturn.militaryUnit = militaryUnit!!.clone();
        if (civilianUnit != null) toReturn.civilianUnit = civilianUnit!!.clone();
        for (airUnit in airUnits) toReturn.airUnits.add(airUnit.clone());
        toReturn.position = position.cpy();
        toReturn.baseTerrain = baseTerrain;
        toReturn.terrainFeatures = terrainFeatures; // immutable lists can be directly passed around
        toReturn.terrainFeatureObjects = terrainFeatureObjects;
        toReturn.naturalWonder = naturalWonder;
        toReturn.resource = resource;
        toReturn.resourceAmount = resourceAmount;
        toReturn.improvement = improvement;
        toReturn.improvementInProgress = improvementInProgress;
        toReturn.improvementIsPillaged = improvementIsPillaged;
        toReturn.roadStatus = roadStatus;
        toReturn.roadIsPillaged = roadIsPillaged;
        toReturn.roadOwner = roadOwner;
        toReturn.turnsToImprovement = turnsToImprovement;
        toReturn.hasBottomLeftRiver = hasBottomLeftRiver;
        toReturn.hasBottomRightRiver = hasBottomRightRiver;
        toReturn.hasBottomRiver = hasBottomRiver;
        toReturn.continent = continent;
        toReturn.exploredBy = exploredBy;
        toReturn.history = history.clone();
        // Setting even though it's transient - where it's needed, it's a real performance saver
        toReturn.tileResourceCache = tileResourceCache;
        return toReturn;
    }

    //region pure functions

    public boolean containsGreatImprovement() {
        return getTileImprovement()?.isGreatImprovement() == true;
    }

    /** Returns military, civilian and air units in tile */
    fun getUnits() = sequence {
        if (militaryUnit != null) yield(militaryUnit!!);
        if (civilianUnit != null) yield(civilianUnit!!);
        if (airUnits.isNotEmpty()) yieldAll(airUnits);
    }

    /** This is for performance reasons of canPassThrough() - faster than getUnits().firstOrNull() */
    public MapUnit getFirstUnit() {
        if (militaryUnit != null) return militaryUnit!!;
        if (civilianUnit != null) return civilianUnit!!;
        if (airUnits.isNotEmpty()) return airUnits.first();
        return null;
    }

    /** Return null if military on tile, or no civilian */
    public MapUnit getUnguardedCivilian(attacker: MapUnit) {
        if (militaryUnit != null && militaryUnit != attacker) return null;
        if (civilianUnit != null) return civilianUnit!!;
        return null;
    }

    public City getCity(): {
        return owningCity;
    }

    @Transient
    private TileResource tileResourceCache;
    private TileResource tileResource;
    public TileResource getTileResource() {
        if (tileResourceCache == null) {
            if (resource == null) throw new IllegalStateException("No resource exists for this tile!");
            if (!ruleset.tileResources.containsKey(resource!!)) throw new IllegalStateException("Resource $resource does not exist in this ruleset!");
            tileResourceCache = ruleset.tileResources[resource!!]!!;
        }
        return tileResourceCache!!;
    }

    private Terrain getNaturalWonder() {
        if (naturalWonder == null) throw new IllegalStateException("No natural wonder exists for this tile!");
        else ruleset.terrains[naturalWonder !!]!!;
    }

    public boolean isVisible(Civilization player) {
        if (DebugUtils.VISIBLE_MAP)
            return true;
        return player.viewableTiles.contains(this);
    }

    public boolean isExplored(player: Civilization) {
        if (DebugUtils.VISIBLE_MAP || Constants.spectator.equals(player.civName))
            return true;
        return exploredBy.contains(player.civName);
    }

    public void setExplored(Civilization player, Boolean isExplored, Vector2 explorerPosition = null) {
        if (isExplored) {
            // Disable the undo button if a new tile has been explored
            if (!exploredBy.contains(player.civName)) {
                if (GUI.isWorldLoaded()) {
                    val worldScreen = GUI.getWorldScreen();
                    worldScreen.preActionGameInfo = worldScreen.gameInfo;
                }
                exploredBy = exploredBy.withItem(player.civName);
            }

            if (player.playerType == PlayerType.Human)
                player.exploredRegion.checkTilePosition(position, explorerPosition);
        } else {
            exploredBy = exploredBy.withoutItem(player.civName);
        }
    }

    public boolean isCityCenter() { return isCityCenterInternal; }

    public boolean isNaturalWonder() { return naturalWonder != null; }

    public boolean isImpassible() { return lastTerrain.impassable; }

    TileImprovement getTileImprovement() {
        if (improvement == null) return null;
        else return ruleset.tileImprovements[improvement !!];
    }

    TileImprovement getUnpillagedTileImprovement() {
        if (getUnpillagedImprovement() == null) return null;
        else return ruleset.tileImprovements[improvement!!];
    }

    public TileImprovement getTileImprovementInProgress() {
        if (improvementInProgress == null) return null;
        else return ruleset.tileImprovements[improvementInProgress!!];
    }

    public TileImprovement getImprovementToPillage() {
        if (canPillageTileImprovement())
            return ruleset.tileImprovements[improvement]!!;
        if (canPillageRoad())
            return ruleset.tileImprovements[roadStatus.name]!!;
        return null;
    }

    // same as above, but slightly quicker
    public String getImprovementToPillageName() {
        if (canPillageTileImprovement())
            return improvement;
        if (canPillageRoad())
            return roadStatus.name;
        return null;
    }

    public TileImprovement getImprovementToRepair() {
        if (improvement != null && improvementIsPillaged)
            return ruleset.tileImprovements[improvement]!!;
        if (roadStatus != RoadStatus.None && roadIsPillaged)
            return ruleset.tileImprovements[roadStatus.name]!!;
        return null;
    }

    public boolean canPillageTile() {
        return canPillageTileImprovement() || canPillageRoad();
    }

    public boolean canPillageTileImprovement() {
        return improvement != null && !improvementIsPillaged
                && !ruleset.tileImprovements[improvement]!!.hasUnique(UniqueType.Unpillagable)
                && !ruleset.tileImprovements[improvement]!!.hasUnique(UniqueType.Irremovable);
    }

    public boolean canPillageRoad() {
        return roadStatus != RoadStatus.None && !roadIsPillaged
                && !ruleset.tileImprovements[roadStatus.name]!!.hasUnique(UniqueType.Unpillagable)
                && !ruleset.tileImprovements[roadStatus.name]!!.hasUnique(UniqueType.Irremovable);
    }

    public String getUnpillagedImprovement() {
        if (improvementIsPillaged) return null;
        else return improvement;
    }

    public RoadStatus getUnpillagedRoad() {
        if (roadIsPillaged) return RoadStatus.None;
        else return roadStatus;
    }

    public TileImprovement getUnpillagedRoadImprovement() {
        if (getUnpillagedRoad() == RoadStatus.None) return null;
        else return ruleset.tileImprovements[getUnpillagedRoad().name];
    }

    /** Does not remove roads */
    fun removeImprovement() =
            improvementFunctions.changeImprovement(null)

    fun changeImprovement(improvementStr: String, civToHandleCompletion:Civilization? = null) =
            improvementFunctions.changeImprovement(improvementStr, civToHandleCompletion)

    // function handling when adding a road to the tile
    public void addRoad(RoadStatus roadType, Civilization creatingCivInfo) {
        roadStatus = roadType;
        roadIsPillaged = false;
        if (getOwner() != null) {
            roadOwner = getOwner()!!.civName;
        } else if (creatingCivInfo != null) {
            roadOwner = creatingCivInfo.civName; // neutral tile, use building unit
            creatingCivInfo.neutralRoads.add(this.position);
        }
    }

    // function handling when removing a road from the tile
    public void removeRoad() {
        roadIsPillaged = false;
        if (roadStatus == RoadStatus.None) return;
        roadStatus = RoadStatus.None;
        if (owningCity == null)
            getRoadOwner()?.neutralRoads?.remove(this.position);
    }

    public String getShownImprovement(Civilization viewingCiv) {
        if (viewingCiv == null || viewingCiv.playerType == PlayerType.AI || viewingCiv.isSpectator())
            return improvement;
        else
            return viewingCiv.lastSeenImprovement[position];
    }


    // This is for performance - since we access the neighbors of a tile ALL THE TIME,
    // and the neighbors of a tile never change, it's much more efficient to save the list once and for all!
    List<Tile> neighbors() {
        return getTilesAtDistance(1).toList().asSequence();
    }

    // We have to .toList() so that the values are stored together once for caching,
    // and the toSequence so that aggregations (like neighbors.flatMap{it.units} don't take up their own space

    /** Returns the left shared neighbor of `this` and [neighbor] (relative to the view direction `this`->[neighbor]), or null if there is no such tile. */
    public Tile getLeftSharedNeighbor(Tile neighbor) {
        return tileMap.getClockPositionNeighborTile(this,(tileMap.getNeighborTileClockPosition(this, neighbor) - 2) % 12);
    }

    /** Returns the right shared neighbor of `this` and [neighbor] (relative to the view direction `this`->[neighbor]), or null if there is no such tile. */
    public Tile getRightSharedNeighbor(Tile neighbor) {
        return tileMap.getClockPositionNeighborTile(this,(tileMap.getNeighborTileClockPosition(this, neighbor) + 2) % 12);
    }

    fun getRow() = HexMath.getRow(position)

    fun getColumn() = HexMath.getColumn(position)

    @delegate:Transient
    public int getTileHeight() {
        // for e.g. hill+forest this is 2, since forest is visible above units
        if (terrainHasUnique(UniqueType.BlocksLineOfSightAtSameElevation)) return unitHeight + 1;
        else return getUnitHeight();
    }

    @delegate:Transient
    public int getUnitHeight() {
        // for e.g. hill+forest this is 1, since only hill provides height for units
        return allTerrains
            .flatMap( it -> it.getMatchingUniques(UniqueType.VisibilityElevation) )
            .map( it -> it.params[0].toInt() )
            .sum();
    }


    public Terrain getBaseTerrain() { return baseTerrainObject; }

    public Civilization getOwner() { return getCity()?.civ; }

    public Civilization getRoadOwner() {
        if (roadOwner != "")
            return tileMap.gameInfo.getCivilization(roadOwner);
        else
            return getOwner();
    }

    public boolean isFriendlyTerritory(Civilization civInfo) {
        Civilization tileOwner = getOwner();
        if (tileOwner == null) return false;
        if (tileOwner == civInfo) return true;
        if (!civInfo.knows(tileOwner)) return false;
        return tileOwner.getDiplomacyManager(civInfo).isConsideredFriendlyTerritory();
    }

    public boolean isEnemyTerritory(Civilization civInfo) {
        Civilization tileOwner = getOwner();
        if (tileOwner == null) return false;
        return civInfo.isAtWarWith(tileOwner);
    }

    public boolean isRoughTerrain() { return allTerrains.any(it -> it.isRough() ); }

    /** Checks whether any of the TERRAINS of this tile has a certain unique */
    fun terrainHasUnique(uniqueType: UniqueType) = terrainUniqueMap.getUniques(uniqueType).any()

    /** Get all uniques of this type that any TERRAIN on this tile has */
    fun getTerrainMatchingUniques(UniqueType uniqueType, stateForConditionals: StateForConditionals = StateForConditionals(tile=this) ): Sequence<Unique> {
        return terrainUniqueMap.getMatchingUniques(uniqueType, stateForConditionals);
    }

    /** Get all uniques of this type that any part of this tile has: terrains, improvement, resource */
    fun getMatchingUniques(UniqueType uniqueType, stateForConditionals: StateForConditionals = StateForConditionals(tile=this)): Sequence<Unique> {
        var uniques = getTerrainMatchingUniques(uniqueType, stateForConditionals);
        if (getUnpillagedImprovement() != null){
            TileImprovement tileImprovement = getTileImprovement();
            if (tileImprovement != null) {
                uniques += tileImprovement.getMatchingUniques(uniqueType, stateForConditionals);
            }
        }
        if (resource != null)
            uniques += tileResource.getMatchingUniques(uniqueType, stateForConditionals);
        return uniques;
    }

    public City getWorkingCity() {
        Civilization civInfo = getOwner();
        if (civInfo == null) return null;
        return civInfo.cities.firstOrNull(it -> it.isWorked(this) );
    }

    public boolean isBlockaded() {
        Civilization owner = getOwner();
        if (owner == null) return false;
        val unit = militaryUnit;

        // If tile has unit
        if (unit != null) {
            if (unit.civ == owner) return false;              // Own - unblocks tile;
            if (unit.civ.isAtWarWith(owner)) return true;     // Enemy - blocks tile;
            return false;                           // Neutral - unblocks tile;
        }

        // No unit -> land tile is not blocked
        if (isLand)
            return false;

        // For water tiles need also to check neighbors:
        // enemy military naval units blockade all adjacent water tiles.
        for (neighbor in neighbors) {
            // Check only water neighbors
            if (!neighbor.isWater)
                continue

                val neighborUnit = neighbor.militaryUnit ?: continue

            // Embarked units do not blockade adjacent tiles
            if (neighborUnit.civ.isAtWarWith(owner) && !neighborUnit.isEmbarked())
                return true
        }
        return false
    }

    fun isWorked(): Boolean = getWorkingCity() != null
    fun providesYield() = getCity() != null && (isCityCenter() || isWorked()
            || getUnpillagedTileImprovement()?.hasUnique(UniqueType.TileProvidesYieldWithoutPopulation) == true
            || terrainHasUnique(UniqueType.TileProvidesYieldWithoutPopulation))

    fun isLocked(): Boolean {
        val workingCity = getWorkingCity()
        return workingCity != null && workingCity.lockedTiles.contains(position)
    }

    // For dividing the map into Regions to determine start locations
    fun getTileFertility(checkCoasts: Boolean): Int {
        var fertility = 0
        for (terrain in allTerrains) {
            if (terrain.hasUnique(UniqueType.OverrideFertility))
                return terrain.getMatchingUniques(UniqueType.OverrideFertility).first().params[0].toInt()
            else
                fertility += terrain.getMatchingUniques(UniqueType.AddFertility)
                        .sumOf { it.params[0].toInt() }
        }
        if (isAdjacentToRiver()) fertility += 1
        if (isAdjacentTo(Constants.freshWater)) fertility += 1 // meaning total +2 for river
        if (checkCoasts && isCoastalTile()) fertility += 2
        return fertility
    }

    fun providesResources(civInfo: Civilization): Boolean {
        if (!hasViewableResource(civInfo)) return false
        if (isCityCenter()) return true
        val improvement = getUnpillagedTileImprovement()
        if (improvement != null && improvement.name in tileResource.getImprovements()
                && (improvement.techRequired == null || civInfo.tech.isResearched(improvement.techRequired!!))
            ) return true
        // TODO: Generic-ify to unique
        return (tileResource.resourceType == ResourceType.Strategic
                && improvement != null
                && improvement.isGreatImprovement())
    }

    // This should be the only adjacency function
    fun isAdjacentTo(terrainFilter:String): Boolean {
        // Rivers are odd, as they aren't technically part of any specific tile but still count towards adjacency
        if (terrainFilter == "River") return isAdjacentToRiver()
        if (terrainFilter == Constants.freshWater && isAdjacentToRiver()) return true
        return (neighbors + this).any { neighbor -> neighbor.matchesFilter(terrainFilter) }
    }

    /** Implements [UniqueParameterType.TileFilter][com.unciv.models.ruleset.unique.UniqueParameterType.TileFilter] */
    fun matchesFilter(filter: String, civInfo: Civilization? = null): Boolean {
        if (matchesTerrainFilter(filter, civInfo)) return true
        if (improvement != null && !improvementIsPillaged && ruleset.tileImprovements[improvement]!!.matchesFilter(filter)) return true
        return improvement == null && filter == "unimproved"
    }

    /** Implements [UniqueParameterType.TerrainFilter][com.unciv.models.ruleset.unique.UniqueParameterType.TerrainFilter] */
    fun matchesTerrainFilter(filter: String, observingCiv: Civilization? = null): Boolean {
        return when (filter) {
            "All" -> true
            baseTerrain -> true
            "Water" -> isWater
            "Land" -> isLand
            Constants.coastal -> isCoastalTile()
            "River" -> isAdjacentToRiver()
            naturalWonder -> true
            "Open terrain" -> !isRoughTerrain()
            "Rough terrain" -> isRoughTerrain()

            "Foreign Land", "Foreign" -> observingCiv != null && !isFriendlyTerritory(observingCiv)
            "Friendly Land", "Friendly" -> observingCiv != null && isFriendlyTerritory(observingCiv)
            "Enemy Land", "Enemy" -> observingCiv != null && isEnemyTerritory(observingCiv)

            resource -> observingCiv != null && hasViewableResource(observingCiv)
            "Water resource" -> isWater && observingCiv != null && hasViewableResource(observingCiv)
            "Natural Wonder" -> naturalWonder != null
            "Featureless" -> terrainFeatures.isEmpty()
            Constants.freshWaterFilter -> isAdjacentTo(Constants.freshWater)

            in terrainFeatures -> true
            else -> {
                if (terrainUniqueMap.getUniques(filter).any()) return true
                if (getOwner()?.nation?.matchesFilter(filter) == true) return true

                // Resource type check is last - cannot succeed if no resource here
                if (resource == null) return false

                // Checks 'luxury resource', 'strategic resource' and 'bonus resource' - only those that are visible of course
                // not using hasViewableResource as observingCiv is often not passed in,
                // and we want to be able to at least test for non-strategic in that case.
                val resourceObject = tileResource
                val hasResourceWithFilter =
                        tileResource.name == filter
                                || tileResource.hasUnique(filter)
                                || tileResource.resourceType.name + " resource" == filter
                if (!hasResourceWithFilter) return false

                // Now that we know that this resource matches the filter - can the observer see that there's a resource here?
                if (resourceObject.revealedBy == null) return true  // no need for tech
                if (observingCiv == null) return false  // can't check tech
                return observingCiv.tech.isResearched(resourceObject.revealedBy!!)
            }
        }
    }

    fun hasImprovementInProgress() = improvementInProgress != null && turnsToImprovement > 0

    @delegate:Transient
    private val _isCoastalTile: Boolean by lazy { neighbors.any { it.baseTerrain == Constants.coast } }
    fun isCoastalTile() = _isCoastalTile

    fun hasViewableResource(civInfo: Civilization): Boolean =
    resource != null && (tileResource.revealedBy == null || civInfo.tech.isResearched(
    tileResource.revealedBy!!))

    fun getViewableTilesList(distance: Int): List<Tile> =
            tileMap.getViewableTiles(position, distance)

    fun getTilesInDistance(distance: Int): Sequence<Tile> =
            tileMap.getTilesInDistance(position, distance)

    fun getTilesInDistanceRange(range: IntRange): Sequence<Tile> =
            tileMap.getTilesInDistanceRange(position, range)

    public List<Tile> getTilesAtDistance(int distance) {
        return tileMap.getTilesAtDistance(position, distance);
    }

    public float getDefensiveBonus() {
        var bonus = baseTerrainObject.defenceBonus;
        if (terrainFeatureObjects.isNotEmpty()) {
            float otherTerrainBonus = terrainFeatureObjects.maxOf(it -> it.defenceBonus);
            if (otherTerrainBonus != 0f) bonus = otherTerrainBonus;  // replaces baseTerrainObject
        }

        if (naturalWonder != null) bonus += getNaturalWonder().defenceBonus;
        TileImprovement tileImprovement = getUnpillagedTileImprovement();
        if (tileImprovement != null) {
            for (unique in tileImprovement.getMatchingUniques(UniqueType.DefensiveBonus, StateForConditionals(tile = this)))
                bonus += unique.params[0].toFloat() / 100;
        }
        return bonus;
    }

    public int aerialDistanceTo(Tile otherTile) {
        float xDelta = position.x - otherTile.position.x;
        float yDelta = position.y - otherTile.position.y;
        float distance = maxOf(abs(xDelta), abs(yDelta), abs(xDelta - yDelta));

        var wrappedDistance = Float.MAX_VALUE;
        if (tileMap.mapParameters.worldWrap) {
            val otherTileUnwrappedPos = tileMap.getUnWrappedPosition(otherTile.position);
            float xDeltaWrapped = position.x - otherTileUnwrappedPos.x;
            float yDeltaWrapped = position.y - otherTileUnwrappedPos.y;
            wrappedDistance = maxOf(abs(xDeltaWrapped), abs(yDeltaWrapped), abs(xDeltaWrapped - yDeltaWrapped));
        }

        return min(distance, wrappedDistance).toInt();
    }

    public boolean canBeSettled() {
        val modConstants = tileMap.gameInfo.ruleset.modOptions.constants;
        if (isWater || isImpassible())
            return false;
        if (getTilesInDistance(modConstants.minimalCityDistanceOnDifferentContinents)
                .any { it.isCityCenter() && it.getContinent() != getContinent() }
            || getTilesInDistance(modConstants.minimalCityDistance)
                .any { it.isCityCenter() && it.getContinent() == getContinent() }
        ) {
            return false;
        }
        return true;
    }

    /** Shows important properties of this tile for debugging _only_, it helps to see what you're doing */
    @Override
    public String toString() {
        List<String> lineList = new ArrayList<>();
        lineList.add("Tile @$position");
        if (!this::baseTerrain.isInitialized) return lineList.get(0) + ", uninitialized";
        if (isCityCenter()) lineList.add(getCity()!!.name);
        lineList.add(baseTerrain);
        for (String terrainFeature : terrainFeatures) lineList.add(terrainFeature);
        if (resource != null) {
            if (tileResource.resourceType == ResourceType.Strategic)
                lineList.add("{$resourceAmount} {$resource}");
            else
                lineList.add(resource!!);
        }
        if (naturalWonder != null) lineList.add(naturalWonder!!);
        if (roadStatus !== RoadStatus.None && !isCityCenter()) lineList.add(roadStatus.name);
        if (improvement != null) lineList.add(improvement!!);
        if (civilianUnit != null) lineList.add(civilianUnit!!.name + " - " + civilianUnit!!.civ.civName);
        if (militaryUnit != null) lineList.add(militaryUnit!!.name + " - " + militaryUnit!!.civ.civName);
        if (this::baseTerrainObject.isInitialized && isImpassible()) lineList.add(Constants.impassable);
        return String.join("\n", lineList);
    }

    /** The two tiles have a river between them */
    public boolean isConnectedByRiver(Tile otherTile) {
        if (otherTile == this) throw new IllegalStateException("Should not be called to compare to self!");

        return when (tileMap.getNeighborTileClockPosition(this, otherTile)) {
            2 -> otherTile.hasBottomLeftRiver // we're to the bottom-left of it
            4 -> hasBottomRightRiver // we're to the top-left of it
            6 -> hasBottomRiver // we're directly above it
            8 -> hasBottomLeftRiver // we're to the top-right of it
            10 -> otherTile.hasBottomRightRiver // we're to the bottom-right of it
            12 -> otherTile.hasBottomRiver // we're directly below it
            else -> throw new IllegalStateException("Should never call this function on a non-neighbor!");
        }
    }

    @delegate:Transient
    private val isAdjacentToRiverLazy by lazy {
        // These are so if you add a river at the bottom of the map (no neighboring tile to be connected to)
        //   that tile is still considered adjacent to river
        hasBottomLeftRiver || hasBottomRiver || hasBottomRightRiver
                || neighbors.any { isConnectedByRiver(it) } }
    fun isAdjacentToRiver() = isAdjacentToRiverLazy

    /**
     * @returns whether units of [civInfo] can pass through this tile, considering only civ-wide filters.
     * Use [UnitMovement.canPassThrough] to check whether a specific unit can pass through a tile.
     */
    fun canCivPassThrough(civInfo: Civilization): Boolean {
        val tileOwner = getOwner()
        // comparing the CivInfo objects is cheaper than comparing strings
        if (tileOwner == null || tileOwner == civInfo) return true
        if (isCityCenter() && civInfo.isAtWarWith(tileOwner)
                && !getCity()!!.hasJustBeenConquered) return false
        if (!civInfo.diplomacyFunctions.canPassThroughTiles(tileOwner)) return false
        return true
    }

    fun hasEnemyInvisibleUnit(viewingCiv: Civilization): Boolean {
        val unitsInTile = getUnits()
        if (unitsInTile.none()) return false
        if (unitsInTile.first().civ != viewingCiv &&
                unitsInTile.firstOrNull { it.isInvisible(viewingCiv) } != null) {
            return true
        }
        return false
    }

    fun hasConnection(civInfo: Civilization) =
    getUnpillagedRoad() != RoadStatus.None || forestOrJungleAreRoads(civInfo)


    private fun forestOrJungleAreRoads(civInfo: Civilization) =
    civInfo.nation.forestsAndJunglesAreRoads
                    && (terrainFeatures.contains(Constants.jungle) || terrainFeatures.contains(Constants.forest))
            && isFriendlyTerritory(civInfo)

    fun getRulesetIncompatibility(ruleset: Ruleset): HashSet<String> {
        val out = HashSet<String>()
        if (!ruleset.terrains.containsKey(baseTerrain))
            out.add("Base terrain [$baseTerrain] does not exist in ruleset!")
        for (terrainFeature in terrainFeatures.filter { !ruleset.terrains.containsKey(it) })
        out.add("Terrain feature [$terrainFeature] does not exist in ruleset!")
        if (resource != null && !ruleset.tileResources.containsKey(resource))
            out.add("Resource [$resource] does not exist in ruleset!")
        if (improvement != null && !ruleset.tileImprovements.containsKey(improvement))
            out.add("Improvement [$improvement] does not exist in ruleset!")
        if (naturalWonder != null && !ruleset.terrains.containsKey(naturalWonder))
            out.add("Natural Wonder [$naturalWonder] does not exist in ruleset!")
        return out
    }

    fun getContinent() = continent

    /** Checks if this tile is marked as target tile for a building with a [UniqueType.CreatesOneImprovement] unique */
    fun isMarkedForCreatesOneImprovement() =
    turnsToImprovement < 0 && improvementInProgress != null
    /** Checks if this tile is marked as target tile for a building with a [UniqueType.CreatesOneImprovement] unique creating a specific [improvement] */
    fun isMarkedForCreatesOneImprovement(improvement: String) =
    turnsToImprovement < 0 && improvementInProgress == improvement

    //endregion
    //region state-changing functions

    /** Do not run this on cloned tiles, since then the cloned *units* will be assigned to the civs
     * Instead run setTerrainTransients */
    fun setTransients() {
        setTerrainTransients()
        setUnitTransients(true)
        setOwnerTransients()
    }

    fun setTerrainTransients() {
        if (!ruleset.terrains.containsKey(baseTerrain))
            throw Exception("Terrain $baseTerrain does not exist in ruleset!")
        baseTerrainObject = ruleset.terrains[baseTerrain]!!
                setTerrainFeatures(terrainFeatures)
        isWater = getBaseTerrain().type == TerrainType.Water
        isLand = getBaseTerrain().type == TerrainType.Land
        isOcean = baseTerrain == Constants.ocean

        // Resource amounts missing - Old save or bad mapgen?
        if (::tileMap.isInitialized && resource != null && tileResource.resourceType == ResourceType.Strategic && resourceAmount == 0) {
            // Let's assume it's a small deposit
            setTileResource(tileResource, majorDeposit = false)
        }
    }

    fun setUnitTransients(unitCivTransients: Boolean) {
        for (unit in getUnits()) {
            unit.currentTile = this
            if (unitCivTransients)
                unit.assignOwner(tileMap.gameInfo.getCivilization(unit.owner), false)
            unit.setTransients(ruleset)
        }
    }

    fun setOwnerTransients() {
        if (owningCity == null && roadOwner != "")
            getRoadOwner()!!.neutralRoads.add(this.position)
    }

    fun stripUnits() {
        for (unit in this.getUnits()) removeUnit(unit)
    }

    /**
     * Sets this tile's [resource] and, if [newResource] is a Strategic resource, [resourceAmount] fields.
     *
     * [resourceAmount] is determined by [MapParameters.mapResources] and [majorDeposit], and
     * if the latter is `null` a random choice between major and minor deposit is made, approximating
     * the frequency [MapRegions][com.unciv.logic.map.mapgenerator.MapRegions] would use.
     * A randomness source ([rng]) can optionally be provided for that step (not used otherwise).
     */
    fun setTileResource(newResource: TileResource, majorDeposit: Boolean? = null, rng: Random = Random.Default) {
        resource = newResource.name

        if (newResource.resourceType != ResourceType.Strategic) {
            resourceAmount = 0
            return
        }

        for (unique in newResource.getMatchingUniques(UniqueType.ResourceAmountOnTiles, StateForConditionals(tile = this))) {
            if (matchesTerrainFilter(unique.params[0])) {
                resourceAmount = unique.params[1].toInt()
                return
            }
        }

        val majorDepositFinal = majorDeposit ?: (rng.nextDouble() < approximateMajorDepositDistribution())
        val depositAmounts = if (majorDepositFinal) newResource.majorDepositAmount else newResource.minorDepositAmount
                resourceAmount = when (tileMap.mapParameters.mapResources) {
            MapResources.sparse -> depositAmounts.sparse
            MapResources.abundant -> depositAmounts.abundant
            else -> depositAmounts.default
        }
    }

    private fun approximateMajorDepositDistribution(): Double {
        // We can't replicate the MapRegions resource distributor, so let's try to get
        // a close probability of major deposits per tile
        var probability = 0.0
        for (unique in allTerrains.flatMap { it.getMatchingUniques(UniqueType.MajorStrategicFrequency) }) {
            val frequency = unique.params[0].toIntOrNull() ?: continue
            if (frequency <= 0) continue
                    // The unique param is literally "every N tiles", so to get a probability p=1/f
            probability += 1.0 / frequency
        }
        return if (probability == 0.0) 0.04  // This is the default of 1 per 25 tiles
        else probability
    }

    fun setTerrainFeatures(terrainFeatureList: List<String>) {
        terrainFeatures = terrainFeatureList
        terrainFeatureObjects = terrainFeatureList.mapNotNull { ruleset.terrains[it] }
        allTerrains = sequence {
            yield(baseTerrainObject) // There is an assumption here that base terrains do not change
            if (naturalWonder != null) yield(getNaturalWonder())
            yieldAll(terrainFeatureObjects)
        }.toList().asSequence() //Save in memory, and return as sequence

        updateUniqueMap()

        lastTerrain = when {
            terrainFeatures.isNotEmpty() -> ruleset.terrains[terrainFeatures.last()]
                    ?: getBaseTerrain()  // defense against rare edge cases involving baseTerrain Hill deprecation
            naturalWonder != null -> getNaturalWonder()
            else -> getBaseTerrain()
        }
    }

    private fun updateUniqueMap() {
        if (!::tileMap.isInitialized) return // This tile is a fake tile, for visual display only (e.g. map editor, civilopedia)
                val terrainNameList = allTerrains.map { it.name }.toList()

        // List hash is function of all its items, so the same items in the same order will always give the same hash
        val cachedUniqueMap = tileMap.tileUniqueMapCache[terrainNameList]
        terrainUniqueMap = if (cachedUniqueMap != null)
            cachedUniqueMap
        else {
            val newUniqueMap = UniqueMap()
            for (terrain in allTerrains)
                newUniqueMap.addUniques(terrain.uniqueObjects)
            tileMap.tileUniqueMapCache[terrainNameList] = newUniqueMap
            newUniqueMap
        }
    }

    fun addTerrainFeature(terrainFeature: String) =
    setTerrainFeatures(ArrayList(terrainFeatures).apply { add(terrainFeature) })

    fun removeTerrainFeature(terrainFeature: String) =
    setTerrainFeatures(ArrayList(terrainFeatures).apply { remove(terrainFeature) })

    fun removeTerrainFeatures() =
    setTerrainFeatures(listOf())

    /** Clean stuff missing in [ruleset] - called from [TileMap.removeMissingTerrainModReferences]
     *  Must be able to run before [setTransients] - and does not need to fix transients.
     */
    fun removeMissingTerrainModReferences(ruleset: Ruleset) {
        terrainFeatures = terrainFeatures.filter { it in ruleset.terrains }
        if (resource != null && resource !in ruleset.tileResources)
        resource = null
        if (improvement != null && improvement !in ruleset.tileImprovements)
        removeImprovement()
    }

    /** If the unit isn't in the ruleset we can't even know what type of unit this is! So check each place
     * This works with no transients so can be called from gameInfo.setTransients with no fear
     */
    fun removeUnit(mapUnit: MapUnit) {
        when {
            airUnits.contains(mapUnit) -> airUnits.remove(mapUnit)
            civilianUnit == mapUnit -> civilianUnit = null
            militaryUnit == mapUnit -> militaryUnit = null
        }
    }

    fun startWorkingOnImprovement(improvement: TileImprovement, civInfo: Civilization, unit: MapUnit) {
        improvementInProgress = improvement.name
        turnsToImprovement = if (civInfo.gameInfo.gameParameters.godMode) 1
        else improvement.getTurnsToBuild(civInfo, unit)
    }

    /** Clears [improvementInProgress] and [turnsToImprovement] */
    fun stopWorkingOnImprovement() {
        improvementInProgress = null
        turnsToImprovement = 0
    }

    /** Sets tile improvement to pillaged (without prior checks for validity)
     *  and ensures that matching [UniqueType.CreatesOneImprovement] queued buildings are removed. */
    fun setPillaged() {
        if (!canPillageTile())
            return
        // http://well-of-souls.com/civ/civ5_improvements.html says that naval improvements are destroyed upon pillage
        //    and I can't find any other sources so I'll go with that
        if (!isLand) {
            removeImprovement()
            owningCity?.reassignPopulationDeferred()
            return
        }

        // Setting turnsToImprovement might interfere with UniqueType.CreatesOneImprovement
        improvementFunctions.removeCreatesOneImprovementMarker()
        improvementInProgress = null  // remove any in progress work as well
        turnsToImprovement = 0
        // if no Repair action, destroy improvements instead
        if (ruleset.tileImprovements[Constants.repair] == null) {
            if (canPillageTileImprovement())
                removeImprovement()
            else
                removeRoad()
        } else {
            // otherwise use pillage/repair systems
            if (canPillageTileImprovement())
                improvementIsPillaged = true
            else
                roadIsPillaged = true
        }

        owningCity?.reassignPopulationDeferred()
        if (owningCity != null)
            owningCity!!.civ.cache.updateCivResources()
    }

    fun isPillaged(): Boolean {
        return improvementIsPillaged || roadIsPillaged
    }

    fun setRepaired() {
        improvementInProgress = null
        turnsToImprovement = 0
        if (improvementIsPillaged)
            improvementIsPillaged = false
        else
            roadIsPillaged = false

        owningCity?.reassignPopulationDeferred()
    }


    /**
     * Assign a continent ID to this tile.
     *
     * Should only be set once at map generation.
     * @param continent Numeric ID >= 0
     * @throws Exception when tile already has a continent ID
     */
    fun setContinent(continent: Int) {
        if (this.continent != -1)
            throw Exception("Continent already assigned @ $position")
        this.continent = continent
    }

    /** Clear continent ID, for map editor */
    fun clearContinent() { continent = -1 }

    //endregion
}
