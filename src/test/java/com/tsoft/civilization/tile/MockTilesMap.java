package com.tsoft.civilization.tile;

import com.tsoft.civilization.world.MapConfiguration;
import com.tsoft.civilization.world.MapSize;
import com.tsoft.civilization.world.generator.map.MapGenerator;

public class MockTilesMap {

    private static final MapGenerator MAP_GENERATOR = new MapGenerator();

    public MockTilesMap() { }

    /** To use with one layer (i.e. tiles only, without features) */
    public static TilesMap of(String ... asciiLines) {
        return MockTilesMap.of(1, asciiLines);
    }

    /** To use with features on the tiles */
    public static TilesMap of(int layerCount, String ... asciiLines) {
        TilesMap map = MAP_GENERATOR.create(MapSize.STANDARD, layerCount, asciiLines);
        map.setMapConfiguration(MapConfiguration.EARTH);
        return map;
    }
}
