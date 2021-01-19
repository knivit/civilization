package com.tsoft.civilization.web.render.unit.civil;

import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.web.render.*;

public class WorkersRender implements Render<Workers> {

    private final IconRender icon = new IconRender("web/images/status/units/worker.png");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Workers objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}
