package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.lake.Lake;
import com.tsoft.civilization.web.render.*;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class LakeRender implements Render<Lake> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final ImageRender image = new ImageRender("web/images/status/tiles/lake.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Lake objToRender) {
        hexagonRender.render(context, graphics, tileInfo, new Color(h("#546a68")));
        image.render(context, graphics, tileInfo);
    }
}
