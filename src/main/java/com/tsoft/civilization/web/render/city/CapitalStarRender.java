package com.tsoft.civilization.web.render.city;

import com.tsoft.civilization.web.render.GraphicsContext;
import com.tsoft.civilization.web.render.RenderContext;
import com.tsoft.civilization.web.render.RenderTileInfo;

import java.awt.*;
import java.awt.geom.Path2D;

import static com.tsoft.civilization.web.render.Render.r;

public class CapitalStarRender {

    public void render(RenderContext context, GraphicsContext graphics, RenderTileInfo tileInfo, Color color) {
        float[] ox = context.getHexBordersX();
        float[] oy = context.getHexBordersY();

        int starWidth = r(0.2f * context.getTileWidth());
        int starHeight = r(0.2f * context.getTileWidth());

        int x = r(tileInfo.x + ox[1] - 0.5f * starWidth);
        int y = r(tileInfo.y + oy[1] - 0.1f * starHeight);

        double degrees144 = Math.toRadians(144);
        double angle = 0;

        Path2D p = new Path2D.Float();
        p.moveTo(x, y);

        for (int i = 0; i < 5; i++) {
            int x2 = x + (int) (Math.cos(angle) * starWidth);
            int y2 = y + (int) (Math.sin(-angle) * starHeight);
            p.lineTo(x2, y2);
            x = x2;
            y = y2;

            angle -= degrees144;
        }
        p.closePath();

        graphics.getG2().setColor(color);
        graphics.getG2().fill(p);

        //graphics.getG2().setColor(stroke);
        //graphics.getG2().draw(p);
    }
}
