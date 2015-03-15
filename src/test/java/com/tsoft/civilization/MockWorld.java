package com.tsoft.civilization;

import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.world.World;

public class MockWorld extends World {
    public static MockWorld newSimpleWorld() {
        MockTilesMap simpleMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 3 ",
                "-+--------",
                "0|. . g . ",
                "1| . M g .",
                "2|. . g . ",
                "3| . . . .");
        return new MockWorld(simpleMap);
    }

    public static MockWorld newWorldWithFeatures() {
        MockTilesMap simpleMap = new MockTilesMap(MapType.SIX_TILES, 3,
                " |0 1 2 3 ", " |0 1 2 3 ", " |0 1 2 3 ",
                "-+--------", "-+--------", "-+--------",
                "0|. . g . ", "0|. . h . ", "0|. . f . ",
                "1| . M g .", "1| . . f .", "1| . . . .",
                "2|. . g . ", "2|. . h . ", "2|. . . . ",
                "3| . . . .", "3| . . . .", "3| . . . .");
        return new MockWorld(simpleMap);
    }

    public MockWorld(MapType mapType, int width, int height) {
        super(mapType, width, height);
    }

    public MockWorld(MockTilesMap mockTilesMap) {
        super(mockTilesMap.getMapType(), mockTilesMap.getWidth(), mockTilesMap.getHeight());
        setMockTilesMap(mockTilesMap);
    }

    public void setMockTilesMap(MockTilesMap mockTilesMap) {
      setTilesMap(mockTilesMap);
    }

    public MockTilesMap getMockTilesMap() {
        return (MockTilesMap)getTilesMap();
    }
}
