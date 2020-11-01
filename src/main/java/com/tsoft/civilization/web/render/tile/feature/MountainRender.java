package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.web.render.*;

public class MountainRender implements Render<Mountain> {

    private final ImageRender image = new ImageRender("web/images/status/tiles/mountain.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Mountain objToRender) {
        image.render(context, graphics, tileInfo);
    }
}
