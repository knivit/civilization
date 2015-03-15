"use strict";

var mapWindow = {
    // Pre-calculations
    tileWidth: 0,
    tileHeight: 0,
    tileWidth1of2: 0,
    tileHeight2of3: 0,
    tileHeight1of3: 0,
    screenKoeff: 0,
    hexTopHeight: 0,
    hexBottomHeight: 0,
    hexMiddleHeight: 0,
    hexBordersXY: { },

    mapWidthX: 0,
    mapHeightY: 0,

    // To project the map on the canvas
    x: 0,
    y: 0,
    luX: 0,
    luY: 0,
    luCol: 0,
    luRow: 0,

    // Parameters: tile's rectangle size in pixels
    init: function(tileWidth, tileHeight) {
        // tile's rectangle width and height in pixels
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tileWidth1of2 = this.tileWidth * 1/2;
        this.tileHeight2of3 = this.tileHeight * 2/3;
        this.tileHeight1of3 = this.tileHeight * 1/3;

        // tile's hexagonal height in pixels
        this.screenKoeff = Math.round((this.tileHeight / 3) / 2);
        this.hexTopHeight = this.tileHeight1of3 - this.screenKoeff;
        this.hexBottomHeight = this.hexTopHeight;
        this.hexMiddleHeight = this.tileHeight - this.hexTopHeight - this.hexBottomHeight;
        this.hexTopAndMiddleHeight = this.tileHeight - this.hexBottomHeight;

        // full size of the whole map in pixels
        this.mapWidthX = tilesMap.mapWidth * this.tileWidth;
        this.mapHeightY = tilesMap.mapHeight * (this.hexTopHeight + this.hexMiddleHeight);

        this.x = 0;
        this.y = 0;
        this.luX = 0;
        this.luY = -this.hexTopHeight;
        this.luCol = 0;
        this.luRow = 0;

        // borders of a tile
        this.hexBordersXY = [
            [0, this.hexTopHeight],
            [this.tileWidth/2, 0],
            [this.tileWidth, this.hexTopHeight],
            [this.tileWidth, this.hexTopAndMiddleHeight],
            [this.tileWidth/2, this.tileHeight],
            [0, this.hexTopAndMiddleHeight],
            [0, this.hexTopHeight]
        ];
    },

    // (dx, dy) is the offset in pixels where to move the left-up corner of the view area
    moveLeftUpXY: function(dx, dy) {
        // left-up corner of the canvas
        var nx = this.x - dx;
        if (nx < 0) nx += this.mapWidthX;
        if (nx >= this.mapWidthX) nx -= this.mapWidthX;

        var ny = this.y - dy;
        if (ny < 0) ny += this.mapHeightY;
        if (ny >= this.mapHeightY) ny -= this.mapHeightY;

        this.x = nx;
        this.y = ny;

        // left-up corner to draw the map on the canvas
        this.luRow = Math.floor(this.y / (this.hexTopHeight + this.hexMiddleHeight));
        var offy = this.y - (this.luRow * (this.hexTopHeight + this.hexMiddleHeight));
        this.luY = -this.hexTopHeight - offy;

        if ((this.luRow % 2) !== 0) {
            var offx = this.x % this.tileWidth1of2;
            var cx = Math.floor(this.x / this.tileWidth1of2);
            if ((cx % 2) !== 0) {
                this.luX = -offx;
            } else {
                this.luX = -offx - this.tileWidth1of2;
            }
            this.luCol = Math.floor((this.x + this.tileWidth1of2) / this.tileWidth) - 1;
            if (this.luCol < 0) this.luCol += tilesMap.mapWidth;
        } else {
            // even row
            this.luX = -(this.x % this.tileWidth);
            this.luCol = Math.floor(this.x / this.tileWidth);
        }

        if (config.DEBUG_MAP_DRAW) {
            console.log("x", this.x, "y", this.y, "luX", this.luX, "luY", this.luY, "luCol", this.luCol, "luRow", this.luRow);

            // check that we don't draw excess lines
            if ((this.luX < -this.tileWidth) || (this.luY < -this.tileHeight)) {
                alert("Drawing of excess rows/cols is detected, dx=" + dx + ", dy=" + dy +
                    ", x=" + this.x + ", y=" + this.y + ", luX=" + this.luX + ", luY=" + this.luY + ", luCol=" + this.luCol + ", luRow=" + this.luRow);
            }
        }
    },

    /**
     * A line is going through two points A(x1, y1) and B(x2, y2).
     * Need to find out is point C(X, Y) above the line or not.
     *
     * The line's equation is (x - x1) / (x2 - x1) = (y - y1) / (y2 - y1)
     * Replace x with X and calculate y.
     * If y > Y then C is below the line
     * if y < Y then C is above the line
     * If y = Y then C is on the line
     *
     *   / \
     *  | +-|------------> X
     *   \|/|\
     *    | | |
     *    |\ /
     *    |
     *  Y |
     */
    getLeftUpColRow: function(offX, offY) {
        var x = this.x + offX;
        var y = this.y + offY;
        if (x < 0) x = this.mapWidthX + x;
        if (y < 0) y = this.mapHeightY + y;
        if (x >= this.mapWidthX) x = x - this.mapWidthX;
        if (y >= this.mapHeightY) y = y - this.mapHeightY;

        var roughColRow = this.getRoughLeftUpColRow(x, y);
        var col = roughColRow.col;
        var row = roughColRow.row;

        var cx = Math.floor(x / this.tileWidth1of2);
        var isLeftSide = ((cx % 2) === 0);
        var isEvenRow = ((row % 2) === 0);

        if (isLeftSide) {
            if (isEvenRow) {
                if (this.isAboveLeftBottomToRightTopLineOfLeftSide(x, y)) {
                    row --;
                    if (row < 0) row = tilesMap.mapHeight - 1;
                    col --;
                    if (col < 0) col = tilesMap.mapWidth - 1;
                }
            } else {
                if (this.isAboveLeftTopToRightBottomLineOfLeftSide(x, y)) {
                    row ++;
                    if (row >= tilesMap.mapHeight) row = 0;
                    col --;
                    if (col < 0) col = tilesMap.mapWidth - 1;
                }
            }
        } else {
            if (isEvenRow) {
                if (this.isAboveLeftTopToRightBottomLineOfRightSide(x, y)) {
                    row --;
                    if (row < 0) row = tilesMap.mapHeight - 1;
                }
            } else {
                if (this.isAboveLeftBottomToRightTopLineOfRightSide(x, y)) {
                    row --;
                    if (row < 0) row = tilesMap.mapHeight - 1;
                    col ++;
                    if (col >= tilesMap.mapWidth) col = 0;
                }
            }
        }

        return { "col": col, "row": row };
    },

    // Rough calculation of col, row - (rc, rr)
    // Possible error for row is -1
    getRoughLeftUpColRow: function(x, y) {
        var row = Math.floor(y / (this.hexTopHeight + this.hexMiddleHeight));
        var col = Math.floor(x / this.tileWidth);
        if ((row % 2) === 1) {
            var cx = x - this.tileWidth1of2;
            col = Math.floor(cx / this.tileWidth);
            if (cx <= 0) col --;
            if (col < 0) col = tilesMap.mapWidth - 1;
        }
        return { "col": col, "row": row };
    },

    /*  (+)  B   |  +
     *     /     |    \
     *   A  (-)  |      +
    */
    isAboveLeftBottomToRightTopLineOfLeftSide: function(x, y) {
        var cr = this.getRoughLeftUpColRow(x, y);
        var x1 = Math.floor(x / this.tileWidth) * this.tileWidth;
        var y1 = cr.row * (this.hexTopHeight + this.hexMiddleHeight) + this.hexTopHeight;
        var x2 = x1 + this.tileWidth1of2 - 1;
        var y2 = cr.row * (this.hexTopHeight + this.hexMiddleHeight);

        y = -y; y1 = -y1; y2 = -y2;
        var n = (x - x1) / (x2 - x1) * (y2 - y1) + y1;
        return n <= y ? true : false;
    },

    /*   A  (+)  |      +
     *     \     |    /
     *  (-)  B   |  +
    */
    isAboveLeftTopToRightBottomLineOfLeftSide: function(x, y) {
        var cr = this.getRoughLeftUpColRow(x, y);
        var x1 = Math.floor(x / this.tileWidth) * this.tileWidth;
        var y1 = cr.row * (this.hexTopHeight + this.hexMiddleHeight);
        var x2 = x1 + this.tileWidth1of2 - 1;
        var y2 = cr.row * (this.hexTopHeight + this.hexMiddleHeight) + this.hexTopHeight;

        y = -y; y1 = -y1; y2 = -y2;
        var n = (x - x1) / (x2 - x1) * (y2 - y1) + y1;
        return n <= y ? true : false;
    },

    /*       +   |  A  (+)
     *     /     |    \
     *   +       | (-)  B
    */
    isAboveLeftTopToRightBottomLineOfRightSide: function(x, y) {
        var cr = this.getRoughLeftUpColRow(x, y);
        var x1 = Math.floor(x / this.tileWidth) * this.tileWidth + this.tileWidth1of2;
        var y1 = cr.row * (this.hexTopHeight + this.hexMiddleHeight);
        var x2 = x1 + this.tileWidth1of2 - 1;
        var y2 = cr.row * (this.hexTopHeight + this.hexMiddleHeight) + this.hexTopHeight;

        y = -y; y1 = -y1; y2 = -y2;
        var n = (x - x1) / (x2 - x1) * (y2 - y1) + y1;
        return n <= y ? true : false;
    },

    /*   +       | (+)  B
     *     \     |    /
     *       +   |  A  (-)
    */
    isAboveLeftBottomToRightTopLineOfRightSide: function(x, y) {
        var cr = this.getRoughLeftUpColRow(x, y);
        var x1 = Math.floor(x / this.tileWidth) * this.tileWidth + this.tileWidth1of2;
        var y1 = cr.row * (this.hexTopHeight + this.hexMiddleHeight) + this.hexTopHeight;
        var x2 = x1 + this.tileWidth1of2 - 1;
        var y2 = cr.row * (this.hexTopHeight + this.hexMiddleHeight);

        y = -y; y1 = -y1; y2 = -y2;
        var n = (x - x1) / (x2 - x1) * (y2 - y1) + y1;
        return n <= y ? true : false;
    },

    // Returns global (x, y) of the given Tile in pixels
    getTileXY: function(col, row) {
        var x = col * this.tileWidth;
        if ((row % 2) === 1) {
            x = x + this.tileWidth1of2;
        }
        var y = Math.round(row * (this.hexTopHeight + this.hexMiddleHeight));

        return { "x": x, "y": y };
    }
}
