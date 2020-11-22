package com.tsoft.civilization.web.render.civilization;

import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.RenderTileInfo;

import java.awt.*;

import static com.tsoft.civilization.web.render.Render.r;

public class BorderRender {

    /**
     * sides[] is a boolean array of size 6 (see TileSide for understanding about it's indexes)
     * if side[i] is true, then this is a Civilization's border
     */
    public void outline(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, boolean[] sides, Color color) {
        int x = tileInfo.x;
        int y = tileInfo.y;
        float[] ox = context.getHexBordersX();
        float[] oy = context.getHexBordersY();

        graphics.getG().setColor(color);
        graphics.getG2().setStroke(new BasicStroke(5));

        for (int i = 0; i < 6; i ++) {
            if (sides[i]) {
                int from = (i == 0) ? 5 : i;
                int to = i;

                graphics.getG2().drawLine(r(x + ox[from]), r(y + oy[from]), r(x + ox[to]), r(y + oy[to]));
            }
        }
    }
}
