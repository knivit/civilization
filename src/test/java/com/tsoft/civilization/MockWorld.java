package com.tsoft.civilization;

import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.web.state.Sessions;
import com.tsoft.civilization.web.state.Worlds;
import com.tsoft.civilization.world.World;

import java.util.UUID;

public class MockWorld extends World {

    public static MockWorld newSimpleWorld() {
        MockTilesMap simpleMap = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 3 ",
            "-+--------",
            "0|. . g . ",
            "1| . p g .",
            "2|. . g . ",
            "3| . . . .");
        return MockWorld.of(simpleMap);
    }

    public static MockWorld newWorldWithFeatures() {
        MockTilesMap simpleMap = new MockTilesMap(MapType.SIX_TILES, 3,
            " |0 1 2 3 ", " |0 1 2 3 ", " |0 1 2 3 ",
            "-+--------", "-+--------", "-+--------",
            "0|. . g . ", "0|. . h . ", "0|. . f . ",
            "1| . g g .", "1| . M f .", "1| . . . .",
            "2|. . g . ", "2|. . h . ", "2|. . . . ",
            "3| . . . .", "3| . . . .", "3| . . . .");
        return MockWorld.of(simpleMap);
    }

    public static MockWorld of(MockTilesMap mockTilesMap) {
        MockWorld world = new MockWorld(mockTilesMap.getMapType(), mockTilesMap.getWidth(), mockTilesMap.getHeight());

        Worlds.add(world);
        Sessions.findOrCreateNewAndSetAsCurrent(UUID.randomUUID().toString(), "localhost", "Unit Test");
        world.setTilesMap(mockTilesMap);

        return world;
    }

    private MockWorld(MapType mapType, int width, int height) {
        super(UUID.randomUUID().toString(), mapType, width, height);
        setMaxNumberOfCivilizations(8);
    }

    public MockTilesMap getMockTilesMap() {
        return (MockTilesMap)getTilesMap();
    }
}
