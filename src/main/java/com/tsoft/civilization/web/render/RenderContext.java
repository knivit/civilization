package com.tsoft.civilization.web.render;

import com.tsoft.civilization.util.Point;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RenderContext {

    // Canvas's size in pixels
    private float canvasWidth;
    private float canvasHeight;

    // Tile's size in pixels
    private final float tileWidth;
    private final float tileHeight;
    private float tileWidth1of2;
    private float tileHeight2of3;
    private float tileHeight1of3;

    // tile's hexagonal height in pixels
    private int screenCoeff;
    private float hexTopHeight;
    private float hexBottomHeight;
    private float hexMiddleHeight;
    private float hexTopAndMiddleHeight;

    // Upper-left corner
    private float luX;
    private float luY;
    private int luCol;
    private int luRow;

    // Map's size in tiles
    private final int mapWidth;
    private final int mapHeight;

    // Map's size in pixels
    private float mapWidthX;
    private float mapHeightY;

    // Borders of a tile's hexagon
    private float[] hexBordersX;
    private float[] hexBordersY;

    // Tiles' rendering info, a link between map (col, row) and screen (x, y)
    private List<RenderTileInfo> tilesInfo;
    private Map<Point, List<RenderTileInfo>> tilesInfoMap;

    public RenderContext(int mapWidth, int mapHeight, float tileWidth, float tileHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        buildRenderContext();
        buildRenderInfo();
    }

    private void buildRenderContext() {
        tileWidth1of2 = tileWidth * 1/2;
        tileHeight2of3 = tileHeight * 2/3;
        tileHeight1of3 = tileHeight * 1/3;

        // tile's hexagonal height in pixels
        screenCoeff = Math.round((tileHeight / 3) / 2);
        hexTopHeight = tileHeight1of3 - screenCoeff;
        hexBottomHeight = hexTopHeight;
        hexMiddleHeight = tileHeight - hexTopHeight - hexBottomHeight;
        hexTopAndMiddleHeight = tileHeight - hexBottomHeight;

        // full size of the whole map in pixels
        mapWidthX = mapWidth * tileWidth;
        mapHeightY = mapHeight * (hexTopHeight + hexMiddleHeight);

        // canvas's size in pixels
        canvasWidth = mapWidthX;
        canvasHeight = mapHeightY;

        luX = 0;
        luY = -hexTopHeight;
        luCol = 0;
        luRow = 0;

        // borders of a tile's hexagon
        //          1
        //    0/6        2
        //     5         3
        //          4
        hexBordersX = new float[] { 0, tileWidth/2, tileWidth, tileWidth, tileWidth/2, 0, 0 };
        hexBordersY = new float[] { hexTopHeight, 0, hexTopHeight, hexTopAndMiddleHeight, tileHeight, hexTopAndMiddleHeight, hexTopHeight };
    }

    // Create an array with (x,y), (col,row) info to update the canvas
    // In a hexagonal map one half of a tile can start the row and the other half (the same tile) can be at the end of row
    // The same rule for 0 row - it will be repeated at the bottom
    //
    // So, for example, for a map 3x4 tilesInfo will be:
    //     (0,0), (1,0), (2,0), (3,0),               <-- 0 row
    //  (3,1), (0,1), (1,1), (2,1), (3,1),           <-- 1/2 of the same tile at the start and at the end of odd row
    //     (0,2), (1,2), (2,2), (3,2),
    //  (3,3), (0,3), (1,3), (2,0), (3,3),
    //     (0,0), (1,0), (2,0), (3,0)                 <-- the same as 0 row
    //
    // As a result, tilesInfoMap for the same point can return 1 or 2 tile infos:
    //     (0,0) will return 1 tile info
    //     (3,1) - 2 infos (at the start and at the end of a row)
    private void buildRenderInfo() {
        tilesInfo = new ArrayList<>();
        tilesInfoMap = new HashMap<>();

        int rn = 0;
        float y = getLuY();
        int row = getLuRow();

        while (true) {
            float x = getLuX();
            int col = getLuCol();

            if ((rn % 2) != 0) {
                x = x - getTileWidth1of2();
                if ((row % 2) != 0) {
                    col --;
                    if (col < 0) {
                        col = getMapWidth() - 1;
                    }
                }
            }

            while (true) {
                RenderTileInfo tileInfo = new RenderTileInfo(Math.round(x), Math.round(y), col, row);

                tilesInfo.add(tileInfo);

                List<RenderTileInfo> infos = tilesInfoMap.computeIfAbsent(new Point(col, row), p -> new ArrayList<>());
                infos.add(tileInfo);

                x = x + getTileWidth();
                if (x >= getCanvasWidth()) {
                    break;
                }

                col ++;
                if (col >= getMapWidth()) {
                    col = 0;
                }
            }

            y = y + getHexTopHeight() + getHexMiddleHeight();
            if (y >= getCanvasHeight()) break;

            row ++;
            if (row >= getMapHeight()) row = 0;

            rn ++;
        }
    }

    /** Can return null, 1 or 2 elements list */
    public List<RenderTileInfo> getTileInfo(Point location) {
        return tilesInfoMap.get(location);
    }
}
