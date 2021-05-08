"use strict";

var drawMap = {
    canvas: {},
    ctx: {},

    // Selected tile
    selectedCol: 0,
    selectedRow: 0,

    // Locations to move an unit
    locationsToMove: [],
    isShowLocationsToMove: false,

    // locations to attack
    locationsToAttack: [],
    isShowLocationsToAttack: false,

    // locations to capture
    locationsToCapture: [],
    isShowLocationsToCapture: false,

    init: function() {
        mapWindow.init(config.TILE_WIDTH, config.TILE_HEIGHT);

        this.selectedCol = 0;
        this.selectedRow = 0;

        this.isMouseLeftButtonPressed = false;
        this.startMouseX = 0;
        this.startMouseY = 0;

        this.keyOffX = Math.round(mapWindow.tileWidth / 2);
        this.keyOffY = Math.round(mapWindow.tileHeight / 2);
    },

    setCanvas: function(canvasId) {
        this.canvas = document.getElementById(canvasId);
        this.ctx = this.canvas.getContext("2d");

        scrollMap.init(this.canvas);
    },

    onSelectTile: function(x, y) {
        var selectedColRow = mapWindow.getLeftUpColRow(x, y);
        this.selectTile(selectedColRow.col, selectedColRow.row);
    },

    selectTile: function(col, row) {
        this.selectedCol = col;
        this.selectedRow = row;

        this.drawTilesMap();

        var canSelect = !this.isShowLocationsToMove && !this.isShowLocationsToAttack && !this.isShowLocationsToCapture;
        if (canSelect) {
            client.selectTile(col, row);
        }
    },

    // Move map's window so the given (col, row) will be in the center
    scrollTo: function(col, row) {
        var viewColCount = Math.floor(this.canvas.width / mapWindow.tileWidth);
        var viewRowCount = Math.floor(this.canvas.height / (mapWindow.hexTopHeight + mapWindow.hexMiddleHeight));
        var newLeftUpCol = col - Math.round(viewColCount / 2) + 1;
        if (newLeftUpCol < 0) newLeftUpCol += tilesMap.mapWidth;
        var newLeftUpRow = row - Math.round(viewRowCount / 2);
        if (newLeftUpRow < 0) newLeftUpRow += tilesMap.mapHeight;
        var xy = mapWindow.getTileXY(newLeftUpCol, newLeftUpRow);
        var dx = mapWindow.x - xy.x;
        var dy = mapWindow.y - xy.y;

        if (config.DEBUG_MAP_DRAW) {
            var crXY = mapWindow.getTileXY(col, row);
            console.log(
               "Scroll to [", col, "=", crXY.x, row, "=", crXY.y, "]",
               "New Left-Up corner is [", newLeftUpCol, "=", xy.x, newLeftUpRow, "=", xy.y, "]",
               "Offset is [", dx, dy, "] px");
        }

        mapWindow.moveLeftUpXY(dx, dy, this.canvas.height);
        this.drawTilesMap();
    },

    // if a tile is not visible, then scroll to it
    makeTileInView: function(col, row) {
        var viewColCount = Math.floor(this.canvas.width / mapWindow.tileWidth);
        var viewRowCount = Math.floor(this.canvas.height / (mapWindow.hexTopHeight + mapWindow.hexMiddleHeight));
        var dCol = col - mapWindow.luCol;
        if (dCol < 0) dCol += tilesMap.mapWidth;
        var dRow = row - mapWindow.luRow;
        if (dRow < 0) dRow += tilesMap.mapHeight;

        this.selectedCol = col;
        this.selectedRow = row;
        if (dCol >= viewColCount || dRow >= viewRowCount) {
            this.scrollTo(col, row);
        } else {
            this.drawTilesMap();
        }
    },

    // On browser's window resize event
    redraw: function() {
        this.drawTilesMap();

        if (window["startCol"] != undefined && window["startRow"] != undefined) {
            this.scrollTo(window["startCol"], window["startRow"]);
            window["startCol"] = undefined;
            window["startRow"] = undefined;
        }
    },

    toggleLocationsToMove: function(locations) {
        this.locationsToMove = locations;
        this.isShowLocationsToMove = !this.isShowLocationsToMove;
        this.redraw();
    },

    toggleLocationsToAttack: function(locations) {
        this.locationsToAttack = locations;
        this.isShowLocationsToAttack = !this.isShowLocationsToAttack;
        this.redraw();
    },

    toggleLocationsToCapture: function(locations) {
        this.locationsToCapture = locations;
        this.isShowLocationsToCapture = !this.isShowLocationsToCapture;
        this.redraw();
    },

    hideLocationsToMove: function() {
        this.isShowLocationsToMove = false;
        this.redraw();
    },

    hideLocationsToAttack: function() {
        this.isShowLocationsToAttack = false;
        this.redraw();
    },

    hideLocationsToCapture: function() {
        this.isShowLocationsToCapture = false;
        this.redraw();
    },

    // create an array with (x,y), (col,row) info to update the canvas
    buildDrawArea: function() {
        var drawArea = [];

        var rn = 0;
        var y = mapWindow.luY;
        var row = mapWindow.luRow;
        while (true) {
            var x = mapWindow.luX;
            var col = mapWindow.luCol;

            if ((rn % 2) !== 0) {
                x = x - mapWindow.tileWidth1of2;
                if ((row % 2) !== 0) {
                    col --;
                    if (col < 0) col = tilesMap.mapWidth - 1;
                }
            }

            while (true) {
                var cell = { "x": Math.round(x), "y": Math.round(y), "col": col, "row": row };
                drawArea.push(cell);

                x = x + mapWindow.tileWidth;
                if (x >= this.canvas.width) break;

                col ++;
                if (col >= tilesMap.mapWidth) col = 0;
            }

            y = y + mapWindow.hexTopHeight + mapWindow.hexMiddleHeight;
            if (y >= this.canvas.height) break;

            row ++;
            if (row >= tilesMap.mapHeight) break;

            rn ++;
        }

        return drawArea;
    },

    drawTilesMap: function() {
        var drawArea = this.buildDrawArea();

        // In the debug mode fill the canvas by red to see problems
        if (config.DEBUG_MAP_DRAW) {
            this.ctx.fillStyle = "rgb(255,0,0)";
            this.ctx.fillRect(0, 0, this.canvas.width, this.canvas.height);
        }

        // first layer, the tiles itself
        this.drawTiles(drawArea);

        // civilizations' boundaries
        this.drawCivilizationsBoundaries(drawArea);

        // locations to move in
        this.drawLocationsToMove(drawArea);

        // locations to attack
        this.drawLocationsToAttack(drawArea);

        // locations to capture
        this.drawLocationsToCapture(drawArea);

        // selected tiles
        this.drawSelectedTiles(drawArea);

        // units
        this.drawUnits(drawArea);

        // cities
        this.drawCities(drawArea);
    },

    drawTiles: function(drawArea) {
        for (var i = 0; i < drawArea.length; i ++) {
            var cell = drawArea[i];
            this.drawTile(cell);
        }
    },

    drawTile: function(cell) {
        var tile = tilesMap.getTile(cell.col, cell.row);

        // base tile
        tile.draw(cell.x, cell.y);

        // features
        for (var i = 0; i < tile.features.length; i ++) {
            var feature = tile.features[i];
            feature.draw(cell.x, cell.y, tile);
        }

        // debug info
        if (config.DEBUG_MAP_DRAW) {
            this.ctx.fillStyle = "#000000";
            this.ctx.strokeStyle = "#000000";
            this.ctx.font = "italic 8pt Arial";
            this.ctx.fillText(cell.col + "," + cell.row, cell.x + 4, cell.y + 20);
        }
    },

    drawCivilizationsBoundaries: function(drawArea) {
        for (var i = 0; i < drawArea.length; i ++) {
            var cell = drawArea[i];
            this.drawCityBoundaries(cell);
        }
    },

    drawCityBoundaries: function(cell) {
        var cityTile = citiesMap.getCityTile(cell.col, cell.row);
        if (cityTile) {
            if (config.DEBUG_DRAW_CITY) {
                console.log("drawCityBoundaries (", cell.col, ",", cell.row, ")", cityTile.civilization, cityTile.borders);
            }
            cityTile.drawBoundaries(cell.x, cell.y);
        }
    },

    drawSelectedTiles: function(drawArea) {
        for (var i = 0; i < drawArea.length; i ++) {
            var cell = drawArea[i];
            if ((cell.col == this.selectedCol) && (cell.row == this.selectedRow)) {
                this.drawSelection(cell.x, cell.y);
            }
        }
    },

    drawSelection: function(x, y) {
        drawTile.drawSelection(x, y);
    },

    drawUnits: function(drawArea) {
        for (var i = 0; i < drawArea.length; i ++) {
            var cell = drawArea[i];
            this.drawUnit(cell);
        }
    },

    drawUnit: function(cell) {
        var units = unitsMap.getUnits(cell.col, cell.row);
        for (var i = 0; i < units.length; i ++) {
            units[i].draw(cell.x, cell.y);
        }
    },

    drawCities: function(drawArea) {
        for (var i = 0; i < drawArea.length; i ++) {
            var cell = drawArea[i];
            this.drawCity(cell);
        }
    },

    drawCity: function(cell) {
        var cityTile = citiesMap.getCityTile(cell.col, cell.row);
        if (cityTile && cityTile.hasCity) {
            if (config.DEBUG_DRAW_CITY) {
                console.log("drawCity (", cell.col, ",", cell.row, ")", cityTile.civilization, cityTile.borders);
            }
            cityTile.drawCity(cell.x, cell.y);
        }
    },

    drawLocationsToMove: function(drawArea) {
        if (this.isShowLocationsToMove) {
            this.drawLocationsToAction(drawArea, this.locationsToMove, drawTile.drawLocationToMove);
        }
    },

    drawLocationsToAttack: function(drawArea) {
        if (this.isShowLocationsToAttack) {
            this.drawLocationsToAction(drawArea, this.locationsToAttack, drawTile.drawLocationToAttack);
        }
    },

    drawLocationsToCapture: function(drawArea) {
        if (this.isShowLocationsToCapture) {
            this.drawLocationsToAction(drawArea, this.locationsToCapture, drawTile.drawLocationToCapture);
        }
    },

    drawLocationsToAction: function(drawArea, locationsToAction, drawFunction) {
        for (var i = 0; i < drawArea.length; i ++) {
            var cell = drawArea[i];
            for (var k = 0; k < locationsToAction.length; k ++) {
                var loc = locationsToAction[k];
                if ((cell.col == loc.col) && (cell.row == loc.row)) {
                    drawFunction(cell.x, cell.y);
                }
            }
        }
    }
};
