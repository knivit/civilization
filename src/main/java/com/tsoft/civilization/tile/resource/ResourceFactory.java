package com.tsoft.civilization.tile.resource;

import com.tsoft.civilization.tile.resource.bonus.*;
import com.tsoft.civilization.tile.resource.luxury.*;
import com.tsoft.civilization.tile.resource.strategic.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.tsoft.civilization.tile.resource.ResourceType.*;

public class ResourceFactory {

    private static final Map<ResourceType, Supplier<? extends AbstractResource>> FACTORY = new HashMap<>();
    private static final Map<ResourceType, AbstractResource> CATALOG = new HashMap<>();

    static {
        // bonus
        FACTORY.put(BANANAS, Bananas::new);
        FACTORY.put(BISON, Bison::new);
        FACTORY.put(CATTLE, Cattle::new);
        FACTORY.put(DEER, Deer::new);
        FACTORY.put(FISH, Fish::new);
        FACTORY.put(SHEEP, Sheep::new);
        FACTORY.put(STONE, Stone::new);
        FACTORY.put(WHEAT, Wheat::new);

        // luxury
        FACTORY.put(COTTON, Cotton::new);
        FACTORY.put(SILK, Silk::new);
        FACTORY.put(SPICES, Spices::new);
        FACTORY.put(SALT, Salt::new);
        FACTORY.put(SUGAR, Sugar::new);
        FACTORY.put(WHALES, Whales::new);
        FACTORY.put(WINE, Wine::new);

        // strategic
        FACTORY.put(ALUMINIUM, Aluminium::new);
        FACTORY.put(COAL, Coal::new);
        FACTORY.put(HORSES, Horses::new);
        FACTORY.put(IRON, Iron::new);
        FACTORY.put(OIL, Oil::new);
        FACTORY.put(URANIUM, Uranium::new);

        FACTORY.forEach((k, v) -> CATALOG.put(k, v.get()));
    }

    public static <T extends AbstractResource> T newInstance(ResourceType type) {
        Supplier<? extends AbstractResource> creator = FACTORY.get(type);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown resource '" + type + "'");
        }

        return  (T)creator.get();
    }

    public static <T extends AbstractResource> T findByClassUuid(String classUuid) {
        return (T)CATALOG.get(classUuid);
    }

    public static List<AbstractResource> getAvailable(ResourceCategory category) {
        return CATALOG.values().stream()
            .filter(e -> category.equals(e.getCategory()))
            .collect(Collectors.toList());
    }
}
