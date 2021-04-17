package com.tsoft.civilization;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.scenario.Scenario;
import com.tsoft.civilization.world.scenario.ScenarioApplyResult;
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

    private final List<MockUnit> units = new ArrayList<>();
    private final List<MockCity> cities = new ArrayList<>();

    // All created objects (units, cities etc)
    private final Map<String, Object> objects = new HashMap<>();

    public MockScenario unit(String name, Point location, String classUuid) {
        MockUnit unit = new MockUnit(name, location, classUuid);
        units.add(unit);
        return this;
    }

    public MockScenario city(String name, Point location) {
        MockCity city = new MockCity(name, location);
        cities.add(city);
        return this;
    }

    public <T> T get(String name) {
        return Optional.of((T)objects.get(name)).orElseThrow();
    }

    public AbstractUnit unit(String name) {
        return Optional.of((AbstractUnit)objects.get(name)).orElseThrow();
    }

    public City city(String name) {
        return Optional.of((City)objects.get(name)).orElseThrow();
    }

    public Collection getAll() {
        return objects.values();
    }

    @Override
    public ScenarioApplyResult apply(Civilization civilization) {
        // Create all needed cities
        cities.forEach(c -> {
            // Create Settlers
            Settlers settlers = UnitFactory.newInstance(civilization, Settlers.CLASS_UUID);
            if (!civilization.units().addUnit(settlers, c.location)) {
                throw new IllegalStateException("Can't place Settlers at " + c.location + " to build a city");
            }

            // Build a city
            City city = civilization.createCity(settlers);
            objects.put(c.name, city);
        });

        // Create all needed units
        units.forEach(u -> {
            AbstractUnit unit = UnitFactory.newInstance(civilization, u.classUuid);
            if (!civilization.units().addUnit(unit, u.location)) {
                throw new IllegalStateException("Can't add unit " + u.name + " at " + u.location);
            }

            objects.put(u.name, unit);
        });

        return ScenarioApplyResult.SUCCESS;
    }
}
