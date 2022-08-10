package com.tsoft.civilization.tile.feature;

import com.tsoft.civilization.tile.terrain.AbstractTerrain;
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
import com.tsoft.civilization.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public final class FeatureFactory {

    private static final Map<String, Supplier<? extends AbstractFeature>> FACTORY = new HashMap<>();
    private static final Map<String, AbstractFeature> CATALOG = new HashMap<>();

    static {
        FACTORY.put(Atoll.CLASS_UUID, Atoll::new);
        FACTORY.put(Coast.CLASS_UUID, Coast::new);
        FACTORY.put(Fallout.CLASS_UUID, Fallout::new);
        FACTORY.put(FloodPlain.CLASS_UUID, FloodPlain::new);
        FACTORY.put(Forest.CLASS_UUID, Forest::new);
        FACTORY.put(Hill.CLASS_UUID, Hill::new);
        FACTORY.put(Ice.CLASS_UUID, Ice::new);
        FACTORY.put(Jungle.CLASS_UUID, Jungle::new);
        FACTORY.put(Marsh.CLASS_UUID, Marsh::new);
        FACTORY.put(Mountain.CLASS_UUID, Mountain::new);
        FACTORY.put(Oasis.CLASS_UUID, Oasis::new);

        FACTORY.forEach((k, v) -> CATALOG.put(k, v.get()));
    }

    private FeatureFactory() { }

    public static <T extends AbstractFeature> T newInstance(String classUuid, AbstractTerrain tile) {
        Supplier<? extends AbstractFeature> creator = FACTORY.get(classUuid);
        if (creator == null) {
            throw new IllegalArgumentException("Unknown terrain feature classUuid = " + classUuid);
        }

        AbstractFeature feature = (T)creator.get();
        feature.init(tile);
        return (T)feature;
    }

    public static <T extends AbstractFeature> T findByClassUuid(String classUuid) {
        return (T)CATALOG.get(classUuid);
    }

    public static List<AbstractFeature> getAvailableFeatures(World world) {
        List<AbstractFeature> result = new ArrayList<>();

        for (AbstractFeature feature : CATALOG.values()) {
            result.add(feature);
        }

        return result;
    }
}
