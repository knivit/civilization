package com.tsoft.civilization.tile.feature.mountain;

import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.tile.util.PassCostList;
import com.tsoft.civilization.unit.civil.Settlers;
import com.tsoft.civilization.unit.civil.Workers;
import com.tsoft.civilization.unit.military.Archers;
import com.tsoft.civilization.unit.military.Warriors;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.tile.base.TilePassCostTable.UNPASSABLE;

public final class MountainPassCostTable {

    private MountainPassCostTable() { }

    public static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Citizen.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Archers.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Settlers.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Warriors.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Workers.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
    }
}
