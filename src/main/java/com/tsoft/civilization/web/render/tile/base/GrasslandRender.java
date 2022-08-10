package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.web.render.*;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class GrasslandRender implements Render<Grassland> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final IconRender icon = new IconRender("web/images/status/tiles/grassland.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Grassland objToRender) {
        hexagonRender.fill(context, graphics, tileInfo, new Color(h("#6a7a39")));
        icon.render(context, graphics, tileInfo);
    }
}
