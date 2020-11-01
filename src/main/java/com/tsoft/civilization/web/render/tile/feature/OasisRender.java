package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.oasis.Oasis;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.ImageRender;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;

public class OasisRender implements Render<Oasis> {

    private final ImageRender image = new ImageRender("web/images/status/tiles/oasis.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Oasis objToRender) {
        image.render(graphicsContext, x, y);
    }
}

