package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.floodplain.FloodPlain;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.ImageRender;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;

public class FloodPlainRender implements Render<FloodPlain> {

    private final ImageRender image = new ImageRender("web/images/status/tiles/floodplain.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, FloodPlain objToRender) {
        image.render(graphicsContext, x, y);
    }
}

