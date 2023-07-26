"use strict";

var drawMine = {
    img: new Image(),

    draw: function(x, y, tile) {
        drawMap.ctx.drawImage(this.img, x + 20, y + 30);
    }
};

drawMine.img.src = "images/map/improvements/mine.png";
