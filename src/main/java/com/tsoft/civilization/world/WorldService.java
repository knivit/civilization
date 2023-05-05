package com.tsoft.civilization.world;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.*;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Pair;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.event.*;
import com.tsoft.civilization.world.scenario.Scenario;
import com.tsoft.civilization.world.scenario.ScenarioApplyResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class WorldService {

    private final World world;

    private final CivilizationList civilizations = new CivilizationList();
    private final HashMap<Pair<Civilization>, CivilizationsRelations> relations = new HashMap<>();
    private final List<Year> history = new ArrayList<>();

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

        // Send an event to civilizations about the new one.
        // Clients need to update their maps to see the new civilization (settlers and warriors)
        sendEvent(NewCivilizationEvent.builder()
            .civilizationName(civilizationName)
            .build());

        // start the game automatically (if it is not started) for human player
        // if it is already started then start a new year for the civilization
        boolean started = false;
        if (!PlayerType.BOT.equals(playerType)) {
            started = world.startGame();
        }

        if (!started) {
            civilization.startYear(world.getYear());
        }

        return civilization;
    }

    public void sendEvent(Event event) {
        Objects.requireNonNull(event, "event can't be null");
        civilizations.forEach(e -> e.addEvent(event));
    }

    public Year getHistory(int stepNo) {
        if (stepNo < 0 || stepNo >= history.size()) {
            throw new IllegalArgumentException("stepNo must be between [0.." + history.size() + ")");
        }

        return history.get(stepNo);
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

    public Civilization getCivilizationById(String civilizationId) {
        return civilizations.getCivilizationById(civilizationId);
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

    public void startYear(Year year) {
        log.debug("A new year {} started", year.getYearLocalized());

        sendEvent(NewYearStartEvent.builder()
            .year(year.getYear())
            .build());

        if (year.isNewEra()) {
            log.debug("A new {} era started", year.getEraLocalized());

            sendEvent(NewEraStartedEvent.builder()
                .era(year.getEra())
                .build());
        }

        world.getTilesMap().startYear();

        for (Civilization civilization : civilizations) {
            civilization.startYear(year);
        }
    }

    // Do not call stopYear() for Civilizations
    // It'll be called in "next turn" action
    public void stopYear(Year year) {
        sendEvent(WorldStopYearEvent.builder()
            .year(year.getYear())
            .build()
        );

        history.add(year);
    }

    public CivilizationList getMovingCivilizations() {
        return new CivilizationList(civilizations.stream()
            .filter(civ -> !CivilizationMoveState.DONE.equals(civ.getCivilizationMoveState()))
            .collect(Collectors.toList()));
    }
}
