package com.tsoft.civilization;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.civil.greatartist.GreatArtist;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.scenario.Scenario;
import com.tsoft.civilization.world.scenario.ScenarioApplyResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

public class MockScenario implements Scenario {

    @RequiredArgsConstructor
    private static class MockUnit {
        private final String name;
        private final Point location;
        private final String classUuid;
    }

    @RequiredArgsConstructor
    private static class MockCity {
        private final String name;
        private final Point location;
    }

    // Scenario objects
    private final List<MockUnit> units = new ArrayList<>();
    private final List<MockCity> cities = new ArrayList<>();

    // Created objects (units, cities etc)
    @Getter
    private final Map<String, Object> objects = new HashMap<>();

    public MockScenario warriors(String name, Point location) {
        return unit(name, location, Warriors.CLASS_UUID);
    }

    public MockScenario archers(String name, Point location) {
        return unit(name, location, Archers.CLASS_UUID);
    }

    public MockScenario settlers(String name, Point location) {
        return unit(name, location, Settlers.CLASS_UUID);
    }

    public MockScenario workers(String name, Point location) {
        return unit(name, location, Workers.CLASS_UUID);
    }

    public MockScenario greatArtist(String name, Point location) {
        return unit(name, location, GreatArtist.CLASS_UUID);
    }

    public MockScenario city(String name, Point location) {
        MockCity city = new MockCity(name, location);
        cities.add(city);
        return this;
    }

    public MockScenario unit(String name, Point location, String classUuid) {
        MockUnit unit = new MockUnit(name, location, classUuid);
        units.add(unit);
        return this;
    }

    @Override
    public ScenarioApplyResult apply(Civilization civilization) {
        // Create cities
        cities.forEach(c -> {
            // Create Settlers
            Settlers settlers = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
            if (!civilization.getUnitService().addUnit(settlers, c.location)) {
                throw new IllegalStateException("Can't place Settlers at " + c.location + " to build a city");
            }

            // Build a city
            City city = civilization.createCity(settlers);
            putObject(c.name, city);
        });

        // Create units
        units.forEach(u -> {
            AbstractUnit unit = UnitFactory.newInstance(civilization, u.classUuid);
            if (!civilization.getUnitService().addUnit(unit, u.location)) {
                throw new IllegalStateException("Can't add unit " + u.name + " at " + u.location);
            }

            putObject(u.name, unit);
        });

        // set NEUTRAL state for this civilization with others
        World world = civilization.getWorld();
        for (Civilization otherCivilization : world.getCivilizations()) {
            if (!civilization.equals(otherCivilization)) {
                world.setCivilizationsRelations(civilization, otherCivilization, CivilizationsRelations.neutral());
            }
        }

        return ScenarioApplyResult.SUCCESS;
    }

    private void putObject(String name, Object object) {
        if (objects.get(name) != null) {
            throw new IllegalStateException("Object '" + name + "' already exists");
        }

        objects.put(name, object);
    }
}
