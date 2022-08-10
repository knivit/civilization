package com.tsoft.civilization.unit.civil.citizen;

import com.tsoft.civilization.tile.terrain.*;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.lake.Lake;
import com.tsoft.civilization.tile.terrain.ocean.Ocean;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.snow.Snow;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;
import com.tsoft.civilization.tile.feature.AbstractFeature;
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

public final class CitizenPlacementTable {

    private CitizenPlacementTable() { }

    private static final Map<Class<? extends AbstractTerrain>, Boolean> tiles = new HashMap<>();

    static {
        tiles.put(Desert.class, true);
        tiles.put(Grassland.class, true);
        tiles.put(Lake.class, true);
        tiles.put(Ocean.class, true);
        tiles.put(Plains.class, true);
        tiles.put(Snow.class, true);
        tiles.put(Tundra.class, true);
    }

    private static final Map<Class<? extends AbstractFeature>, Boolean> features = new HashMap<>();

    static {
        features.put(Atoll.class, true);
        features.put(Coast.class, true);
        features.put(Fallout.class, false);
        features.put(FloodPlain.class, true);
        features.put(Forest.class, true);
        features.put(Hill.class, true);
        features.put(Ice.class, false);
        features.put(Jungle.class, true);
        features.put(Marsh.class, true);
        features.put(Mountain.class, false);
        features.put(Oasis.class, true);
    }

    public static boolean canPlaceCitizen(AbstractTerrain tile) {
        if (!tiles.get(tile.getClass())) {
            return false;
        }

        for (AbstractFeature feature : tile.getFeatures()) {
            if (!features.get(feature.getClass())) {
                return false;
            }
        }

        return true;
    }
}
