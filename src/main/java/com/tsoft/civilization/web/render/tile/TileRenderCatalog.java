package com.tsoft.civilization.web.render.tile;

import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.desert.Desert;
import com.tsoft.civilization.tile.base.grassland.Grassland;
import com.tsoft.civilization.tile.base.lake.Lake;
import com.tsoft.civilization.tile.base.ocean.Ocean;
import com.tsoft.civilization.tile.base.plain.Plain;
import com.tsoft.civilization.tile.base.snow.Snow;
import com.tsoft.civilization.tile.base.tundra.Tundra;
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
