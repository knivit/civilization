package com.tsoft.civilization.tile.feature.jungle;

import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.tile.PassCostList;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;

import java.util.HashMap;
import java.util.Map;

public final class JunglePassCostTable {

    private JunglePassCostTable() { }

    public static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Citizen.CLASS_UUID, PassCostList.of(null, 3));
        table.put(Archers.CLASS_UUID, PassCostList.of(null, 3));;
        table.put(Settlers.CLASS_UUID, PassCostList.of(null, 2));
        table.put(Warriors.CLASS_UUID, PassCostList.of(null, 3));
        table.put(Workers.CLASS_UUID, PassCostList.of(null, 2));
    }
}
