package com.tsoft.civilization.improvement;

import com.tsoft.civilization.improvement.ancientruins.AncientRuins;
import com.tsoft.civilization.improvement.farm.Farm;
import com.tsoft.civilization.improvement.mine.Mine;
import com.tsoft.civilization.improvement.road.Road;
import com.tsoft.civilization.tile.tile.AbstractTile;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public final class ImprovementFactory {

    private ImprovementFactory() { }

    public static <T extends AbstractImprovement> T newInstance(String classUuid, AbstractTile tile) {
        return (T)createImprovement(classUuid, tile);
    }

    private static final Map<String, Function<AbstractTile, AbstractImprovement>> CATALOG = new HashMap<>();

    static {
        CATALOG.put(Road.CLASS_UUID, Road::new);
        CATALOG.put(Farm.CLASS_UUID, Farm::new);
        CATALOG.put(Mine.CLASS_UUID, Mine::new);
        CATALOG.put(AncientRuins.CLASS_UUID, AncientRuins::new);
    }

    private static AbstractImprovement createImprovement(String classUuid, AbstractTile tile) {
        Function<AbstractTile, AbstractImprovement> supplier = CATALOG.get(classUuid);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown improvement classUuid = " + classUuid);
        }

        return supplier.apply(tile);
    }

    public static <T extends AbstractImprovement> T findByClassUuid(String classUuid) {
        return (T)CATALOG.get(classUuid);
    }
}
