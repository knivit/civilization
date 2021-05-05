package com.tsoft.civilization.web.render.tile;

import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.desert.Desert;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.tile.lake.Lake;
import com.tsoft.civilization.tile.tile.ocean.Ocean;
import com.tsoft.civilization.tile.tile.plain.Plain;
import com.tsoft.civilization.tile.tile.snow.Snow;
import com.tsoft.civilization.tile.tile.tundra.Tundra;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderCatalog;
import com.tsoft.civilization.web.render.tile.base.*;

import java.util.HashMap;
import java.util.Map;

public class TileRenderCatalog extends RenderCatalog {

    private static final Map<Class<? extends AbstractTile>, Render<? extends AbstractTile>> TILE_RENDERS = new HashMap<>();

    static {
        TILE_RENDERS.put(Desert.class, new DesertRender());
        TILE_RENDERS.put(Grassland.class, new GrasslandRender());
        TILE_RENDERS.put(Lake.class, new LakeRender());
        TILE_RENDERS.put(Ocean.class, new OceanRender());
        TILE_RENDERS.put(Plain.class, new PlainRender());
        TILE_RENDERS.put(Snow.class, new SnowRender());
        TILE_RENDERS.put(Tundra.class, new TundraRender());
    }

    public TileRenderCatalog() {
        super(TILE_RENDERS);
    }
}
