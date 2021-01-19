package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.web.render.*;

public class CoastRender implements Render<Coast> {

    private final IconRender icon = new IconRender("web/images/status/tiles/coast.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Coast objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}
