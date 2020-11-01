package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.ImageRender;

public class HillRender implements Render<Hill> {

    private final ImageRender image = new ImageRender("web/images/status/tiles/hill.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Hill objToRender) {
        image.render(graphicsContext, x + 10, y + 30);
    }
}
