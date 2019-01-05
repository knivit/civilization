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

    if (this.name === '.') this.drawTile = drawOceanTile;
    if (this.name === ',') this.drawTile = drawCoastTile;
    if (this.name === 'd') this.drawTile = drawDesertTile;
    if (this.name === 'g') this.drawTile = drawGrasslandTile;
    if (this.name === 'i') this.drawTile = drawIceTile;
    if (this.name === 'l') this.drawTile = drawLakeTile;
    if (this.name === 'M') this.drawTile = drawMountainTile;
    if (this.name === 'p') this.drawTile = drawPlainTile;
    if (this.name === 's') this.drawTile = drawSnowTile;
    if (this.name === 't') this.drawTile = drawTundraTile;

    this.draw = function(x, y) {
        this.drawTile.draw(x, y, this);
    }
};
