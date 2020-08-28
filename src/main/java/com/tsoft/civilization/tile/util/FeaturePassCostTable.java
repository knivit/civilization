package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.improvement.city.Citizen;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.feature.*;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.Archers;
import com.tsoft.civilization.unit.Settlers;
import com.tsoft.civilization.unit.Warriors;
import com.tsoft.civilization.unit.Workers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class FeaturePassCostTable {
    public static int UNPASSABLE = Integer.MAX_VALUE;

    private FeaturePassCostTable() { }

    private static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Citizen.CLASS_UUID + Atoll.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Citizen.CLASS_UUID + Coast.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 1));
        table.put(Citizen.CLASS_UUID + Fallout.CLASS_UUID, PassCostList.of(null, 3));
        table.put(Citizen.CLASS_UUID + FloodPlain.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Citizen.CLASS_UUID + Forest.CLASS_UUID, PassCostList.of(null, 1));;
        table.put(Citizen.CLASS_UUID + Hill.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Citizen.CLASS_UUID + Ice.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Citizen.CLASS_UUID + Jungle.CLASS_UUID, PassCostList.of(null, 3));
        table.put(Citizen.CLASS_UUID + Marsh.CLASS_UUID, PassCostList.of(null, 3));
        table.put(Citizen.CLASS_UUID + Mountain.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Citizen.CLASS_UUID + Oasis.CLASS_UUID, PassCostList.of(null, 1));

        table.put(Archers.CLASS_UUID + Atoll.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Archers.CLASS_UUID + Coast.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 1));
        table.put(Archers.CLASS_UUID + Fallout.CLASS_UUID, PassCostList.of(null, 3));
        table.put(Archers.CLASS_UUID + FloodPlain.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Archers.CLASS_UUID + Forest.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Archers.CLASS_UUID + Hill.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Archers.CLASS_UUID + Ice.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Archers.CLASS_UUID + Jungle.CLASS_UUID, PassCostList.of(null, 3));;
        table.put(Archers.CLASS_UUID + Marsh.CLASS_UUID, PassCostList.of(null, 3));
        table.put(Archers.CLASS_UUID + Mountain.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Archers.CLASS_UUID + Oasis.CLASS_UUID, PassCostList.of(null, 1));

        table.put(Settlers.CLASS_UUID + Atoll.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID + Coast.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 1));
        table.put(Settlers.CLASS_UUID + Fallout.CLASS_UUID, PassCostList.of(null, 2));
        table.put(Settlers.CLASS_UUID + FloodPlain.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID + Forest.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID + Hill.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID + Ice.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Settlers.CLASS_UUID + Jungle.CLASS_UUID, PassCostList.of(null, 2));
        table.put(Settlers.CLASS_UUID + Marsh.CLASS_UUID, PassCostList.of(null, 2));
        table.put(Settlers.CLASS_UUID + Mountain.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Settlers.CLASS_UUID + Oasis.CLASS_UUID, PassCostList.of(null, 1));

        table.put(Warriors.CLASS_UUID + Atoll.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID + Coast.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 1));
        table.put(Warriors.CLASS_UUID + Fallout.CLASS_UUID, PassCostList.of(null, 3));
        table.put(Warriors.CLASS_UUID + FloodPlain.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID + Forest.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID + Hill.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID + Ice.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Warriors.CLASS_UUID + Jungle.CLASS_UUID, PassCostList.of(null, 3));
        table.put(Warriors.CLASS_UUID + Marsh.CLASS_UUID, PassCostList.of(null, 3));
        table.put(Warriors.CLASS_UUID + Mountain.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Warriors.CLASS_UUID + Oasis.CLASS_UUID, PassCostList.of(null, 1));

        table.put(Workers.CLASS_UUID + Atoll.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID + Coast.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 1));
        table.put(Workers.CLASS_UUID + Fallout.CLASS_UUID, PassCostList.of(null, 2));
        table.put(Workers.CLASS_UUID + FloodPlain.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID + Forest.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID + Hill.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID + Ice.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Workers.CLASS_UUID + Jungle.CLASS_UUID, PassCostList.of(null, 2));
        table.put(Workers.CLASS_UUID + Marsh.CLASS_UUID, PassCostList.of(null, 2));
        table.put(Workers.CLASS_UUID + Mountain.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Workers.CLASS_UUID + Oasis.CLASS_UUID, PassCostList.of(null, 1));
    }

    public static int get(AbstractUnit<?> unit, TerrainFeature<?> feature) {
        Objects.requireNonNull(unit, "unit can't be null");
        Objects.requireNonNull(feature, "feature can't be null");

        String key = unit.getClassUuid() + feature.getClassUuid();
        PassCostList list = table.get(key);
        return (list == null) ? UNPASSABLE : list.getPassCost(unit.getCivilization());
    }
}
