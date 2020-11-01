package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;
import com.tsoft.civilization.web.render.ImageRender;

import java.awt.*;

public class MountainRender implements Render<Mountain> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final ImageRender image = new ImageRender("web/images/map/mountain.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Mountain objToRender) {
        hexagonRender.render(context, graphicsContext, x, y, new Color(h("#5d5f5e")));
        image.render(graphicsContext, x + 20, y + 30);
    }
}
