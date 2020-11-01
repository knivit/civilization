package com.tsoft.civilization.web.render.tile.feature;

import com.tsoft.civilization.tile.feature.forest.Forest;
import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.Render;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.ImageRender;

public class ForestRender implements Render<Forest> {

    private final ImageRender forestOnTundraImage = new ImageRender("web/images/map/forest_tundra.jpg");
    private final ImageRender forestOnGrasslandImage = new ImageRender("web/images/map/forest_grassland.jpg");

    @Override
    public void render(RenderContext context, GraphicsContext graphicsContext, int x, int y, Forest objToRender) {
        if (objToRender.getBaseTile().isTundra()) {
            forestOnTundraImage.render(graphicsContext, x + 10, y + 30);
            return;
        }

        if (objToRender.getBaseTile().isGrassland()) {
            forestOnGrasslandImage.render(graphicsContext, x + 10, y + 30);
            return;
        }
    }
}
