package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.tile.feature.Atoll;
import com.tsoft.civilization.tile.feature.Fallout;
import com.tsoft.civilization.tile.feature.FloodPlain;
import com.tsoft.civilization.tile.feature.Forest;
import com.tsoft.civilization.tile.feature.Hill;
import com.tsoft.civilization.tile.feature.Jungle;
import com.tsoft.civilization.tile.feature.Marsh;
import com.tsoft.civilization.tile.feature.Oasis;
import com.tsoft.civilization.unit.Archers;

import java.util.HashMap;
import java.util.Map;

public class MissileFeaturePastCostTable {
    private MissileFeaturePastCostTable() { }

    private static Map<String, Integer> table = new HashMap<String, Integer>();

    static {
        table.put(Archers.CLASS_UUID + Atoll.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Fallout.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + FloodPlain.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Forest.CLASS_UUID, 3);
        table.put(Archers.CLASS_UUID + Hill.CLASS_UUID, 2);
        table.put(Archers.CLASS_UUID + Jungle.CLASS_UUID, 3);
        table.put(Archers.CLASS_UUID + Marsh.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Oasis.CLASS_UUID, 2);

        table.put(City.CLASS_UUID + Atoll.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Fallout.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + FloodPlain.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Forest.CLASS_UUID, 3);
        table.put(City.CLASS_UUID + Hill.CLASS_UUID, 2);
        table.put(City.CLASS_UUID + Jungle.CLASS_UUID, 3);
        table.put(City.CLASS_UUID + Marsh.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Oasis.CLASS_UUID, 2);
    }

    public static int get(HasCombatStrength attacker, TerrainFeature feature) {
        assert (attacker != null && feature != null) : "Attacker and/or feature can't be null";
        String key = attacker.getClassUuid() + feature.getClassUuid();

        Integer passCost = table.get(key);
        if (passCost == null) {
            return 0;
        }

        return passCost;
    }
}
