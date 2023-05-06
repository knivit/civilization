"use strict";

var drawMountainFeature = {
    img: new Image(),

    draw: function(x, y, tile) {
        //drawTile.drawHexagon(x, y, "#fd5f5e");
        drawMap.ctx.drawImage(this.img, x, y);
    }
};

drawMountainFeature.img.src = "images/map/mountain.png"