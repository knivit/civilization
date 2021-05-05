package com.tsoft.civilization.web.render.tile;

import com.tsoft.civilization.tile.feature.AbstractFeature;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.feature.floodplain.FloodPlain;
import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.tile.feature.jungle.Jungle;
import com.tsoft.civilization.tile.feature.marsh.Marsh;
import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.tile.feature.oasis.Oasis;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderCatalog;
import com.tsoft.civilization.web.render.tile.feature.*;

import java.util.HashMap;
import java.util.Map;

public class TerrainFeatureRenderCatalog extends RenderCatalog {

    private static final Map<Class<? extends AbstractFeature>, Render<? extends AbstractFeature>> TERRAIN_FEATURE_RENDERS = new HashMap<>();
    static {
        TERRAIN_FEATURE_RENDERS.put(Coast.class, new CoastRender());
        TERRAIN_FEATURE_RENDERS.put(FloodPlain.class, new FloodPlainRender());
        TERRAIN_FEATURE_RENDERS.put(Forest.class, new ForestRender());
        TERRAIN_FEATURE_RENDERS.put(Hill.class, new HillRender());
        TERRAIN_FEATURE_RENDERS.put(Ice.class, new IceRender());
        TERRAIN_FEATURE_RENDERS.put(Jungle.class, new JungleRender());
        TERRAIN_FEATURE_RENDERS.put(Marsh.class, new MarshRender());
        TERRAIN_FEATURE_RENDERS.put(Oasis.class, new OasisRender());
        TERRAIN_FEATURE_RENDERS.put(Mountain.class, new MountainRender());
    }

    public TerrainFeatureRenderCatalog() {
        super(TERRAIN_FEATURE_RENDERS);
    }
}
