package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.Coast;
import com.tsoft.civilization.tile.base.Desert;
import com.tsoft.civilization.tile.base.Grassland;
import com.tsoft.civilization.tile.base.Ice;
import com.tsoft.civilization.tile.base.Lake;
import com.tsoft.civilization.tile.base.Mountain;
import com.tsoft.civilization.tile.base.Ocean;
import com.tsoft.civilization.tile.base.Plain;
import com.tsoft.civilization.tile.base.Snow;
import com.tsoft.civilization.tile.base.Tundra;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.Archers;
import com.tsoft.civilization.unit.Settlers;
import com.tsoft.civilization.unit.Warriors;
import com.tsoft.civilization.unit.Workers;
import com.tsoft.civilization.world.Civilization;

import java.util.HashMap;
import java.util.Map;

public final class TilePassCostTable {
    public static int UNPASSABLE = Integer.MAX_VALUE;

    private TilePassCostTable() { }

    private static Map<String, PassCostList> table = new HashMap<String, PassCostList>();

    static {
        table.put(Archers.CLASS_UUID + Coast.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 1));
        table.put(Archers.CLASS_UUID + Desert.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Archers.CLASS_UUID + Grassland.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Archers.CLASS_UUID + Ice.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Archers.CLASS_UUID + Lake.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 2));
        table.put(Archers.CLASS_UUID + Mountain.CLASS_UUID, new PassCostList().add(null, UNPASSABLE));
        table.put(Archers.CLASS_UUID + Ocean.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 2));
        table.put(Archers.CLASS_UUID + Plain.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Archers.CLASS_UUID + Snow.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Archers.CLASS_UUID + Tundra.CLASS_UUID, new PassCostList().add(null, 1));

        table.put(Settlers.CLASS_UUID + Coast.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 1));
        table.put(Settlers.CLASS_UUID + Desert.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Settlers.CLASS_UUID + Grassland.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Settlers.CLASS_UUID + Ice.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Settlers.CLASS_UUID + Lake.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 2));
        table.put(Settlers.CLASS_UUID + Mountain.CLASS_UUID, new PassCostList().add(null, UNPASSABLE));
        table.put(Settlers.CLASS_UUID + Ocean.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 2));
        table.put(Settlers.CLASS_UUID + Plain.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Settlers.CLASS_UUID + Snow.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Settlers.CLASS_UUID + Tundra.CLASS_UUID, new PassCostList().add(null, 1));

        table.put(Warriors.CLASS_UUID + Coast.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 1));
        table.put(Warriors.CLASS_UUID + Desert.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Warriors.CLASS_UUID + Grassland.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Warriors.CLASS_UUID + Ice.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Warriors.CLASS_UUID + Lake.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 2));
        table.put(Warriors.CLASS_UUID + Mountain.CLASS_UUID, new PassCostList().add(null, UNPASSABLE));
        table.put(Warriors.CLASS_UUID + Ocean.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 2));
        table.put(Warriors.CLASS_UUID + Plain.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Warriors.CLASS_UUID + Snow.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Warriors.CLASS_UUID + Tundra.CLASS_UUID, new PassCostList().add(null, 1));

        table.put(Workers.CLASS_UUID + Coast.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 1));
        table.put(Workers.CLASS_UUID + Desert.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Workers.CLASS_UUID + Grassland.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Workers.CLASS_UUID + Ice.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Workers.CLASS_UUID + Lake.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 2));
        table.put(Workers.CLASS_UUID + Mountain.CLASS_UUID, new PassCostList().add(null, UNPASSABLE));
        table.put(Workers.CLASS_UUID + Ocean.CLASS_UUID, new PassCostList().add(null, UNPASSABLE).add(Technology.NAVIGATION, 2));
        table.put(Workers.CLASS_UUID + Plain.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Workers.CLASS_UUID + Snow.CLASS_UUID, new PassCostList().add(null, 1));
        table.put(Workers.CLASS_UUID + Tundra.CLASS_UUID, new PassCostList().add(null, 1));
    }

    public static int get(AbstractUnit unit, AbstractTile tile) {
        assert (unit != null && tile != null) : "Unit and/or tile can't be null";
        String key = unit.getClassUuid() + tile.getClassUuid();

        PassCostList list = table.get(key);
        if (list == null) {
            return UNPASSABLE;
        }

        int passCost = list.get(0).getPassCost();
        if (list.size() == 1) {
            return passCost;
        }

        // check for technologies
        Civilization civilization = unit.getCivilization();
        for (int i = 1; i < list.size(); i ++) {
            if (civilization.isResearched(list.get(i).getTechnology())) {
                return list.get(i).getPassCost();
            }
        }

        return passCost;
    }
}
