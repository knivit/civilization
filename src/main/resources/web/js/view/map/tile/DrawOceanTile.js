"use strict";

var drawOceanTile = {
    img: new Image(),

    draw: function(x, y, tile) {
       var isDeepOcean = (tile.isDeepOcean != undefined);
        drawTile.drawHexagon(x, y, isDeepOcean ? "#334456" : "#394958"); // 485867 697988 394958
    }
};

drawOceanTile.img.src = "";