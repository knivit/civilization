package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.*;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Pair;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.event.DeclareFriendsEvent;
import com.tsoft.civilization.world.event.DeclareWarEvent;
import com.tsoft.civilization.world.event.NewCivilizationEvent;
import com.tsoft.civilization.world.scenario.Scenario;
import com.tsoft.civilization.world.scenario.ScenarioApplyResult;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
public class WorldService {
    private final World world;

    private final CivilizationList civilizations;
    private final HashMap<Pair<Civilization>, CivilizationsRelations> relations = new HashMap<>();

    public WorldService(World world) {
        this.world = world;

        civilizations = new CivilizationList();
    }

    public Civilization createCivilization(PlayerType playerType, L10n civilizationName, Scenario scenario) {
        if (civilizations.getCivilizationByName(civilizationName) != null) {
            log.warn("Civilization '{}' already exists", civilizationName);
            return null;
        }

        Civilization civilization = CivilizationFactory.newInstance(civilizationName, world, playerType);

        if (ScenarioApplyResult.FAIL.equals(scenario.apply(civilization))) {
            log.warn("Can't apply a scenario on Civilization '{}'", civilizationName);
            return null;
        }

        civilizations.add(civilization);

        // send an event to civilizations about the new one
        // clients need to update their maps to see the new civilization (settlers and warriors)
        sendEvent(NewCivilizationEvent.builder()
            .civilizationName(civilizationName)
            .build());

        return civilization;
    }

    public void sendEvent(Event event) {
        Objects.requireNonNull(event, "event can't be null");
        civilizations.forEach(e -> e.addEvent(event));
    }

    // Returns NULL when relations can not be found
    public CivilizationsRelations getRelations(Civilization c1, Civilization c2) {
        if (c1 == null || c1.equals(c2)) {
            return null;
        }

        Pair<Civilization> key = new Pair<>(c1, c2);
        return relations.get(key);
    }

    public void setRelations(Civilization c1, Civilization c2, CivilizationsRelations rel) {
        Objects.requireNonNull(c1, "c1 must not be null");
        Objects.requireNonNull(c2, "c2 must not be null");
        Objects.requireNonNull(rel, "rel must not be null");

        Pair<Civilization> key = new Pair<>(c1, c2);
        CivilizationsRelations curr = relations.get(key);
        if (rel.equals(curr)) {
            return;
        }

        relations.put(key, rel);

        // send an Event about that to all civilizations
        if (rel.isWar()) {
            sendEvent(DeclareWarEvent.builder()
                .civilizationName1(c1.getName())
                .civilizationName2(c2.getName())
                .build());
        }

        if (rel.isFriends()) {
            sendEvent(DeclareFriendsEvent.builder()
                .civilizationName1(c1.getName())
                .civilizationName2(c2.getName())
                .build());
        }
    }

    public boolean isWar(Civilization c1, Civilization c2) {
        if (c1 == null || c2 == null || c1.equals(c2)) {
            return false;
        }

        CivilizationsRelations relations = getRelations(c1, c2);
        if (relations == null) {
            return false;
        }

        return relations.isWar();
    }

    // Find a location to place a Settlers
    public Point getSettlersStartLocation(Civilization civ) {
        List<Point> possibleLocations = getTilesToStartCivilization();
        return getCivilizationStartLocation(civ, possibleLocations);
    }

    private List<Point> getTilesToStartCivilization() {
        return world.getTilesMap().tiles()
            .filter(AbstractTile::isCanBuildCity)
            .map(AbstractTile::getLocation)
            .collect(Collectors.toList());
    }

    // Find a location to place a Settlers
    // Rules:
    //   - not less than 4 tiles from other civilizations tiles
    //   - location must be passable for the Settlers
    //   - there are must be 3 tiles of Earth around the location
    private Point getCivilizationStartLocation(Civilization civ, List<Point> possibleLocations) {
        Set<Point> busyLocations = new HashSet<>();

        for (Civilization civilization : civilizations) {
            // skip the target civilization
            if (civilization.equals(civ)) {
                continue;
            }

            // exclude cities
            for (City city : civilization.getCityService().getCities()) {
                busyLocations.addAll(city.getTileService().getLocations());
            }

            // exclude units and locations around them as far as 4 tiles
            for (AbstractUnit unit : civilization.getUnitService().getUnits()) {
                busyLocations.add(unit.getLocation());
                busyLocations.addAll(world.getLocationsAround(unit.getLocation(), 4));
            }
        }

        possibleLocations.removeAll(busyLocations);
        if (possibleLocations.isEmpty()) {
            return null;
        }

        // must be 3 tiles of Earth around
        possibleLocations = getLocationsWithEarthAround(possibleLocations, 3);
        if (possibleLocations.isEmpty()) {
            return null;
        }

        int n = ThreadLocalRandom.current().nextInt(possibleLocations.size());
        return possibleLocations.get(n);
    }

    private List<Point> getLocationsWithEarthAround(List<Point> possibleLocations, int radius) {
        Set<Point> busyLocations = new HashSet<>();

        int numberOfLocationsAround = world.getLocationsAround(possibleLocations.get(0), radius).size();
        int minRadiusWithEarthAround = numberOfLocationsAround / 2;

        for (Point location : possibleLocations) {
            List<AbstractTile> tilesAround = world.getLocationsAround(location, radius).stream()
                .map(e -> world.getTilesMap().getTile(e))
                .filter(AbstractTile::isCanBuildCity)
                .collect(Collectors.toList());

            if (tilesAround.size() < minRadiusWithEarthAround) {
                busyLocations.add(location);
            }
        }

        possibleLocations.removeAll(busyLocations);
        return possibleLocations;
    }

    public Civilization getCivilizationById(String civilizationId) {
        return civilizations.getCivilizationById(civilizationId);
    }

    // Find out what Civilization have this tile, or null
    public Civilization getCivilizationOnTile(Point location) {
        return civilizations.getCivilizationOnTile(location);
    }

    // Only one city may be on a tile
    public City getCityAtLocation(Point location) {
        return civilizations.getCityAtLocation(location);
    }

    public CityList getCitiesAtLocations(Collection<Point> locations) {
        return getCitiesAtLocations(locations, null);
    }

    public CityList getCitiesAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return civilizations.getCitiesAtLocations(locations, excludeCivilization);
    }

    public UnitList getUnitsAtLocation(Point location) {
        return getUnitsAtLocation(location, null);
    }

    public UnitList getUnitsAtLocation(Point location, Civilization excludeCivilization) {
        return civilizations.getUnitsAtLocation(location, excludeCivilization);
    }

    public UnitList getUnitsAtLocations(Collection<Point> locations) {
        return getUnitsAtLocations(locations, null);
    }

    public UnitList getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return civilizations.getUnitsAtLocations(locations, excludeCivilization);
    }

    public CivilizationList getCivilizations() {
        return civilizations.unmodifiableList();
    }

    public AbstractUnit getUnitById(String unitId) {
        return civilizations.getUnitById(unitId);
    }

    public City getCityById(String cityId) {
        return civilizations.getCityById(cityId);
    }

    public void startYear() {
        civilizations.forEach(Civilization::startYear);
    }

    public CivilizationList getMovingCivilizations() {
        return new CivilizationList(civilizations.stream()
            .filter(civ -> !MoveState.DONE.equals(civ.getMoveState()))
            .collect(Collectors.toList()));
    }
}
