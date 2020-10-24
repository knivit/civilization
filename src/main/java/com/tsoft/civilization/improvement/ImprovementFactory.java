package com.tsoft.civilization.improvement;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.farm.Farm;
import com.tsoft.civilization.improvement.road.Road;
import com.tsoft.civilization.util.Point;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public final class ImprovementFactory {

    private ImprovementFactory() { }

    public static <T extends AbstractImprovement> T newInstance(String classUuid, Civilization civilization, Point location) {
        return (T)createImprovement(classUuid, civilization, location);
    }

    private static final Map<String, BiFunction<Civilization, Point, AbstractImprovement>> IMPROVEMENT_CATALOG = new HashMap<>();

    static {
        IMPROVEMENT_CATALOG.put(City.CLASS_UUID, City::new);
        IMPROVEMENT_CATALOG.put(Road.CLASS_UUID, Road::new);
        IMPROVEMENT_CATALOG.put(Farm.CLASS_UUID, Farm::new);
    }

    private static AbstractImprovement createImprovement(String classUuid, Civilization civilization, Point location) {
        BiFunction<Civilization, Point, AbstractImprovement> supplier = IMPROVEMENT_CATALOG.get(classUuid);
        if (supplier == null) {
            throw new IllegalArgumentException("Unknown improvement classUuid = " + classUuid);
        }
        return supplier.apply(civilization, location);
    }
}
