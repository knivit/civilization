"use strict";

var drawMountainFeature = {
    img: new Image(),

    draw: function(x, y, tile) {
        drawTile.drawHexagon(x, y, "#fd5f5e");
    }
};

drawMountainFeature.img.src = "";