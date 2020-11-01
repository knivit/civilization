package com.tsoft.civilization.web.render.tile;

import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.RenderTileInfo;

import java.awt.*;

import static com.tsoft.civilization.web.render.Render.r;

public class HexagonRender {

    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Color color) {
        graphics.getG().setColor(color);

        int x = tileInfo.x;
        int y = tileInfo.y;
        float[] ox = context.getHexBordersX();
        float[] oy = context.getHexBordersY();
        graphics.getG().fillPolygon(
            new int[] { r(x + ox[0]), r(x + ox[1]), r(x + ox[2]), r(x + ox[3]), r(x + ox[4]), r(x + ox[5]) },
            new int[] { r(y + oy[0]), r(y + oy[1]), r(y + oy[2]), r(y + oy[3]), r(y + oy[4]), r(y + oy[5]) },
            6);
/*
        if (config.isShowTileBorders) {
            drawMap.ctx.strokeStyle = "#ffffff";
            drawMap.ctx.lineWidth = 1;
            drawMap.ctx.stroke();
        }*/
    }
}
