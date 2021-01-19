package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.jungle.Jungle;
import com.tsoft.civilization.web.render.*;

public class JungleRender implements Render<Jungle> {

    private final IconRender icon = new IconRender("web/images/status/tiles/jungle.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Jungle objToRender) {
        icon.render(context, graphics, tileInfo);
    }
}

