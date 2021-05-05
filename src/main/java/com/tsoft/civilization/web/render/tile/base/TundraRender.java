package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.tile.tundra.Tundra;
import com.tsoft.civilization.web.render.*;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class TundraRender implements Render<Tundra> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final IconRender icon = new IconRender("web/images/status/tiles/tundra.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Tundra objToRender) {
        hexagonRender.fill(context, graphics, tileInfo, new Color(h("#6d6552")));
        icon.render(context, graphics, tileInfo);
    }
}
