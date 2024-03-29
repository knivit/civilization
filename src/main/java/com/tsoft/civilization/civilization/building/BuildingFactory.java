package com.tsoft.civilization.civilization.building;

import com.tsoft.civilization.civilization.building.catalog.granary.Granary;
import com.tsoft.civilization.civilization.building.catalog.market.Market;
import com.tsoft.civilization.civilization.building.catalog.monument.Monument;
import com.tsoft.civilization.civilization.building.catalog.palace.Palace;
import com.tsoft.civilization.civilization.building.catalog.settlement.Settlement;
import com.tsoft.civilization.civilization.building.catalog.walls.Walls;
import com.tsoft.civilization.civilization.city.City;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class BuildingFactory {

    private static final Map<String, AbstractBuilding> CATALOG = new HashMap<>();
    private static final Map<String, Supplier<AbstractBuilding>> FACTORY = new HashMap<>();

    static {
        FACTORY.put(Granary.CLASS_UUID, Granary::new);
        FACTORY.put(Market.CLASS_UUID, Market::new);
        FACTORY.put(Monument.CLASS_UUID, Monument::new);
        FACTORY.put(Palace.CLASS_UUID, Palace::new);
        FACTORY.put(Settlement.CLASS_UUID, Settlement::new);
        FACTORY.put(Walls.CLASS_UUID, Walls::new);

        FACTORY.forEach((k, v) -> CATALOG.put(k, v.get()));
    }

    private BuildingFactory() { }

    public static <T extends AbstractBuilding> T newInstance(String classUuid, City city) {
        Supplier<AbstractBuilding> creator = FACTORY.get(classUuid);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown building classUuid = " + classUuid);
        }

        T building = (T)creator.get();
        building.init(city);
        return building;
    }

    public static AbstractBuilding findByClassUuid(String classUuid) {
        return CATALOG.get(classUuid);
    }

    public static Collection<? extends AbstractBuilding> getCatalogueInstances() {
        return CATALOG.values();
    }
}
