package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.*;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Pair;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.event.Event;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class CivilizationService {
    private final World world;

    private final CivilizationList civilizations;
    private final HashMap<Pair<Civilization>, CivilizationsRelations> relations = new HashMap<>();

    public CivilizationService(World world) {
        this.world = world;

        civilizations = new CivilizationList();
    }

    public Civilization create(PlayerType playerType, L10n civilizationName) {
        if (civilizations.getCivilizationByName(civilizationName) != null) {
            return null;
        }

        Civilization civilization = CivilizationFactory.newInstance(civilizationName, world, playerType);

        if (!civilization.units().addFirstUnits()) {
            return null;
        }

        // set NEUTRAL state for this civilization with others
        for (Civilization otherCivilization : civilizations) {
            if (!civilization.equals(otherCivilization)) {
                Pair<Civilization> key = new Pair<>(civilization, otherCivilization);
                relations.put(key, CivilizationsRelations.neutral());
            }
        }

        civilizations.add(civilization);

        civilization.startYear();

        // send an event to civilizations about the new one
        // clients need to update their maps to see the new civilization (settlers and warriors)
        sendEvent(new Event(Event.UPDATE_WORLD, civilization, L10nWorld.NEW_CIVILIZATION_EVENT,
            civilization.getView().getLocalizedCivilizationName(), world.getYear()));

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
        relations.put(key, rel);

        // send an Event about that to all civilizations
        if (rel.isWar()) {
            sendEvent(new Event(Event.UPDATE_STATUS_PANEL, this, L10nWorld.DECLARE_WAR_EVENT, c1.getView().getLocalizedCivilizationName(), c2.getView().getLocalizedCivilizationName()));
        }

        if (rel.isFriends()) {
            sendEvent(new Event(Event.UPDATE_STATUS_PANEL, this, L10nWorld.DECLARE_FRIENDS_EVENT, c1.getView().getLocalizedCivilizationName(), c2.getView().getLocalizedCivilizationName()));
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
    // Rules:
    //   - not less than 4 tiles from other civilizations tiles
    //   - location must be passable for the Settlers
    //   - there are must be 3 tiles of Earth around the location
    public Point getCivilizationStartLocation(Civilization civ, List<Point> possibleLocations) {
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