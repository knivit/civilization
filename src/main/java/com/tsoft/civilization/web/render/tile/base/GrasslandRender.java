package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.grassland.Grassland;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class GrasslandRender implements Render<Grassland> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Grassland objToRender) {
        hexagonRender.render(context, graphicsContext, x, y, new Color(h("#6a7a39")));
    }
}
