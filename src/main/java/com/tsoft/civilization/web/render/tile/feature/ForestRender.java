package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.web.render.*;

public class ForestRender implements Render<Forest> {

    private final IconRender icon = new IconRender("web/images/status/tiles/forest.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Forest objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}
