package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.jungle.Jungle;
import com.tsoft.civilization.web.render.*;

public class JungleRender implements Render<Jungle> {

    private final ImageRender image = new ImageRender("web/images/status/tiles/jungle.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Jungle objToRender) {
        image.render(context, graphics, tileInfo);
    }
}

