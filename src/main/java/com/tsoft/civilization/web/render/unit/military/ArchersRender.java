package com.tsoft.civilization.web.render.unit.military;

import com.tsoft.civilization.unit.catalog.archers.Archers;
import com.tsoft.civilization.web.render.*;

public class ArchersRender implements Render<Archers> {

    private final IconRender icon = new IconRender("web/images/status/units/archer.png");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Archers objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}
