package com.tsoft.civilization.unit.service.move;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.desert.DesertPassCostTable;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.grassland.GrasslandPassCostTable;
import com.tsoft.civilization.tile.terrain.lake.Lake;
import com.tsoft.civilization.tile.terrain.lake.LakePassCostTable;
import com.tsoft.civilization.tile.terrain.ocean.Ocean;
import com.tsoft.civilization.tile.terrain.ocean.OceanPassCostTable;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.plains.PlainsPassCostTable;
import com.tsoft.civilization.tile.terrain.snow.Snow;
import com.tsoft.civilization.tile.terrain.snow.SnowPassCostTable;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;
import com.tsoft.civilization.tile.terrain.tundra.TundraPassCostTable;
import com.tsoft.civilization.unit.AbstractUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class TilePassCostTable {
    public static int UNPASSABLE = Integer.MAX_VALUE;

    private TilePassCostTable() { }

    private final static Map<Class<? extends AbstractTerrain>, Map<String, PassCostList>> table = new HashMap<>();

    static {
        table.put(Desert.class, DesertPassCostTable.table);
        table.put(Grassland.class, GrasslandPassCostTable.table);
        table.put(Lake.class, LakePassCostTable.table);
        table.put(Ocean.class, OceanPassCostTable.table);
        table.put(Plains.class, PlainsPassCostTable.table);
        table.put(Snow.class, SnowPassCostTable.table);
        table.put(Tundra.class, TundraPassCostTable.table);
    }

    public static int get(Civilization civilization, AbstractUnit unit, AbstractTerrain tile) {
        Objects.requireNonNull(civilization, "Civilization can't be null");
        Objects.requireNonNull(unit, "Unit can't be null");
        Objects.requireNonNull(tile, "Tile can't be null");

        Map<String, PassCostList> passCostTable = table.get(tile.getClass());
        PassCostList list = passCostTable.get(unit.getClassUuid());
        return (list == null) ? UNPASSABLE : list.getPassCost(civilization);
    }
}
