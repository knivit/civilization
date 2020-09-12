package com.tsoft.civilization.tile.feature.forest;

import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.tile.PassCostList;
import com.tsoft.civilization.unit.civil.Settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;

import java.util.HashMap;
import java.util.Map;

public final class ForestPassCostTable {

    private ForestPassCostTable() { }

    public static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Citizen.CLASS_UUID, PassCostList.of(null, 1));;
        table.put(Archers.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID, PassCostList.of(null, 1));
    }
}
