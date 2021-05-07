package com.tsoft.civilization.world.generator;

import com.tsoft.civilization.world.MapConfiguration;
import com.tsoft.civilization.world.generator.earth.EarthWorldGenerator;
import com.tsoft.civilization.world.generator.onecontinent.OneContinentWorldGenerator;

import java.util.*;

import static com.tsoft.civilization.world.MapConfiguration.CONTINENTS;
import static com.tsoft.civilization.world.MapConfiguration.EARTH;

public class WorldGeneratorFactory {

    private static final Map<MapConfiguration, WorldGenerator> GENERATORS = new HashMap<>();

    static {
        GENERATORS.put(EARTH, new EarthWorldGenerator());
        GENERATORS.put(CONTINENTS, new OneContinentWorldGenerator());
    };

    public static WorldGenerator getGenerator(MapConfiguration conf)  {
        WorldGenerator generator = GENERATORS.get(conf);
        return (generator == null) ? GENERATORS.get(CONTINENTS) : generator;
    }
}
