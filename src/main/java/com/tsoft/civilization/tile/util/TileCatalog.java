package com.tsoft.civilization.tile.util;

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
import com.tsoft.civilization.tile.feature.TerrainFeature;
import com.tsoft.civilization.tile.feature.Atoll;
import com.tsoft.civilization.tile.feature.Fallout;
import com.tsoft.civilization.tile.feature.FloodPlain;
import com.tsoft.civilization.tile.feature.Forest;
import com.tsoft.civilization.tile.feature.Hill;
import com.tsoft.civilization.tile.feature.Jungle;
import com.tsoft.civilization.tile.feature.Marsh;
import com.tsoft.civilization.tile.feature.Oasis;

import java.util.HashMap;
import java.util.Map;

public class TileCatalog {
    private static final Map<String, AbstractTile<?>> tilesCatalog = new HashMap<>();

    // Read-only objects, this map is to use as a catalog only
    static {
        tilesCatalog.put(Coast.CLASS_UUID, new Coast());
        tilesCatalog.put(Desert.CLASS_UUID, new Desert());
        tilesCatalog.put(Grassland.CLASS_UUID, new Grassland());
        tilesCatalog.put(Ice.CLASS_UUID, new Ice());
        tilesCatalog.put(Lake.CLASS_UUID, new Lake());
        tilesCatalog.put(Mountain.CLASS_UUID, new Mountain());
        tilesCatalog.put(Ocean.CLASS_UUID, new Ocean());
        tilesCatalog.put(Plain.CLASS_UUID, new Plain());
        tilesCatalog.put(Snow.CLASS_UUID, new Snow());
        tilesCatalog.put(Tundra.CLASS_UUID, new Tundra());
    }

    private static final Map<String, TerrainFeature<?>> featuresCatalog = new HashMap<>();

    // Read-only objects, this map is to use as a catalog only
    static {
        featuresCatalog.put(Atoll.CLASS_UUID, new Atoll());
        featuresCatalog.put(Fallout.CLASS_UUID, new Fallout());
        featuresCatalog.put(FloodPlain.CLASS_UUID, new FloodPlain());
        featuresCatalog.put(Forest.CLASS_UUID, new Forest());
        featuresCatalog.put(Hill.CLASS_UUID, new Hill());
        featuresCatalog.put(Jungle.CLASS_UUID, new Jungle());
        featuresCatalog.put(Marsh.CLASS_UUID, new Marsh());
        featuresCatalog.put(Oasis.CLASS_UUID, new Oasis());
    }

    private TileCatalog() { }

    public static AbstractTile<?> findTileByClassUuid(String classUuid) {
        return tilesCatalog.getOrDefault(classUuid, null);
    }

    public static TerrainFeature<?> findFeatureByClassUuid(String classUuid) {
        return featuresCatalog.getOrDefault(classUuid, null);
    }
}
