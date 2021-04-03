package com.tsoft.civilization;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.PlayerType;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.World;

import java.util.UUID;

import static com.tsoft.civilization.civilization.L10nCivilization.CIVILIZATIONS;

public class MockWorld extends World {

    public static final MockTilesMap SIMPLE_TILES_MAP = new MockTilesMap(MapType.SIX_TILES,
        " |0 1 2 3 ",
        "-+--------",
        "0|. . g . ",
        "1| . p g .",
        "2|. . g . ",
        "3| . . . .");

    public static final MockTilesMap TILES_MAP_WITH_FEATURES = new MockTilesMap(MapType.SIX_TILES, 3,
        " |0 1 2 3 ", " |0 1 2 3 ", " |0 1 2 3 ",
        "-+--------", "-+--------", "-+--------",
        "0|. . g . ", "0|. . h . ", "0|. . f . ",
        "1| . g g .", "1| . M f .", "1| . . . .",
        "2|. . g . ", "2|. . h . ", "2|. . . . ",
        "3| . . . .", "3| . . . .", "3| . . . .");

    public static MockWorld newSimpleWorld() {
        return MockWorld.of(SIMPLE_TILES_MAP);
    }

    public static MockWorld newWorldWithFeatures() {
        return MockWorld.of(TILES_MAP_WITH_FEATURES);
    }

    public static MockWorld of(MockTilesMap mockTilesMap) {
        MockWorld world = new MockWorld(mockTilesMap);

        Worlds.clearAll();
        Worlds.add(world);

        Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");

        return world;
    }

    private MockWorld(MockTilesMap mockTilesMap) {
        super(UUID.randomUUID().toString(), mockTilesMap);
        setMaxNumberOfCivilizations(CIVILIZATIONS.size());
        startYear();
    }

    public Civilization createCivilization(L10n civilizationName) {
        return super.createCivilization(PlayerType.BOT, civilizationName);
    }

    public void move() {
        stopYear();
    }
}
