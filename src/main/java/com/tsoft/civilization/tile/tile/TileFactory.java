package com.tsoft.civilization.tile.tile;

import com.tsoft.civilization.tile.tile.desert.Desert;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.tile.lake.Lake;
import com.tsoft.civilization.tile.tile.ocean.Ocean;
import com.tsoft.civilization.tile.tile.plain.Plain;
import com.tsoft.civilization.tile.tile.snow.Snow;
import com.tsoft.civilization.tile.tile.tundra.Tundra;
import com.tsoft.civilization.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class TileFactory {

    private static final Map<String, Supplier<? extends AbstractTile>> FACTORY = new HashMap<>();
    private static final Map<String, AbstractTile> CATALOG = new HashMap<>();

    static {
        FACTORY.put(Desert.CLASS_UUID, Desert::new);
        FACTORY.put(Grassland.CLASS_UUID, Grassland::new);
        FACTORY.put(Lake.CLASS_UUID, Lake::new);
        FACTORY.put(Ocean.CLASS_UUID, Ocean::new);
        FACTORY.put(Plain.CLASS_UUID, Plain::new);
        FACTORY.put(Snow.CLASS_UUID, Snow::new);
        FACTORY.put(Tundra.CLASS_UUID, Tundra::new);

        FACTORY.forEach((k, v) -> CATALOG.put(k, v.get()));
    }

    public static <T extends AbstractTile> T newInstance(String classUuid) {
        Supplier<? extends AbstractTile> creator = FACTORY.get(classUuid);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown tile classUuid = " + classUuid);
        }

        return  (T)creator.get();
    }

    public static <T extends AbstractTile> T findByClassUuid(String classUuid) {
        return (T)CATALOG.get(classUuid);
    }

    public static List<AbstractTile> getAvailable(World world) {
        List<AbstractTile> result = new ArrayList<>();

        for (AbstractTile tile : CATALOG.values()) {
            result.add(tile);
        }

        return result;
    }
}
