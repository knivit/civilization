package com.tsoft.civilization.world.generator.earth;

import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.world.Climate;
import com.tsoft.civilization.world.MapSize;
import com.tsoft.civilization.world.generator.WorldGenerator;
import com.tsoft.civilization.world.generator.MapGenerator;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.world.MapSize.DUEL;

public class EarthWorldGenerator implements WorldGenerator {

    private static final MapGenerator MAP_GENERATOR = new MapGenerator();

    private static final Map<MapSize, String[]> MAPS = new HashMap<>();

    static {
        MAPS.put(DUEL, EarthDuelMap.MAP);
    }

    @Override
    public TilesMap generate(MapSize mapSize, Climate climate) {
        String[] map = MAPS.get(mapSize);
        return MAP_GENERATOR.create(mapSize, 1, map);
    }
}
