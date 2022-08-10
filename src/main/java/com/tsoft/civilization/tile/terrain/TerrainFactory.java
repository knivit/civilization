package com.tsoft.civilization.tile.terrain;

import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.lake.Lake;
import com.tsoft.civilization.tile.terrain.ocean.Ocean;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.snow.Snow;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;
import com.tsoft.civilization.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class TerrainFactory {

    private static final Map<String, Supplier<? extends AbstractTerrain>> FACTORY = new HashMap<>();
    private static final Map<String, AbstractTerrain> CATALOG = new HashMap<>();

    static {
        FACTORY.put(Desert.CLASS_UUID, Desert::new);
        FACTORY.put(Grassland.CLASS_UUID, Grassland::new);
        FACTORY.put(Lake.CLASS_UUID, Lake::new);
        FACTORY.put(Ocean.CLASS_UUID, Ocean::new);
        FACTORY.put(Plains.CLASS_UUID, Plains::new);
        FACTORY.put(Snow.CLASS_UUID, Snow::new);
        FACTORY.put(Tundra.CLASS_UUID, Tundra::new);

        FACTORY.forEach((k, v) -> CATALOG.put(k, v.get()));
    }

    public static <T extends AbstractTerrain> T newInstance(String classUuid) {
        Supplier<? extends AbstractTerrain> creator = FACTORY.get(classUuid);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown tile classUuid = " + classUuid);
        }

        return  (T)creator.get();
    }

    public static <T extends AbstractTerrain> T findByClassUuid(String classUuid) {
        return (T)CATALOG.get(classUuid);
    }

    public static List<AbstractTerrain> getAvailable(World world) {
        List<AbstractTerrain> result = new ArrayList<>();

        for (AbstractTerrain tile : CATALOG.values()) {
            result.add(tile);
        }

        return result;
    }
}
