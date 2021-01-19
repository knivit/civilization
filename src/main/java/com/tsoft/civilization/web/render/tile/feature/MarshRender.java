package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.marsh.Marsh;
import com.tsoft.civilization.web.render.*;

public class MarshRender implements Render<Marsh> {

    private final IconRender icon = new IconRender("web/images/status/tiles/marsh.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Marsh objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}

