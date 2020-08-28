package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.tile.feature.*;
import com.tsoft.civilization.unit.Archers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MissileFeaturePastCostTable {
    private MissileFeaturePastCostTable() { }

    private static Map<String, Integer> table = new HashMap<>();

    static {
        table.put(Archers.CLASS_UUID + Atoll.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Fallout.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + FloodPlain.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Forest.CLASS_UUID, 3);
        table.put(Archers.CLASS_UUID + Hill.CLASS_UUID, 2);
        table.put(Archers.CLASS_UUID + Ice.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Jungle.CLASS_UUID, 3);
        table.put(Archers.CLASS_UUID + Marsh.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Oasis.CLASS_UUID, 2);

        table.put(City.CLASS_UUID + Atoll.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Fallout.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + FloodPlain.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Forest.CLASS_UUID, 3);
        table.put(City.CLASS_UUID + Hill.CLASS_UUID, 2);
        table.put(City.CLASS_UUID + Ice.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Jungle.CLASS_UUID, 3);
        table.put(City.CLASS_UUID + Marsh.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Oasis.CLASS_UUID, 2);
    }

    public static int get(HasCombatStrength attacker, TerrainFeature<?> feature) {
        Objects.requireNonNull(attacker, "Attacker can't be null");
        Objects.requireNonNull(feature, "Feature can't be null");

        String key = attacker.getClassUuid() + feature.getClassUuid();
        Integer passCost = table.get(key);

        return (passCost == null) ? 0 : passCost;
    }
}
