package com.tsoft.civilization.web.render;

import lombok.Getter;

@Getter
public class RenderContext {

    // Canvas's size in pixels
    private final float canvasWidth;
    private final float canvasHeight;

    // Tile's size in pixels
    private final float tileWidth;
    private final float tileHeight;
    private final float tileWidth1of2;
    private final float tileHeight2of3;
    private final float tileHeight1of3;

    // tile's hexagonal height in pixels
    private final int screenCoeff;
    private final float hexTopHeight;
    private final float hexBottomHeight;
    private final float hexMiddleHeight;
    private final float hexTopAndMiddleHeight;

    // Upper-left corner
    private final float luX;
    private final float luY;
    private final int luCol;
    private final int luRow;

    // Map's size in tiles
    private final int mapWidth;
    private final int mapHeight;

    // Map's size in pixels
    private final float mapWidthX;
    private final float mapHeightY;

    // Borders of a tile
    private final float hexBordersX[];
    private final float hexBordersY[];

    public RenderContext(int mapWidth, int mapHeight, float tileWidth, float tileHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

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

        // borders of a tile
        hexBordersX = new float[] { 0, tileWidth/2, tileWidth, tileWidth, tileWidth/2, 0, 0 };
        hexBordersY = new float[] { hexTopHeight, 0, hexTopHeight, hexTopAndMiddleHeight, tileHeight, hexTopAndMiddleHeight, hexTopHeight };
    }
}
