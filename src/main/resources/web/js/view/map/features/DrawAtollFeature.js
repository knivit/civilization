"use strict";

var drawAtollFeature = {
    img: new Image(),

    draw: function(x, y, tile) {
        drawTile.drawHexagon(x, y, "#274545");
    }
};

drawAtollFeature.img.src = "";