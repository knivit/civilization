package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.civilization.MoveState;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.tile.TileService;
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
    private final TilesMap tilesMap;
    private final WorldView view;

    // Filled on World creation
    private final String name;
    private int maxNumberOfCivilizations;

    // use ArrayList as almost always we need to iterate through it
    private final CivilizationList civilizations = new CivilizationList();

    private final HashMap<Pair<Civilization>, CivilizationsRelations> relations = new HashMap<>();
    private final List<Year> years = new ArrayList<>();

    private final TileService tileService = new TileService();

    public World(String name, TilesMap tilesMap) {
        this.name = name;
        year = new Year(-3000);
        view = new WorldView(this);
        this.tilesMap = tilesMap;
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

    // Send an event to all civilizations
    public void sendEvent(Event event) {
        Objects.requireNonNull(event, "event can't be null");
        civilizations.forEach(e -> e.addEvent(event));
    }

    // Returns NULL when a civilization can not be created (it is already exists etc)
    public Civilization createCivilization(L10n civilizationName) {
        if (civilizations.getCivilizationByName(civilizationName) != null) {
            return null;
        }

        Civilization civilization = new Civilization(this, civilizationName);

        // set NEUTRAL state for this civilization with others
        for (Civilization otherCivilization : civilizations) {
            if (!civilization.equals(otherCivilization)) {
                Pair<Civilization> key = new Pair<>(civilization, otherCivilization);
                relations.put(key, CivilizationsRelations.NEUTRAL);
            }
        }

        civilization.setAi(true);

        civilizations.add(civilization);

        civilization.startYear();

        // send an event to civilizations about the new one
        // clients need to update their maps to see the new civilization (settlers and warriors)
        sendEvent(new Event(Event.UPDATE_WORLD, civilization, L10nWorld.NEW_CIVILIZATION_EVENT,
            civilization.getView().getLocalizedCivilizationName(), year));

        return civilization;
    }

    // Returns NULL when relations can not be found
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
        if (c1 == null || c2 == null || c1.equals(c2)) {
            return false;
        }

        CivilizationsRelations relations = getCivilizationsRelations(c1, c2);
        if (relations == null) {
            return false;
        }

        return relations.isWar();
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
            for (City city : civilization.cities().getCities()) {
                busyLocations.addAll(city.getLocations());
            }

            // exclude units
            for (AbstractUnit unit : civilization.units().getUnits()) {
                busyLocations.add(unit.getLocation());
            }
        }

        List<Point> possibleLocations = tileService.getTilesToStartCivilization(tilesMap);
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

    public List<Point> getLocationsAround(Point location, int radius) {
        return getTilesMap().getLocationsAround(location, radius);
    }

    public WorldView getView() {
        return view;
    }

    public String getName() {
        return name;
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

    public List<Year> getYears() {
        return years;
    }

    /**
     *
     * Движение мира
     *
     * 1. Создается мир (пустой, без цивилизаций, в стартовый год)
     * 2. Присоединяются игроки (управляемые человеком или AI) и создаются их цивилизации (JoinWorldAction)
     * 3. Все человеческие цивилизации ходят
     *    - игроки делают ходы
     *    - игроки нажимают "Next Turn" - цивилизация переводится в состояние "DONE" (NextTurnAction)
     *    - цивилизация вызывает метод World.onCivilizationMoved()
     *    - когда все человеческие цивилизации перешли в "DONE", переход к 4)
     * 4. Год завершается
     *    - все AI-цивилизации по очереди делают ходы
     *    - все (управляемые человеком или AI) цивилизации строятся, популяция меняется, рассчитывается supply etc
     * 5. Стартует новый год
     *    - меняется ландшафт (глубина океанов etc)
     *    - цивилизации переводятся в состояние "IN PROGRESS"
     *    - каждая цивилизация подготавливает свое состояние к началу нового года
     * 6. Переход к 3)
    */

    // Start a new year
    public void startYear() {
        years.add(year);
        tilesMap.startYear();
        civilizations.forEach(Civilization::startYear);

        // add it to the history
        sendEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.NEW_YEAR_START_EVENT, year.getValue()));
    }

    public void onCivilizationMoved(Civilization civilization) {
        if (civilization.isAi()) {
            return;
        }

        Optional<Civilization> humanNotMoved = civilizations.stream()
            .filter(c -> !c.isAi())
            .filter(c -> MoveState.DONE != c.getMoveState())
            .findAny();

        if (humanNotMoved.isEmpty()) {
            stopYear();

            // Start a new year !
            startYear();
        }
    }

    public void stopYear() {
        // Move AI civilizations
        civilizations.stream()
            .filter(Civilization::isAi)
            .forEach(Civilization::stopYear);

        sendEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.NEW_YEAR_COMPLETE_EVENT, year.getValue()));

        year = year.nextYear();
    }
}
