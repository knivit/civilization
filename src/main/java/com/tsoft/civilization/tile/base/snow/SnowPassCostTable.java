package com.tsoft.civilization.tile.base.snow;

import com.tsoft.civilization.tile.PassCostList;
import com.tsoft.civilization.unit.civil.Settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;

import java.util.HashMap;
import java.util.Map;

public final class SnowPassCostTable {
    private SnowPassCostTable() { }

    public static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Archers.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID, PassCostList.of(null, 1));
    }
}
