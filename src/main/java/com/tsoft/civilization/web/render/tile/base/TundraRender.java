package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.tundra.Tundra;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class TundraRender implements Render<Tundra> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Tundra objToRender) {
        hexagonRender.render(context, graphicsContext, x, y, new Color(h("#6d6552")));
    }
}
