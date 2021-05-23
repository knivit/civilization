package com.tsoft.civilization.improvement;

import com.tsoft.civilization.improvement.ancientruins.AncientRuins;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.farm.Farm;
import com.tsoft.civilization.improvement.mine.Mine;
import com.tsoft.civilization.improvement.road.Road;
import com.tsoft.civilization.tile.tile.AbstractTile;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class ImprovementFactory {

    private static final Map<String, AbstractImprovement> CATALOG = new HashMap<>();
    private static final Map<String, Function<AbstractTile, AbstractImprovement>> FACTORY = new HashMap<>();

    static {
        FACTORY.put(AncientRuins.CLASS_UUID, AncientRuins::new);
        FACTORY.put(Road.CLASS_UUID, Road::new);
        FACTORY.put(Farm.CLASS_UUID, Farm::new);
        FACTORY.put(Mine.CLASS_UUID, Mine::new);

        FACTORY.forEach((k, v) -> CATALOG.put(k, v.apply(null)));
    }

    private ImprovementFactory() { }

    public static <T extends AbstractImprovement> T newInstance(String classUuid, AbstractTile tile, City city) {
        Function<AbstractTile, AbstractImprovement> supplier = FACTORY.get(classUuid);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown improvement classUuid = " + classUuid);
        }

        T improvement = (T)supplier.apply(tile);
        improvement.init(city);
        return improvement;
    }

    public static <T extends AbstractImprovement> T findByClassUuid(String classUuid) {
        return (T)CATALOG.get(classUuid);
    }
}
