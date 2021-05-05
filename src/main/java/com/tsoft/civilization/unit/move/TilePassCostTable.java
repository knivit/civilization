package com.tsoft.civilization.unit.move;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.desert.Desert;
import com.tsoft.civilization.tile.tile.desert.DesertPassCostTable;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.tile.grassland.GrasslandPassCostTable;
import com.tsoft.civilization.tile.tile.lake.Lake;
import com.tsoft.civilization.tile.tile.lake.LakePassCostTable;
import com.tsoft.civilization.tile.tile.ocean.Ocean;
import com.tsoft.civilization.tile.tile.ocean.OceanPassCostTable;
import com.tsoft.civilization.tile.tile.plain.Plain;
import com.tsoft.civilization.tile.tile.plain.PlainPassCostTable;
import com.tsoft.civilization.tile.tile.snow.Snow;
import com.tsoft.civilization.tile.tile.snow.SnowPassCostTable;
import com.tsoft.civilization.tile.tile.tundra.Tundra;
import com.tsoft.civilization.tile.tile.tundra.TundraPassCostTable;
import com.tsoft.civilization.unit.AbstractUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class TilePassCostTable {
    public static int UNPASSABLE = Integer.MAX_VALUE;

    private TilePassCostTable() { }

    private final static Map<Class<? extends AbstractTile>, Map<String, PassCostList>> table = new HashMap<>();

    static {
        table.put(Desert.class, DesertPassCostTable.table);
        table.put(Grassland.class, GrasslandPassCostTable.table);
        table.put(Lake.class, LakePassCostTable.table);
        table.put(Ocean.class, OceanPassCostTable.table);
        table.put(Plain.class, PlainPassCostTable.table);
        table.put(Snow.class, SnowPassCostTable.table);
        table.put(Tundra.class, TundraPassCostTable.table);
    }

    public static int get(Civilization civilization, AbstractUnit unit, AbstractTile tile) {
        Objects.requireNonNull(civilization, "Civilization can't be null");
        Objects.requireNonNull(unit, "Unit can't be null");
        Objects.requireNonNull(tile, "Tile can't be null");

        Map<String, PassCostList> passCostTable = table.get(tile.getClass());
        PassCostList list = passCostTable.get(unit.getClassUuid());
        return (list == null) ? UNPASSABLE : list.getPassCost(civilization);
    }
}
