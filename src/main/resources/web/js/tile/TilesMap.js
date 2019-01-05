"use strict";

var tilesMap = {
    mapWidth: {},
    mapHeight: {},
    tilesMap: [],

    create: function(width, height, tiles) {
        this.mapWidth = new Number(width);
        this.mapHeight = new Number(height);
        if ((this.mapWidth * this.mapHeight) !== tiles.length) {
            alert("Invalid response: width=" + this.width + ", height=" + this.height + ", tiles.length=" + tiles.length);
            return;
        }
    
        this.tilesMap = utils.createArray(this.mapWidth, this.mapHeight);
        this.update(tiles);
    },

    update: function(tiles) {
        var n = 0;
        for (var row = 0; row < this.mapHeight; row ++) {
            for (var col = 0; col < this.mapWidth; col ++) {
                this.tilesMap[col][row] = new Tile(col, row, tiles[n]);
                n ++;
            }
        }
    },

    getTile: function(col, row) {
        // the map is cyclic
        if (col < 0) col += this.mapWidth;
        if (col >= this.mapWidth) col -= this.mapWidth;

        if (row < 0) row += this.mapHeight;
        if (row >= this.mapHeight) row -= this.mapHeight;

        return this.tilesMap[col][row];
    },

    //  1 2
    // 0 * 3
    //  5 4
    getNearByTiles: function(col, row) {
        return [
            tilesMap.getTile(col - 1, row),
            tilesMap.getTile(col - 1, row - 1),
            tilesMap.getTile(col, row - 1),
            tilesMap.getTile(col + 1, row),
            tilesMap.getTile(col, row + 1),
            tilesMap.getTile(col - 1, row + 1)
        ];
    },

    getTilesAround: function(col, row, radius) {
        var result = [];

        // up
        var c = col - radius;
        var r = row;
        for (var i = 0; i < radius; i ++) {
            var len = radius + radius + 1 - i;
            for (var k = 0; k < len; k ++) {
                result.push(this.getTile(c + k, r - i));
            }
            if (((r - i) % 2) !== 0) col ++;
        }

        // down
        var c = col - radius;
        var r = row + 1;
        for (var i = 1; i < radius; i ++) {
            var len = radius + radius + 1 - i;
            for (var k = 0; k < len; k ++) {
                result.push(this.getTile(c + k, r + i));
            }
            if (((r + i) % 2) !== 0) col ++;
        }

        return result;
    }
};
