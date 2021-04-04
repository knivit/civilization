"use strict";

var drawGrasslandTile = {
    img: new Image(),

    draw: function(x, y, tile) {
        drawTile.drawHexagon(x, y, "#6a7a39");
    }
};

drawGrasslandTile.img.src = "";