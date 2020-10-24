package com.tsoft.civilization.web.render.tile.base;

import com.tsoft.civilization.tile.base.lake.Lake;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;

import java.awt.*;

public class LakeRender implements Render<Lake> {

    private final HexagonRender hexagonRender = new HexagonRender();

    @Override
    public void render(RenderContext context, Graphics g, int x, int y, Lake tile) {
        hexagonRender.render(context, g, x, y, new Color(h("#546a68")));
    }
}
