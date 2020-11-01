package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.jungle.Jungle;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.ImageRender;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;

public class JungleRender implements Render<Jungle> {

    private final ImageRender image = new ImageRender("web/images/status/tiles/jungle.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Jungle objToRender) {
        image.render(graphicsContext, x, y);
    }
}

