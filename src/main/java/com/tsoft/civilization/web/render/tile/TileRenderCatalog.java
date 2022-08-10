package com.tsoft.civilization.web.render.tile;

import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.lake.Lake;
import com.tsoft.civilization.tile.terrain.ocean.Ocean;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.snow.Snow;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderCatalog;
import com.tsoft.civilization.web.render.tile.base.*;

import java.util.HashMap;
import java.util.Map;

public class TileRenderCatalog extends RenderCatalog {

    private static final Map<Class<? extends AbstractTerrain>, Render<? extends AbstractTerrain>> TILE_RENDERS = new HashMap<>();

    static {
        TILE_RENDERS.put(Desert.class, new DesertRender());
        TILE_RENDERS.put(Grassland.class, new GrasslandRender());
        TILE_RENDERS.put(Lake.class, new LakeRender());
        TILE_RENDERS.put(Ocean.class, new OceanRender());
        TILE_RENDERS.put(Plains.class, new PlainRender());
        TILE_RENDERS.put(Snow.class, new SnowRender());
        TILE_RENDERS.put(Tundra.class, new TundraRender());
    }

    public TileRenderCatalog() {
        super(TILE_RENDERS);
    }
}
