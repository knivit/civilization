package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.web.view.world.WorldView;
import com.tsoft.civilization.world.util.CivilizationCollection;
import com.tsoft.civilization.world.util.CivilizationList;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.util.UnmodifiableCivilizationList;

import java.util.*;

public class World {
    private String id;
    private Year year;
    private TilesMap tilesMap;
    private final WorldView worldView;

    // Filled on World creation
    private String name;
    private int maxNumberOfCivilizations;

    // use ArrayList as almost always we need to iterate through it
    private CivilizationCollection civilizations = new CivilizationList();
    private CivilizationCollection unmodifiableCivilizations = new UnmodifiableCivilizationList(civilizations);

    private HashMap<Pair, CivilizationsRelations> relations = new HashMap<>();
    private List<Year> years = new ArrayList<>();

    public World(MapType mapType, int width, int height) {
        id = UUID.randomUUID().toString();
        worldView = new WorldView(this);

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
        sendEvent(new Event(civilization, L10nWorld.NEW_CIVILIZATION_EVENT, Event.UPDATE_WORLD));
    }

    public CivilizationsRelations getCivilizationsRelations(Civilization c1, Civilization c2) {
        if (c1.equals(c2)) {
            return null;
        }

        Pair<Civilization> key = new Pair<>(c1, c2);
        CivilizationsRelations rel = relations.get(key);
        return rel;
    }

    public void setCivilizationsRelations(Civilization c1, Civilization c2, CivilizationsRelations rel) {
        Pair<Civilization> key = new Pair<>(c1, c2);
        relations.get(key).setState(rel);

        // send an Event about that to all civilizations
        switch (rel.getState()) {
            case CivilizationsRelations.WAR_STATE: {
                sendEvent(new Event(this, L10nWorld.DECLARE_WAR_EVENT, Event.UPDATE_STATUS_PANEL));
                break;
            }

            case CivilizationsRelations.FRIENDS_STATE: {
                sendEvent(new Event(this, L10nWorld.DECLARE_FRIENDS_EVENT, Event.UPDATE_STATUS_PANEL));
                break;
            }
        }
    }

    public boolean isWar(Civilization c1, Civilization c2) {
        if (c1 == null || c2 == null || c1.equals(c2)) return false;
        return getCivilizationsRelations(c1, c2).isWar();
    }

    // Find a location to place a Settlers
    public Point getCivilizationStartLocation() {
        // not less than 4 tiles from other civilizations tiles
        // location must be passable for the Settlers
        // there are must be 3 tiles of Earth around the location
        Set<Point> busyLocations = new HashSet<>();
        for (Civilization civilization : civilizations) {
            for (City city : civilization.getCities()) {
                busyLocations.addAll(city.getLocations());
            }

            for (AbstractUnit unit : civilization.getUnits()) {
                busyLocations.add(unit.getLocation());
            }
        }

        List<Point> possibleLocations = tilesMap.getTilesToStartCivilization();
        possibleLocations.removeAll(busyLocations);

        int n = (int)Math.round(Math.random() * (possibleLocations.size() - 1));
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

    public CityCollection getCitiesAtLocations(Collection<Point> locations) {
        return getCitiesAtLocations(locations, null);
    }

    public CityCollection getCitiesAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return civilizations.getCitiesAtLocations(locations, excludeCivilization);
    }

    public UnitCollection getUnitsAtLocation(Point location) {
        return getUnitsAtLocation(location, null);
    }

    public UnitCollection getUnitsAtLocation(Point location, Civilization excludeCivilization) {
        return civilizations.getUnitsAtLocation(location, excludeCivilization);
    }

    public UnitCollection getUnitsAtLocations(Collection<Point> locations) {
        return getUnitsAtLocations(locations, null);
    }

    public UnitCollection getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return civilizations.getUnitsAtLocations(locations, excludeCivilization);
    }

    public ArrayList<Point> getLocationsAround(Point location, int radius) {
        return getTilesMap().getLocationsAround(location, radius);
    }

    public WorldView getView() {
        return worldView;
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

    public CivilizationCollection getCivilizations() {
        return unmodifiableCivilizations;
    }

    public AbstractUnit getUnitById(String unitId) {
        return civilizations.getUnitById(unitId);
    }

    public City getCityById(String cityId) {
        return civilizations.getCityById(cityId);
    }

    public List<Civilization> getNotMovedHumanCivilizations() {
        List<Civilization> result = new ArrayList<>();
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

        for (Civilization civilization : civilizations) {
            civilization.step(year);
        }

        sendEvent(new Event(this, L10nWorld.NEW_YEAR_EVENT, Event.UPDATE_CONTROL_PANEL));
    }

    public List<Year> getYears() {
        return years;
    }
}
