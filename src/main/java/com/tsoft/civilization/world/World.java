package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Pair;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.CivilizationList;
import com.tsoft.civilization.world.event.Event;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class World {
    private final String id = UUID.randomUUID().toString();

    private Year year;
    private TilesMap tilesMap;
    private final WorldView VIEW;

    // Filled on World creation
    private String name;
    private int maxNumberOfCivilizations;

    // use ArrayList as almost always we need to iterate through it
    private CivilizationList civilizations = new CivilizationList();

    private HashMap<Pair<Civilization>, CivilizationsRelations> relations = new HashMap<>();
    private List<Year> years = new ArrayList<>();

    public World(MapType mapType, int width, int height) {
        VIEW = new WorldView(this);

        // Create a new year and add it to the history
        year = new Year(-3000);
        years.add(year);

        tilesMap = new TilesMap(mapType, width, height);
    }

    public String getId() {
        return id;
    }

    public Year getYear() {
        return year;
    }

    public TilesMap getTilesMap() {
        return tilesMap;
    }

    public void setTilesMap(TilesMap tilesMap) {
        this.tilesMap = tilesMap;
    }

    // Send an event to all civilizations
    public void sendEvent(Event event) {
        for (Civilization civilization : civilizations) {
            civilization.addEvent(event);
        }
    }

    public void addCivilization(Civilization civilization) {
        // set NEUTRAL state for this civilization with others
        for (Civilization otherCivilization : civilizations) {
            if (!civilization.equals(otherCivilization)) {
                Pair<Civilization> key = new Pair<>(civilization, otherCivilization);
                relations.put(key, CivilizationsRelations.NEUTRAL);
            }
        }

        civilizations.add(civilization);

        // send an event to civilizations about the new one
        // clients need to update their maps to see the new civilization (settlers and warriors)
        sendEvent(new Event(Event.UPDATE_WORLD, civilization, L10nWorld.NEW_CIVILIZATION_EVENT, civilization.getView().getLocalizedCivilizationName()));
    }

    public CivilizationsRelations getCivilizationsRelations(Civilization c1, Civilization c2) {
        if (c1.equals(c2)) {
            return null;
        }

        Pair<Civilization> key = new Pair<>(c1, c2);
        return relations.get(key);
    }

    public void setCivilizationsRelations(Civilization c1, Civilization c2, CivilizationsRelations rel) {
        Pair<Civilization> key = new Pair<>(c1, c2);
        relations.get(key).setState(rel);

        // send an Event about that to all civilizations
        switch (rel.getState()) {
            case CivilizationsRelations.WAR_STATE ->
                sendEvent(new Event(Event.UPDATE_STATUS_PANEL, this, L10nWorld.DECLARE_WAR_EVENT, c1.getView().getLocalizedCivilizationName(), c2.getView().getLocalizedCivilizationName()));

            case CivilizationsRelations.FRIENDS_STATE ->
                sendEvent(new Event(Event.UPDATE_STATUS_PANEL, this, L10nWorld.DECLARE_FRIENDS_EVENT, c1.getView().getLocalizedCivilizationName(), c2.getView().getLocalizedCivilizationName()));
        }
    }

    public boolean isWar(Civilization c1, Civilization c2) {
        if (c1 == null || c2 == null || c1.equals(c2)) return false;
        return getCivilizationsRelations(c1, c2).isWar();
    }

    // Find a location to place a Settlers
    public Point getCivilizationStartLocation(Civilization civ) {
        // not less than 4 tiles from other civilizations tiles
        // location must be passable for the Settlers
        // there are must be 3 tiles of Earth around the location
        Set<Point> busyLocations = new HashSet<>();
        for (Civilization civilization : civilizations) {
            // skip the target civilization
            if (civilization.equals(civ)) {
                continue;
            }

            // exclude cities
            for (City city : civilization.getCities()) {
                busyLocations.addAll(city.getLocations());
            }

            // exclude units
            for (AbstractUnit unit : civilization.getUnits()) {
                busyLocations.add(unit.getLocation());
            }
        }

        List<Point> possibleLocations = tilesMap.getTilesToStartCivilization();
        possibleLocations.removeAll(busyLocations);
        if (possibleLocations.isEmpty()) {
            return null;
        }

        int n = ThreadLocalRandom.current().nextInt(possibleLocations.size());
        return possibleLocations.get(n);
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

    public UnitList<?> getUnitsAtLocation(Point location) {
        return getUnitsAtLocation(location, null);
    }

    public UnitList<?> getUnitsAtLocation(Point location, Civilization excludeCivilization) {
        return civilizations.getUnitsAtLocation(location, excludeCivilization);
    }

    public UnitList<?> getUnitsAtLocations(Collection<Point> locations) {
        return getUnitsAtLocations(locations, null);
    }

    public UnitList<?> getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return civilizations.getUnitsAtLocations(locations, excludeCivilization);
    }

    public ArrayList<Point> getLocationsAround(Point location, int radius) {
        return getTilesMap().getLocationsAround(location, radius);
    }

    public WorldView getView() {
        return VIEW;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxNumberOfCivilizations() {
        return maxNumberOfCivilizations;
    }

    public void setMaxNumberOfCivilizations(int maxNumberOfCivilizations) {
        this.maxNumberOfCivilizations = maxNumberOfCivilizations;
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

    public CivilizationList getNotMovedHumanCivilizations() {
        CivilizationList result = new CivilizationList();
        for (Civilization civilization : civilizations) {
            if (!civilization.isMoved() && !civilization.isArtificialIntelligence()) {
                result.add(civilization);
            }
        }
        return result;
    }

    public void nextMove() {
        // do not step into the next year
        // if some non-artificial civilizations didn't move
        if (!getNotMovedHumanCivilizations().isEmpty()) {
            return;
        }

        // after all human-managed civilization did move,
        // move AI-managed civilizations
        for (Civilization civilization : civilizations) {
            if (civilization.isArtificialIntelligence()) {
                civilization.nextMove();
            }
        }

        step();
    }

    public void step() {
        // start a new year
        year = year.step();

        // add it to the history
        years.add(year);
        sendEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.NEW_YEAR_START_EVENT, year.getValue()));

        for (Civilization civilization : civilizations) {
            civilization.step(year);
        }

        sendEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.NEW_YEAR_COMPLETE_EVENT, year.getValue()));
    }

    public List<Year> getYears() {
        return years;
    }
}
