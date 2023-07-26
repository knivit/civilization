package com.tsoft.civilization.improvement;

import com.tsoft.civilization.improvement.catalog.farm.Farm;
import com.tsoft.civilization.improvement.catalog.mine.Mine;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.improvement.catalog.road.Road;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class ImprovementFactory {

    private static final Map<String, AbstractImprovement> CATALOG = new HashMap<>();
    private static final Map<String, Supplier<AbstractImprovement>> FACTORY = new HashMap<>();

    static {
        FACTORY.put(Road.CLASS_UUID, Road::new);
        FACTORY.put(Farm.CLASS_UUID, Farm::new);
        FACTORY.put(Mine.CLASS_UUID, Mine::new);

        FACTORY.forEach((k, v) -> CATALOG.put(k, v.get()));
    }

    private ImprovementFactory() { }

    public static <T extends AbstractImprovement> T newInstance(String classUuid, AbstractTerrain tile, World world, City city) {
        Supplier<AbstractImprovement> creator = FACTORY.get(classUuid);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown improvement classUuid = " + classUuid);
        }

        T improvement = (T)creator.get();
        improvement.init(tile, world, city);
        return improvement;
    }

    public static <T extends AbstractImprovement> T findByClassUuid(String classUuid) {
        return (T)CATALOG.get(classUuid);
    }
}
