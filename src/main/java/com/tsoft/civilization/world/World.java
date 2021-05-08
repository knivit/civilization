package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.*;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.event.NewYearStartEvent;
import com.tsoft.civilization.world.event.WorldStopYearEvent;
import com.tsoft.civilization.world.scenario.Scenario;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class World {

    @Getter
    private final String id = UUID.randomUUID().toString();

    @Getter
    private Year year;

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

    private final List<Year> history = new ArrayList<>();
    private final WorldService worldService;
    private volatile boolean isYearStarted;

    public World(String name, TilesMap tilesMap, Climate climate) {
        this.name = name;
        this.tilesMap = tilesMap;
        this.climate = climate;

        year = new Year(-3000);
        view = new WorldView(this);

        worldService = new WorldService(this);
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

    // Find a location to place a Settlers
    public Point getSettlersStartLocation(Civilization civ) {
        List<Point> possibleLocations = worldService.getTilesToStartCivilization(tilesMap);
        return worldService.getCivilizationStartLocation(civ, possibleLocations);
    }

    public Civilization getCivilizationById(String civilizationId) {
        return worldService.getCivilizationById(civilizationId);
    }

    // Find out what Civilization have this tile, or null
    public Civilization getCivilizationOnTile(Point location) {
        return worldService.getCivilizationOnTile(location);
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

    public List<Year> getHistory() {
        return history;
    }

    public void startGame() {
        if (!isYearStarted) {
            startYear();
        }
    }

    public void startYear() {
        if (isYearStarted) {
            throw new IllegalStateException("This year already started !");
        }

        isYearStarted = true;

        history.add(year);
        tilesMap.startYear();
        worldService.startYear();

        sendEvent(NewYearStartEvent.builder()
            .year(year.getYear())
            .build());

        log.debug("A new year {} started", year.getYear());
    }

    // Callback on civilizations' "next turn" button
    public void onCivilizationMoved() {
        CivilizationList list = worldService.getMovingCivilizations();

        if (list.isEmpty()) {
            stopYear();

            // Start a new year !
            startYear();
        }
    }

    // Do not invoke this method directly; it is will be invoked in onCivilizationMoved()
    // when all Civilizations done their move
    private void stopYear() {
        if (!isYearStarted) {
            throw new IllegalStateException("This year already ended !");
        }

        isYearStarted = false;

        sendEvent(WorldStopYearEvent.builder()
            .year(year.getYear())
            .build()
        );

        year = year.nextYear();
    }
}
