package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.web.render.*;

public class CoastRender implements Render<Coast> {

    private final ImageRender image = new ImageRender("web/images/status/tiles/coast.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Coast objToRender) {
        image.render(context, graphics, tileInfo);
    }
}
