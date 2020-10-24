package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.tile.ImageRender;

import java.awt.*;

public class ForestRender implements Render<Forest> {

    private final ImageRender forestOnTundraImage = new ImageRender("/images/map/forest_tundra.jpg");
    private final ImageRender forestOnGrasslandImage = new ImageRender("/images/map/forest_grassland.jpg");

    @Override
    public void render(RenderContext context, Graphics g, int x, int y, Forest feature) {
        if (feature.getBaseTile().isTundra()) {
            forestOnTundraImage.render(g, x + 10, y + 30);
            return;
        }

        if (feature.getBaseTile().isGrassland()) {
            forestOnGrasslandImage.render(g, x + 10, y + 30);
            return;
        }
    }
}
