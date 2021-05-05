package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.tile.lake.Lake;
import com.tsoft.civilization.web.render.*;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class LakeRender implements Render<Lake> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final IconRender icon = new IconRender("web/images/status/tiles/lake.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Lake objToRender) {
        hexagonRender.fill(context, graphics, tileInfo, new Color(h("#546a68")));
        icon.render(context, graphics, tileInfo);
    }
}
