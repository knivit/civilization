package com.tsoft.civilization.web.render.unit.civil;

import com.tsoft.civilization.unit.catalog.settlers.Settlers;
import com.tsoft.civilization.web.render.*;

public class SettlersRender implements Render<Settlers> {

    private final IconRender icon = new IconRender("web/images/status/units/settlers.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Settlers objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}
