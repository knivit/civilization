package com.tsoft.civilization;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.Climate;
import com.tsoft.civilization.world.DifficultyLevel;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.scenario.Scenario;

import java.util.*;

import static com.tsoft.civilization.civilization.L10nCivilization.CIVILIZATIONS;

public class MockWorld extends World {

    public static final TilesMap SIMPLE_TILES_MAP = MockTilesMap.of(
        " |0 1 2 3 ",
        "-+--------",
        "0|. . g . ",
        "1| . p g .",
        "2|. . g . ",
        "3| . . . .");

    public static final TilesMap TILES_MAP_WITH_FEATURES = MockTilesMap.of(3,
        " |0 1 2 3 ", " |0 1 2 3 ", " |0 1 2 3 ",
        "-+--------", "-+--------", "-+--------",
        "0|. . g . ", "0|. . h . ", "0|. . f . ",
        "1| . g g .", "1| . M f .", "1| . . . .",
        "2|. . g . ", "2|. . h . ", "2|. . . . ",
        "3| . . . .", "3| . . . .", "3| . . . .");

    public static final TilesMap GRASSLAND_MAP_10x10 = MockTilesMap.of(
        " |0 1 2 3 4 5 6 7 8 9 ",
        "-+--------------------",
        "0|g g g g g g g g g g ",
        "1| g g g g g g g g g g",
        "2|g g g g g g g g g g ",
        "3| g g g g g g g g g g",
        "4|g g g g g g g g g g ",
        "5| g g g g g g g g g g",
        "6|g g g g g g g g g g ",
        "7| g g g g g g g g g g",
        "8|g g g g g g g g g g ",
        "9| g g g g g g g g g g");

    // Created objects (units, cities etc) in Civilizations' scenarios
    private final Map<String, Object> objects = new HashMap<>();

    public static MockWorld newSimpleWorld() {
        return MockWorld.of(SIMPLE_TILES_MAP);
    }

    public static MockWorld newWorldWithFeatures() {
        return MockWorld.of(TILES_MAP_WITH_FEATURES);
    }

    public static MockWorld of(TilesMap tilesMap) {
        MockWorld world = new MockWorld(tilesMap);

        Worlds.clearAll();
        Worlds.add(world);

        Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        return world;
    }

    private MockWorld(TilesMap tilesMap) {
        super(UUID.randomUUID().toString(), tilesMap, Climate.NORMAL);

        setMaxNumberOfCivilizations(CIVILIZATIONS.size());
        setDifficultyLevel(DifficultyLevel.PRINCE);
    }

    public Civilization createCivilization(L10n civilizationName, Scenario scenario) {
        return super.createCivilization(PlayerType.HUMAN, civilizationName, scenario);
    }

    public Civilization createCivilization(L10n civilizationName, MockScenario scenario) {
        Civilization civilization = super.createCivilization(PlayerType.HUMAN, civilizationName, scenario);

        scenario.getObjects().forEach((name, obj) -> {
            if (objects.get(name) != null) {
                throw new IllegalStateException("Object '" + name + "' already exists");
            }
            objects.put(name, obj);
        });

        return civilization;
    }

    public void nextYear() {
        getCivilizations().forEach(Civilization::stopYear);
    }

    public Point location(String name) {
        Object obj = Optional.of(objects.get(name)).orElseThrow();
        if (obj instanceof AbstractUnit) {
            return ((AbstractUnit)obj).getLocation();
        }
        if (obj instanceof City) {
            return ((City)obj).getLocation();
        }
        throw new IllegalStateException("Object '" + name + "' doesn't have a location");
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
}
