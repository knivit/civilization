package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.HexagonRender;
import com.tsoft.civilization.web.render.tile.ImageRender;

import java.awt.*;

public class MountainRender implements Render<Mountain> {

    private final HexagonRender hexagonRender = new HexagonRender();
    private final ImageRender mountainImage = new ImageRender("/images/map/mountain.jpg");

    @Override
    public void render(RenderContext context, Graphics g, int x, int y, Mountain feature) {
        hexagonRender.render(context, g, x, y, new Color(h("#5d5f5e")));
        mountainImage.render(g, x + 20, y + 30);
    }
}
