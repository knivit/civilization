"use strict";

function Tile(col, row, tile) {
    this.col = col;
    this.row = row;
    this.name = tile.name;

    this.features = new Array(tile.features.length);
    for (var i = 0; i < tile.features.length; i ++) {
        var feature = new Feature(tile.features[i]);
        this.features[i] = feature;
    }

    this.isOcean = (this.name === '.');
    this.isTundra = (this.name === 't');
    this.isGrassland = (this.name === 'g');

         if (this.name === '.') this.drawTile = drawOceanTile
    else if (this.name === 'd') this.drawTile = drawDesertTile
    else if (this.name === 'g') this.drawTile = drawGrasslandTile
    else if (this.name === 'i') this.drawTile = drawIceTile
    else if (this.name === 'l') this.drawTile = drawLakeTile
    else if (this.name === 'p') this.drawTile = drawPlainTile
    else if (this.name === 's') this.drawTile = drawSnowTile
    else if (this.name === 't') this.drawTile = drawTundraTile
    else alert("Unknown tile " + this.name);

    this.draw = function(x, y) {
        this.drawTile.draw(x, y, this);
    }
};
