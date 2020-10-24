package com.tsoft.civilization.web.render;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class RenderArea {

    @RequiredArgsConstructor
    public static class RenderInfo {
        final int x;
        final int y;
        final int col;
        final int row;
    }

    @Getter
    private final List<RenderInfo> renderInfo;

    private RenderArea(List<RenderInfo> renderInfo) {
        this.renderInfo = renderInfo;
    }

    // Create an array with (x,y), (col,row) info to update the canvas
    public static RenderArea build(RenderContext context) {
        List<RenderInfo> renderInfo = new ArrayList<>();

        int rn = 0;
        float y = context.getLuY();
        int row = context.getLuRow();

        while (true) {
            float x = context.getLuX();
            int col = context.getLuCol();

            if ((rn % 2) != 0) {
                x = x - context.getTileWidth1of2();
                if ((row % 2) != 0) {
                    col --;
                    if (col < 0) {
                        col = context.getMapWidth() - 1;
                    }
                }
            }

            while (true) {
                RenderInfo drawInfo = new RenderInfo(Math.round(x), Math.round(y), col, row);
                renderInfo.add(drawInfo);

                x = x + context.getTileWidth();
                if (x >= context.getCanvasWidth()) break;

                col ++;
                if (col >= context.getMapWidth()) col = 0;
            }

            y = y + context.getHexTopHeight() + context.getHexMiddleHeight();
            if (y >= context.getCanvasHeight()) break;

            row ++;
            if (row >= context.getMapHeight()) row = 0;

            rn ++;
        }

        return new RenderArea(renderInfo);
    }
}
