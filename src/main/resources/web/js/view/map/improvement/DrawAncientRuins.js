"use strict";

var drawAncientRuins = {
    img: new Image(),

    draw: function(x, y, tile) {
        drawMap.ctx.drawImage(image.mapImages[0], x + 20, y + 30);
    }
};

drawAncientRuins.img.src = "images/map/improvements/ancient_ruins.png";
