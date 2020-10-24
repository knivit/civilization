package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class IceRender implements Render <Ice>{

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, Graphics g, int x, int y, Ice feature) {
        hexagonRender.render(context, g, x, y, new Color(h("#cfe2f3")));
    }
}
