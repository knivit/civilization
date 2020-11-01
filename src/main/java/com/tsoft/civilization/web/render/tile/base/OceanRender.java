package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.ocean.Ocean;
import com.tsoft.civilization.web.render.*;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class OceanRender implements Render<Ocean> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final ImageRender image = new ImageRender("web/images/status/tiles/ocean.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Ocean objToRender) {
        hexagonRender.render(context, graphics, tileInfo,
            (objToRender.isDeepOcean()) ? new Color(h("#334456")) : new Color(h("#394958")));
        image.render(context, graphics, tileInfo);
    }
}
