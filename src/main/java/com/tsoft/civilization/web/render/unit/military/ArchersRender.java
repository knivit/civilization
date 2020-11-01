package com.tsoft.civilization.web.render.unit.military;

import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.web.render.*;

public class ArchersRender implements Render<Archers> {

    private final ImageRender image = new ImageRender("web/images/status/units/archer.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Archers objToRender) {
        image.render(context, graphics, tileInfo);
    }
}
