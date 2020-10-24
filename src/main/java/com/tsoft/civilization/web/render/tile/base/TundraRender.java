package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.tundra.Tundra;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class TundraRender implements Render<Tundra> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, Graphics g, int x, int y, Tundra tile) {
        hexagonRender.render(context, g, x, y, new Color(h("#6d6552")));
    }
}
