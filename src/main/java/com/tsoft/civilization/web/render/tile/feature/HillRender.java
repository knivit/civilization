package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.hill.Hill;
import com.tsoft.civilization.web.render.*;

public class HillRender implements Render<Hill> {

    private final ImageRender image = new ImageRender("web/images/status/tiles/hill.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Hill objToRender) {
        image.render(context, graphics, tileInfo);
    }
}
