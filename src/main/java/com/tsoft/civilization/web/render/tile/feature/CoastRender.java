package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class CoastRender implements Render<Coast> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, Graphics g, int x, int y, Coast feature) {
        hexagonRender.render(context, g, x, y, new Color(h("#274545")));
    }
}
