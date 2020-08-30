package com.tsoft.civilization.tile.feature.atoll;

import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.tile.util.PassCostList;
import com.tsoft.civilization.unit.civil.Settlers;
import com.tsoft.civilization.unit.civil.Workers;
import com.tsoft.civilization.unit.military.Archers;
import com.tsoft.civilization.unit.military.Warriors;

import java.util.HashMap;
import java.util.Map;

public final class AtollPassCostTable {

    private AtollPassCostTable() { }

    public static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Citizen.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Archers.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID, PassCostList.of(null, 1));
    }
}