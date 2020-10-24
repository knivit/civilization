package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.desert.Desert;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class DesertRender implements Render<Desert> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, Graphics g, int x, int y, Desert tile) {
        hexagonRender.render(context, g, x, y, new Color(h("#c5a376")));
    }
}
