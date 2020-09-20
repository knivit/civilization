package com.tsoft.civilization.tile.base;

import com.tsoft.civilization.tile.base.desert.Desert;
import com.tsoft.civilization.tile.base.grassland.Grassland;
import com.tsoft.civilization.tile.base.lake.Lake;
import com.tsoft.civilization.tile.base.ocean.Ocean;
import com.tsoft.civilization.tile.base.plain.Plain;
import com.tsoft.civilization.tile.base.snow.Snow;
import com.tsoft.civilization.tile.base.tundra.Tundra;

import java.util.HashMap;
import java.util.Map;

public final class TileCatalog {
    private TileCatalog() { }

    private static final Map<String, AbstractTile> catalog = new HashMap<>();

    // Read-only objects, this map is to use as a catalog only
    static {
        catalog.put(Desert.CLASS_UUID, Desert.STUB);
        catalog.put(Grassland.CLASS_UUID, Grassland.STUB);
        catalog.put(Lake.CLASS_UUID, Lake.STUB);
        catalog.put(Ocean.CLASS_UUID, Ocean.STUB);
        catalog.put(Plain.CLASS_UUID, Plain.STUB);
        catalog.put(Snow.CLASS_UUID, Snow.STUB);
        catalog.put(Tundra.CLASS_UUID, Tundra.STUB);
    }

    public static AbstractTile findByClassUuid(String classUuid) {
        return catalog.getOrDefault(classUuid, null);
    }
}
