package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class CoastRender implements Render<Coast> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Coast objToRender) {
        hexagonRender.render(context, graphicsContext, x, y, new Color(h("#274545")));
    }
}
