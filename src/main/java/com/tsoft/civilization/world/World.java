package com.tsoft.civilization.world;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.*;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.CityList;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.scenario.Scenario;
import com.tsoft.civilization.world.tile.WorldTileService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class World {

    @Getter
    private final String id = UUID.randomUUID().toString();

    @Getter
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Getter
    private Year year = Year.NOT_STARTED;

    @Getter
    private final Climate climate;

    @Getter
    private final TilesMap tilesMap;

    @Getter
    private final WorldView view;

    @Getter
    private final String name;

    @Getter @Setter
    private int maxNumberOfCivilizations;

    @Getter @Setter
    private DifficultyLevel difficultyLevel;

    @Getter
    private final WorldTileService tileService;

    private final WorldService worldService;

    public World(String name, TilesMap tilesMap, Climate climate) {
        this.name = name;
        this.tilesMap = tilesMap;
        this.climate = climate;

        view = new WorldView(this);

        worldService = new WorldService(this);
        tileService = new WorldTileService(this);
    }

    // Send an event to all civilizations
    public void sendEvent(Event event) {
        worldService.sendEvent(event);
    }

    // Returns NULL when a civilization can not be created (it is already exists etc)
    public Civilization createCivilization(PlayerType playerType, L10n civilizationName, Scenario scenario) {
        return worldService.createCivilization(playerType, civilizationName, scenario);
    }

    // Returns NULL when relations can not be found
    public CivilizationsRelations getCivilizationsRelations(Civilization c1, Civilization c2) {
        return worldService.getRelations(c1, c2);
    }

    public void setCivilizationsRelations(Civilization c1, Civilization c2, CivilizationsRelations rel) {
        worldService.setRelations(c1, c2, rel);
    }

    public boolean isWar(Civilization c1, Civilization c2) {
        return worldService.isWar(c1, c2);
    }

    public Civilization getCivilizationById(String civilizationId) {
        return worldService.getCivilizationById(civilizationId);
    }

    // Only one city may be on a tile
    public City getCityAtLocation(Point location) {
        return worldService.getCityAtLocation(location);
    }

    public CityList getCitiesAtLocations(Collection<Point> locations) {
        return getCitiesAtLocations(locations, null);
    }

    public CityList getCitiesAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return worldService.getCitiesAtLocations(locations, excludeCivilization);
    }

    public UnitList getUnitsAtLocation(Point location) {
        return getUnitsAtLocation(location, null);
    }

    public UnitList getUnitsAtLocation(Point location, Civilization excludeCivilization) {
        return worldService.getUnitsAtLocation(location, excludeCivilization);
    }

    public UnitList getUnitsAtLocations(Collection<Point> locations) {
        return getUnitsAtLocations(locations, null);
    }

    public UnitList getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return worldService.getUnitsAtLocations(locations, excludeCivilization);
    }

    public List<Point> getLocationsAround(Point location, int radius) {
        return getTilesMap().getLocationsAround(location, radius);
    }

    public CivilizationList getCivilizations() {
        return worldService.getCivilizations();
    }

    public AbstractUnit getUnitById(String unitId) {
        return worldService.getUnitById(unitId);
    }

    public City getCityById(String cityId) {
        return worldService.getCityById(cityId);
    }

    public Year getHistory(int stepNo) {
        return worldService.getHistory(stepNo);
    }

    // Returns false if the game already started
    public synchronized boolean startGame() {
        if (Year.NOT_STARTED.equals(year)) {
            year = new Year(-3000);
            worldService.startYear(year);
            return true;
        }

        return false;
    }

    // Callback on civilizations "next turn" button
    public synchronized void onCivilizationMoved() {
        CivilizationList list = worldService.getMovingCivilizations();

        if (list.isEmpty()) {
            nextYear();
        }
    }

    private void nextYear() {
        worldService.stopYear(year);

        year = year.nextYear();

        worldService.startYear(year);
    }
}
