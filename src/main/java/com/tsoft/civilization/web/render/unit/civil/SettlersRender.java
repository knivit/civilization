package com.tsoft.civilization.web.render.unit.civil;

import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.ImageRender;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;

public class SettlersRender implements Render<Settlers> {

    private final ImageRender image = new ImageRender("web/images/status/units/settler.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Settlers objToRender) {
        image.render(graphicsContext, x + 20, y + 30);
    }
}
