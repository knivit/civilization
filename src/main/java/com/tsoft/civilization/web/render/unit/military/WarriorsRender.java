package com.tsoft.civilization.web.render.unit.military;

import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.web.render.*;

public class WarriorsRender implements Render<Warriors> {

    private final IconRender icon = new IconRender("web/images/status/units/warrior.png");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Warriors objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}
