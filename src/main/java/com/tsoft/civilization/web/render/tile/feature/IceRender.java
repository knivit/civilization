package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class IceRender implements Render<Ice>{

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Ice objToRender) {
        hexagonRender.render(context, graphicsContext, x, y, new Color(h("#cfe2f3")));
    }
}
