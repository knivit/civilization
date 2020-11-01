package com.tsoft.civilization.web.render.unit.military;

import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.ImageRender;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;

public class ArchersRender implements Render<Archers> {

    private final ImageRender image = new ImageRender("web/images/status/units/archer.jpg");

    @Override
    public void render(RenderContext renderContext, GraphicsContext graphicsContext, int x, int y, Archers objToRender) {
        image.render(graphicsContext, x + 20, y + 30);
    }
}
