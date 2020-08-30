package com.tsoft.civilization.tile.base.ocean;

import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.util.PassCostList;
import com.tsoft.civilization.unit.civil.Settlers;
import com.tsoft.civilization.unit.civil.Workers;
import com.tsoft.civilization.unit.military.Archers;
import com.tsoft.civilization.unit.military.Warriors;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.tile.base.TilePassCostTable.UNPASSABLE;

public final class OceanPassCostTable {
    private OceanPassCostTable() { }

    public static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Archers.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Settlers.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Warriors.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Workers.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
    }
}
