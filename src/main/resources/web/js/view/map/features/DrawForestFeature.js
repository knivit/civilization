"use strict";

var drawForestFeature = {
    imgOnTundra: new Image(),
    imgOnGrassland: new Image(),

    draw: function(x, y, tile) {
        if (tile.isTundra) {
            drawMap.ctx.drawImage(this.imgOnTundra, x + 10, y + 30);
            return;
        }

        if (tile.isGrassland) {
            drawMap.ctx.drawImage(this.imgOnGrassland, x + 10, y + 30);
            return;
        }

        alert("drawForestFeature: unknown Tile");
    }
};

drawForestFeature.imgOnTundra.src = "images/map/forest_tundra.jpg"
drawForestFeature.imgOnGrassland.src = "images/map/forest_grassland.jpg"