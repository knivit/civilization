package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.snow.Snow;
import com.tsoft.civilization.web.render.*;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class SnowRender implements Render<Snow> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final IconRender icon = new IconRender("web/images/status/tiles/snow.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Snow objToRender) {
        hexagonRender.fill(context, graphics, tileInfo, new Color(h("#a1afb8")));
        icon.render(context, graphics, tileInfo);
    }
}
