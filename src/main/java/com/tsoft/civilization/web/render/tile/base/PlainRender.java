package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.plain.Plain;
import com.tsoft.civilization.web.render.*;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class PlainRender implements Render<Plain> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final ImageRender image = new ImageRender("web/images/status/tiles/plain.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Plain objToRender) {
        hexagonRender.fill(context, graphics, tileInfo, new Color(h("#a89352")));
        image.render(context, graphics, tileInfo);
    }
}
