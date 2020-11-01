package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.marsh.Marsh;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.ImageRender;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;

public class MarshRender implements Render<Marsh> {

    private final ImageRender image = new ImageRender("web/images/status/tiles/marsh.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Marsh objToRender) {
        image.render(graphicsContext, x, y);
    }
}

