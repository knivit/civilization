package com.tsoft.civilization.web.render.tile;

import com.tsoft.civilization.web.render.RenderContext;

import java.awt.*;

public class HexagonRender {

    public void render(RenderContext context, Graphics g, int x, int y, Color color) {
        int w = context.getTileWidth();
        int h = context.getTileHeight();
        g.setColor(color);

        g.fillPolygon(new int[] {x, x + w/2, x + w, x + w, x + w/2, x},
            new int[] {r(y + h*2.0/3.0), y + h, r(y + h*2.0/3.0), r(y + h*1.0/3.0), y, r(y + h*1.0/3.0)}, 6);
/*
        if (config.isShowTileBorders) {
            drawMap.ctx.strokeStyle = "#ffffff";
            drawMap.ctx.lineWidth = 1;
            drawMap.ctx.stroke();
        }*/
    }

    private int r(double val) {
        return (int)(Math.round(val));
    }
}
