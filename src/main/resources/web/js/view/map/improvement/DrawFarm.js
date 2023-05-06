"use strict";

var drawAncientRuins = {
    img: new Image(),

    draw: function(x, y, tile) {
        drawMap.ctx.drawImage(this.img, x + 20, y + 30);
    }
};

drawAncientRuins.img.src = "images/map/improvements/farm.png";
