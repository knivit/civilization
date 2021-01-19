package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.web.render.*;

public class IceRender implements Render<Ice>{

    private final IconRender icon = new IconRender("web/images/status/tiles/ice.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Ice objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}
