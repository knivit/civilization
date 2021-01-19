package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.ocean.Ocean;
import com.tsoft.civilization.web.render.*;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class OceanRender implements Render<Ocean> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final IconRender icon = new IconRender("web/images/status/tiles/ocean.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Ocean objToRender) {
        hexagonRender.fill(context, graphics, tileInfo,
            (objToRender.isDeepOcean()) ? new Color(h("#334456")) : new Color(h("#394958")));
        icon.render(context, graphics, tileInfo);
    }
}
