package com.tsoft.civilization.web.render.unit.civil;

import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.web.render.*;

public class SettlersRender implements Render<Settlers> {

    private final ImageRender image = new ImageRender("web/images/status/units/settler.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Settlers objToRender) {
        image.render(context, graphics, tileInfo);
    }
}
