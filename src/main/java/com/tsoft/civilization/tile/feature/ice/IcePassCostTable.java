package com.tsoft.civilization.tile.feature.ice;

import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.tile.PassCostList;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.tile.base.TilePassCostTable.UNPASSABLE;

public final class IcePassCostTable {

    private IcePassCostTable() { }

    public static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Citizen.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Archers.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Settlers.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Warriors.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
        table.put(Workers.CLASS_UUID, PassCostList.of(null, UNPASSABLE));
    }
}
