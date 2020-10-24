package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.snow.Snow;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class SnowRender implements Render<Snow> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, Graphics g, int x, int y, Snow tile) {
        hexagonRender.render(context, g, x, y, new Color(h("#a1afb8")));
    }
}
