package com.tsoft.civilization.web.render.unit.civil;

import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.web.render.*;

public class WorkersRender implements Render<Workers> {

    private final ImageRender image = new ImageRender("web/images/status/units/worker.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Workers objToRender) {
        image.render(context, graphics, tileInfo);
    }
}
