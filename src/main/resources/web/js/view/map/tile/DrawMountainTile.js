"use strict";

var drawMountainTile = {
    img: new Image(),

    draw: function(x, y, tile) {
        drawTile.drawHexagon(x, y, "#5d5f5e");
        drawMap.ctx.drawImage(image.mapImages[0], x + 20, y + 30);
    }
};

drawMountainTile.img.src = "images/map/mountain.jpg";