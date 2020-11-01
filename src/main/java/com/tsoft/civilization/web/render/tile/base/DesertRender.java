package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.desert.Desert;
import com.tsoft.civilization.web.render.*;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class DesertRender implements Render<Desert> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final ImageRender image = new ImageRender("web/images/status/tiles/desert.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Desert objToRender) {
        hexagonRender.render(context, graphics, tileInfo, new Color(h("#c5a376")));
        image.render(context, graphics, tileInfo);
    }
}
