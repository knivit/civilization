package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.oasis.Oasis;
import com.tsoft.civilization.web.render.*;

public class OasisRender implements Render<Oasis> {

    private final IconRender icon = new IconRender("web/images/status/tiles/oasis.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Oasis objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}

