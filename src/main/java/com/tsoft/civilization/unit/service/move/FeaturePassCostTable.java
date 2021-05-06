package com.tsoft.civilization.unit.service.move;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.tile.feature.atoll.Atoll;
import com.tsoft.civilization.tile.feature.atoll.AtollPassCostTable;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.feature.coast.CoastPassCostTable;
import com.tsoft.civilization.tile.feature.fallout.Fallout;
import com.tsoft.civilization.tile.feature.fallout.FalloutPassCostTable;
import com.tsoft.civilization.tile.feature.floodplain.FloodPlain;
import com.tsoft.civilization.tile.feature.floodplain.FloodPlainPassCostTable;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.tile.feature.forest.ForestPassCostTable;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.feature.hill.HillPassCostTable;
import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.tile.feature.ice.IcePassCostTable;
import com.tsoft.civilization.tile.feature.jungle.Jungle;
import com.tsoft.civilization.tile.feature.jungle.JunglePassCostTable;
import com.tsoft.civilization.tile.feature.marsh.Marsh;
import com.tsoft.civilization.tile.feature.marsh.MarshPassCostTable;
import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.tile.feature.mountain.MountainPassCostTable;
import com.tsoft.civilization.tile.feature.oasis.Oasis;
import com.tsoft.civilization.tile.feature.oasis.OasisPassCostTable;
import com.tsoft.civilization.unit.AbstractUnit;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.tsoft.civilization.unit.service.move.TilePassCostTable.UNPASSABLE;

public final class FeaturePassCostTable {
    private FeaturePassCostTable() { }

    private final static Map<Class<? extends AbstractFeature>, Map<String, PassCostList>> table = new HashMap<>();

    static {
        table.put(Atoll.class, AtollPassCostTable.table);
        table.put(Coast.class, CoastPassCostTable.table);
        table.put(Fallout.class, FalloutPassCostTable.table);
        table.put(FloodPlain.class, FloodPlainPassCostTable.table);
        table.put(Forest.class, ForestPassCostTable.table);
        table.put(Hill.class, HillPassCostTable.table);
        table.put(Ice.class, IcePassCostTable.table);
        table.put(Jungle.class, JunglePassCostTable.table);
        table.put(Marsh.class, MarshPassCostTable.table);
        table.put(Mountain.class, MountainPassCostTable.table);
        table.put(Oasis.class, OasisPassCostTable.table);
    }

    public static int get(Civilization civilization, AbstractUnit unit, AbstractFeature feature) {
        Objects.requireNonNull(unit, "unit can't be null");
        Objects.requireNonNull(feature, "feature can't be null");

        Map<String, PassCostList> passCostTable = table.get(feature.getClass());
        PassCostList list = passCostTable.get(unit.getClassUuid());
        return (list == null) ? UNPASSABLE : list.getPassCost(civilization);
    }
}
