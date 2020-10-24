package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.ocean.Ocean;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class OceanRender implements Render<Ocean> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, Graphics g, int x, int y, Ocean tile) {
        hexagonRender.render(context, g, x, y,
            (tile.isDeepOcean()) ? new Color(h("#334456")) : new Color(h("#394958")));
    }
}
