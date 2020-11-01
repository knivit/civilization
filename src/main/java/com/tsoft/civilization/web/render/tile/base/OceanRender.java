package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.ocean.Ocean;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class OceanRender implements Render<Ocean> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Ocean objToRender) {
        hexagonRender.render(context, graphicsContext, x, y,
            (objToRender.isDeepOcean()) ? new Color(h("#334456")) : new Color(h("#394958")));
    }
}
