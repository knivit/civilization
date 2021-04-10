package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.*;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.event.Event;

import java.util.*;

public class World {
    private final String id = UUID.randomUUID().toString();

    private Year year;
    private final TilesMap tilesMap;
    private final WorldView view;

    // Filled on World creation
    private final String name;
    private int maxNumberOfCivilizations;

    private final List<Year> years = new ArrayList<>();
    private final CivilizationService civilizationService;
    private final TileService tileService = new TileService();

    public World(String name, TilesMap tilesMap) {
        this.name = name;
        year = new Year(-3000);
        view = new WorldView(this);
        this.tilesMap = tilesMap;

        civilizationService = new CivilizationService(this);
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
        civilizationService.sendEvent(event);
    }

    // Returns NULL when a civilization can not be created (it is already exists etc)
    public Civilization createCivilization(PlayerType playerType, L10n civilizationName) {
        return civilizationService.create(playerType, civilizationName);
    }

    // Returns NULL when relations can not be found
    public CivilizationsRelations getCivilizationsRelations(Civilization c1, Civilization c2) {
        return civilizationService.getRelations(c1, c2);
    }

    public void setCivilizationsRelations(Civilization c1, Civilization c2, CivilizationsRelations rel) {
        civilizationService.setRelations(c1, c2, rel);
    }

    public boolean isWar(Civilization c1, Civilization c2) {
        return civilizationService.isWar(c1, c2);
    }

    // Find a location to place a Settlers
    public Point getCivilizationStartLocation(Civilization civ) {
        List<Point> possibleLocations = tileService.getTilesToStartCivilization(tilesMap);
        return civilizationService.getCivilizationStartLocation(civ, possibleLocations);
    }

    public Civilization getCivilizationById(String civilizationId) {
        return civilizationService.getCivilizationById(civilizationId);
    }

    // Find out what Civilization have this tile, or null
    public Civilization getCivilizationOnTile(Point location) {
        return civilizationService.getCivilizationOnTile(location);
    }

    // Only one city may be on a tile
    public City getCityAtLocation(Point location) {
        return civilizationService.getCityAtLocation(location);
    }

    public CityList getCitiesAtLocations(Collection<Point> locations) {
        return getCitiesAtLocations(locations, null);
    }

    public CityList getCitiesAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return civilizationService.getCitiesAtLocations(locations, excludeCivilization);
    }

    public UnitList getUnitsAtLocation(Point location) {
        return getUnitsAtLocation(location, null);
    }

    public UnitList getUnitsAtLocation(Point location, Civilization excludeCivilization) {
        return civilizationService.getUnitsAtLocation(location, excludeCivilization);
    }

    public UnitList getUnitsAtLocations(Collection<Point> locations) {
        return getUnitsAtLocations(locations, null);
    }

    public UnitList getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return civilizationService.getUnitsAtLocations(locations, excludeCivilization);
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
        return civilizationService.getCivilizations();
    }

    public AbstractUnit getUnitById(String unitId) {
        return civilizationService.getUnitById(unitId);
    }

    public City getCityById(String cityId) {
        return civilizationService.getCityById(cityId);
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
        civilizationService.startYear();

        // add it to the history
        sendEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.NEW_YEAR_START_EVENT, year.getYear()));
    }

    public void onCivilizationMoved() {
        CivilizationList list = civilizationService.getMovingCivilizations();

        if (list.isEmpty()) {
            stopYear();

            // Start a new year !
            startYear();
        }
    }

    public void stopYear() {
        sendEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.NEW_YEAR_COMPLETE_EVENT, year.getYear()));

        year = year.nextYear();
    }
}
