package com.tsoft.civilization.combat;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.tile.feature.atoll.Atoll;
import com.tsoft.civilization.tile.feature.fallout.Fallout;
import com.tsoft.civilization.tile.feature.floodplain.FloodPlain;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.tile.feature.jungle.Jungle;
import com.tsoft.civilization.tile.feature.marsh.Marsh;
import com.tsoft.civilization.tile.feature.oasis.Oasis;
import com.tsoft.civilization.unit.catalog.archers.Archers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MissileFeaturePastCostTable {
    private MissileFeaturePastCostTable() { }

    private static final Map<String, Integer> table = new HashMap<>();

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

    public static int get(HasCombatStrength attacker, AbstractFeature feature) {
        Objects.requireNonNull(attacker, "Attacker can't be null");
        Objects.requireNonNull(feature, "Feature can't be null");

        String key = attacker.getClassUuid() + feature.getClassUuid();
        Integer passCost = table.get(key);

        return (passCost == null) ? 0 : passCost;
    }
}
