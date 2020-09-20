package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.tile.feature.atoll.Atoll;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.feature.fallout.Fallout;
import com.tsoft.civilization.tile.feature.floodplain.FloodPlain;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.tile.feature.jungle.Jungle;
import com.tsoft.civilization.tile.feature.marsh.Marsh;
import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.tile.feature.oasis.Oasis;

import java.util.HashMap;
import java.util.Map;

public final class FeatureCatalog {

    private FeatureCatalog() { }

    private static final Map<String, TerrainFeature> catalog = new HashMap<>();

    // Read-only objects, this map is to use as a catalog only
    static {
        catalog.put(Atoll.CLASS_UUID, Atoll.STUB);
        catalog.put(Coast.CLASS_UUID, Coast.STUB);
        catalog.put(Fallout.CLASS_UUID, Fallout.STUB);
        catalog.put(FloodPlain.CLASS_UUID, FloodPlain.STUB);
        catalog.put(Forest.CLASS_UUID, Forest.STUB);
        catalog.put(Hill.CLASS_UUID, Hill.STUB);
        catalog.put(Ice.CLASS_UUID, Ice.STUB);
        catalog.put(Jungle.CLASS_UUID, Jungle.STUB);
        catalog.put(Marsh.CLASS_UUID, Marsh.STUB);
        catalog.put(Mountain.CLASS_UUID, Mountain.STUB);
        catalog.put(Oasis.CLASS_UUID, Oasis.STUB);
    }

    public static TerrainFeature findByClassUuid(String classUuid) {
        return catalog.getOrDefault(classUuid, null);
    }
}
