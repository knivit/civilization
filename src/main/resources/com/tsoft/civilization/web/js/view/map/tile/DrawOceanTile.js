"use strict";

var drawOceanTile = {
    img: new Image(),

    draw: function(x, y, tile) {
        // if as far as 3 tiles around is ocean, then this tile is deep ocean
        var tilesAround = tilesMap.getTilesAround(tile.col, tile.row, 2);
        var isDeepOcean = true;
        for (var i = 0; i < tilesAround.length; i ++) {
            if (!tilesAround[i].isOcean) {
                isDeepOcean = false;
                break;
            }
        }
        drawTile.drawHexagon(x, y, (isDeepOcean ? "#334456" : "#394958")); // 485867 697988 394958
    }
};

drawOceanTile.img.src = "";