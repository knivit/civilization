package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.tile.feature.Atoll;
import com.tsoft.civilization.tile.feature.Fallout;
import com.tsoft.civilization.tile.feature.FloodPlain;
import com.tsoft.civilization.tile.feature.Forest;
import com.tsoft.civilization.tile.feature.Hill;
import com.tsoft.civilization.tile.feature.Jungle;
import com.tsoft.civilization.tile.feature.Marsh;
import com.tsoft.civilization.tile.feature.Oasis;
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

    private static Map<String, Integer> table = new HashMap<>();

    static {
        table.put(Archers.CLASS_UUID + Atoll.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Fallout.CLASS_UUID, 3);
        table.put(Archers.CLASS_UUID + FloodPlain.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Forest.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Hill.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Jungle.CLASS_UUID, 3);
        table.put(Archers.CLASS_UUID + Marsh.CLASS_UUID, 3);
        table.put(Archers.CLASS_UUID + Oasis.CLASS_UUID, 1);

        table.put(Settlers.CLASS_UUID + Atoll.CLASS_UUID, 1);
        table.put(Settlers.CLASS_UUID + Fallout.CLASS_UUID, 2);
        table.put(Settlers.CLASS_UUID + FloodPlain.CLASS_UUID, 1);
        table.put(Settlers.CLASS_UUID + Forest.CLASS_UUID, 1);
        table.put(Settlers.CLASS_UUID + Hill.CLASS_UUID, 1);
        table.put(Settlers.CLASS_UUID + Jungle.CLASS_UUID, 2);
        table.put(Settlers.CLASS_UUID + Marsh.CLASS_UUID, 2);
        table.put(Settlers.CLASS_UUID + Oasis.CLASS_UUID, 1);

        table.put(Warriors.CLASS_UUID + Atoll.CLASS_UUID, 1);
        table.put(Warriors.CLASS_UUID + Fallout.CLASS_UUID, 3);
        table.put(Warriors.CLASS_UUID + FloodPlain.CLASS_UUID, 1);
        table.put(Warriors.CLASS_UUID + Forest.CLASS_UUID, 1);
        table.put(Warriors.CLASS_UUID + Hill.CLASS_UUID, 1);
        table.put(Warriors.CLASS_UUID + Jungle.CLASS_UUID, 3);
        table.put(Warriors.CLASS_UUID + Marsh.CLASS_UUID, 3);
        table.put(Warriors.CLASS_UUID + Oasis.CLASS_UUID, 1);

        table.put(Workers.CLASS_UUID + Atoll.CLASS_UUID, 1);
        table.put(Workers.CLASS_UUID + Fallout.CLASS_UUID, 2);
        table.put(Workers.CLASS_UUID + FloodPlain.CLASS_UUID, 1);
        table.put(Workers.CLASS_UUID + Forest.CLASS_UUID, 1);
        table.put(Workers.CLASS_UUID + Hill.CLASS_UUID, 1);
        table.put(Workers.CLASS_UUID + Jungle.CLASS_UUID, 2);
        table.put(Workers.CLASS_UUID + Marsh.CLASS_UUID, 2);
        table.put(Workers.CLASS_UUID + Oasis.CLASS_UUID, 1);
    }

    public static int get(AbstractUnit<?> unit, TerrainFeature<?> feature) {
        Objects.requireNonNull(unit, "Unit can't be null");
        Objects.requireNonNull(feature, "Feature can't be null");

        String key = unit.getClassUuid() + feature.getClassUuid();
        Integer passCost = table.get(key);

        return (passCost == null) ? UNPASSABLE : passCost;
    }
}
