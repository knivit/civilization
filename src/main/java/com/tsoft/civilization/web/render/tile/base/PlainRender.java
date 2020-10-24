package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.plain.Plain;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class PlainRender implements Render<Plain> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, Graphics g, int x, int y, Plain tile) {
        hexagonRender.render(context, g, x, y, new Color(h("#a89352")));
    }
}
