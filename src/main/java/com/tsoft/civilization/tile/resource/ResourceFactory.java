package com.tsoft.civilization.tile.resource;

import com.tsoft.civilization.tile.resource.bonus.*;
import com.tsoft.civilization.tile.resource.luxury.*;
import com.tsoft.civilization.tile.resource.strategic.*;
import com.tsoft.civilization.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ResourceFactory {

    private static final Map<String, Supplier<? extends AbstractResource>> FACTORY = new HashMap<>();
    private static final Map<String, AbstractResource> CATALOG = new HashMap<>();

    static {
        // bonus
        FACTORY.put(Bananas.CLASS_UUID, Bananas::new);
        FACTORY.put(Bison.CLASS_UUID, Bison::new);
        FACTORY.put(Cattle.CLASS_UUID, Cattle::new);
        FACTORY.put(Deer.CLASS_UUID, Deer::new);
        FACTORY.put(Fish.CLASS_UUID, Fish::new);
        FACTORY.put(Sheep.CLASS_UUID, Sheep::new);
        FACTORY.put(Stone.CLASS_UUID, Stone::new);
        FACTORY.put(Wheat.CLASS_UUID, Wheat::new);

        // luxury
        FACTORY.put(Cotton.CLASS_UUID, Cotton::new);
        FACTORY.put(Silk.CLASS_UUID, Silk::new);
        FACTORY.put(Spices.CLASS_UUID, Spices::new);
        FACTORY.put(Sugar.CLASS_UUID, Sugar::new);
        FACTORY.put(Whales.CLASS_UUID, Whales::new);
        FACTORY.put(Wine.CLASS_UUID, Wine::new);

        // strategic
        FACTORY.put(Aluminium.CLASS_UUID, Aluminium::new);
        FACTORY.put(Coal.CLASS_UUID, Coal::new);
        FACTORY.put(Horses.CLASS_UUID, Horses::new);
        FACTORY.put(Iron.CLASS_UUID, Iron::new);
        FACTORY.put(Oil.CLASS_UUID, Oil::new);
        FACTORY.put(Uranium.CLASS_UUID, Uranium::new);

        FACTORY.forEach((k, v) -> CATALOG.put(k, v.get()));
    }

    public static <T extends AbstractResource> T newInstance(String classUuid) {
        Supplier<? extends AbstractResource> creator = FACTORY.get(classUuid);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown resource classUuid = " + classUuid);
        }

        return  (T)creator.get();
    }

    public static <T extends AbstractResource> T findByClassUuid(String classUuid) {
        return (T)CATALOG.get(classUuid);
    }

    public static List<AbstractResource> getAvailable(World world) {
        List<AbstractResource> result = new ArrayList<>();

        for (AbstractResource tile : CATALOG.values()) {
            result.add(tile);
        }

        return result;
    }
}
