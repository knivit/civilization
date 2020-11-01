package com.tsoft.civilization.web.render.unit.military;

import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.ImageRender;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;

public class WarriorsRender implements Render<Warriors> {

    private final ImageRender image = new ImageRender("web/images/status/units/warrior.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Warriors objToRender) {
        image.render(graphicsContext, x + 20, y + 30);
    }
}
