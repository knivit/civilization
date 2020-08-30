package com.tsoft.civilization.tile.base;

import java.util.HashMap;
import java.util.Map;

public final class TileCatalog {
    private TileCatalog() { }

    private static final Map<String, AbstractTile<?>> tilesCatalog = new HashMap<>();

    // Read-only objects, this map is to use as a catalog only
    static {
        tilesCatalog.put(Desert.CLASS_UUID, new Desert());
        tilesCatalog.put(Grassland.CLASS_UUID, new Grassland());
        tilesCatalog.put(Lake.CLASS_UUID, new Lake());
        tilesCatalog.put(Ocean.CLASS_UUID, new Ocean());
        tilesCatalog.put(Plain.CLASS_UUID, new Plain());
        tilesCatalog.put(Snow.CLASS_UUID, new Snow());
        tilesCatalog.put(Tundra.CLASS_UUID, new Tundra());
    }

    public static AbstractTile<?> findByClassUuid(String classUuid) {
        return tilesCatalog.getOrDefault(classUuid, null);
    }
}
