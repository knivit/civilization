package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.Desert;
import com.tsoft.civilization.tile.base.Grassland;
import com.tsoft.civilization.tile.base.Lake;
import com.tsoft.civilization.tile.base.Ocean;
import com.tsoft.civilization.tile.base.Plain;
import com.tsoft.civilization.tile.base.Snow;
import com.tsoft.civilization.tile.base.Tundra;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.Archers;
import com.tsoft.civilization.unit.Settlers;
import com.tsoft.civilization.unit.Warriors;
import com.tsoft.civilization.unit.Workers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class TilePassCostTable {
    public static int UNPASSABLE = Integer.MAX_VALUE;

    private TilePassCostTable() { }

    private static Map<String, PassCostList> table = new HashMap<>();

    static {
        table.put(Archers.CLASS_UUID + Desert.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Archers.CLASS_UUID + Grassland.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Archers.CLASS_UUID + Lake.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Archers.CLASS_UUID + Ocean.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Archers.CLASS_UUID + Plain.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Archers.CLASS_UUID + Snow.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Archers.CLASS_UUID + Tundra.CLASS_UUID, PassCostList.of(null, 1));

        table.put(Settlers.CLASS_UUID + Desert.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID + Grassland.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID + Lake.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Settlers.CLASS_UUID + Ocean.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Settlers.CLASS_UUID + Plain.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID + Snow.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Settlers.CLASS_UUID + Tundra.CLASS_UUID, PassCostList.of(null, 1));

        table.put(Warriors.CLASS_UUID + Desert.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID + Grassland.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID + Lake.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Warriors.CLASS_UUID + Ocean.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Warriors.CLASS_UUID + Plain.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID + Snow.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Warriors.CLASS_UUID + Tundra.CLASS_UUID, PassCostList.of(null, 1));

        table.put(Workers.CLASS_UUID + Desert.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID + Grassland.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID + Lake.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Workers.CLASS_UUID + Ocean.CLASS_UUID, PassCostList.of(null, UNPASSABLE, Technology.NAVIGATION, 2));
        table.put(Workers.CLASS_UUID + Plain.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID + Snow.CLASS_UUID, PassCostList.of(null, 1));
        table.put(Workers.CLASS_UUID + Tundra.CLASS_UUID, PassCostList.of(null, 1));
    }

    public static int get(AbstractUnit<?> unit, AbstractTile<?> tile) {
        Objects.requireNonNull(unit, "Unit can't be null");
        Objects.requireNonNull(tile, "Tile can't be null");

        String key = unit.getClassUuid() + tile.getClassUuid();
        PassCostList list = table.get(key);
        return (list == null) ? UNPASSABLE : list.getPassCost(unit.getCivilization());
    }
}
