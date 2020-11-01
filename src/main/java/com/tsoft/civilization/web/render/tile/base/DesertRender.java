package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.desert.Desert;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class DesertRender implements Render<Desert> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Desert objToRender) {
        hexagonRender.render(context, graphicsContext, x, y, new Color(h("#c5a376")));
    }
}
