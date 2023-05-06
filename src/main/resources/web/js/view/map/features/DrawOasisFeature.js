"use strict";

var drawOasisFeature = {
    img: new Image(),

    draw: function(x, y, tile) {
        drawMap.ctx.drawImage(this.img, x, y);
    }
};

drawOasisFeature.img.src = "images/map/oasis.png"
