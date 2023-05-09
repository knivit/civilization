"use strict";

function Tile(col, row, tile) {
    this.col = col;
    this.row = row;
    this.name = tile.name;

    if (tile.features != undefined) {
        this.features = new Array(tile.features.length);
        for (var i = 0; i < tile.features.length; i ++) {
            var feature = new Feature(tile.features[i]);
            this.features[i] = feature;
        }
    }

    if (tile.improvements != undefined) {
        this.improvements = new Array(tile.improvements.length);
        for (var i = 0; i < tile.improvements.length; i ++) {
            var improvement = new Improvement(tile.improvements[i]);
            this.improvements[i] = improvement;
        }
    }

    this.isOcean = (this.name === 'Ocean');
    this.isTundra = (this.name === 'Tundra');
    this.isGrassland = (this.name === 'Grassland');

         if (this.name === 'Ocean') this.drawTile = drawOceanTile
    else if (this.name === 'Desert') this.drawTile = drawDesertTile
    else if (this.name === 'Grassland') this.drawTile = drawGrasslandTile
    else if (this.name === 'Lake') this.drawTile = drawLakeTile
    else if (this.name === 'Plains') this.drawTile = drawPlainsTile
    else if (this.name === 'Snow') this.drawTile = drawSnowTile
    else if (this.name === 'Tundra') this.drawTile = drawTundraTile
    else alert("Unknown tile " + this.name);

    this.draw = function(x, y) {
        this.drawTile.draw(x, y, this);

        if (this.improvements != undefined) {
            for (var i = 0; i < this.improvements.length; i ++) {
                this.improvements[i].draw(x, y, this);
            }
        }
    }
};
