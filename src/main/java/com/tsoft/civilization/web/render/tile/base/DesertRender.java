package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.tile.desert.Desert;
import com.tsoft.civilization.web.render.*;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class DesertRender implements Render<Desert> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final IconRender icon = new IconRender("web/images/status/tiles/desert.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Desert objToRender) {
        hexagonRender.fill(context, graphics, tileInfo, new Color(h("#c5a376")));
        icon.render(context, graphics, tileInfo);
    }
}
