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

    private static final Map<String, TerrainFeature<?>> featuresCatalog = new HashMap<>();

    // Read-only objects, this map is to use as a catalog only
    static {
        featuresCatalog.put(Atoll.CLASS_UUID, new Atoll());
        featuresCatalog.put(Coast.CLASS_UUID, new Coast());
        featuresCatalog.put(Fallout.CLASS_UUID, new Fallout());
        featuresCatalog.put(FloodPlain.CLASS_UUID, new FloodPlain());
        featuresCatalog.put(Forest.CLASS_UUID, new Forest());
        featuresCatalog.put(Hill.CLASS_UUID, new Hill());
        featuresCatalog.put(Ice.CLASS_UUID, new Ice());
        featuresCatalog.put(Jungle.CLASS_UUID, new Jungle());
        featuresCatalog.put(Marsh.CLASS_UUID, new Marsh());
        featuresCatalog.put(Mountain.CLASS_UUID, new Mountain());
        featuresCatalog.put(Oasis.CLASS_UUID, new Oasis());
    }

    public static TerrainFeature<?> findByClassUuid(String classUuid) {
        return featuresCatalog.getOrDefault(classUuid, null);
    }
}
