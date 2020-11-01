package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.floodplain.FloodPlain;
import com.tsoft.civilization.web.render.*;

public class FloodPlainRender implements Render<FloodPlain> {

    private final ImageRender image = new ImageRender("web/images/status/tiles/floodplain.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, FloodPlain objToRender) {
        image.render(context, graphics, tileInfo);
    }
}

