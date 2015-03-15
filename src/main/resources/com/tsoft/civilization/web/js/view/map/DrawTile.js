"use strict";

var drawTile = {
    drawHexagon: function(x, y, fillStyle) {
        drawMap.ctx.beginPath();
        drawMap.ctx.moveTo(x, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + mapWindow.tileHeight);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y);
        drawMap.ctx.lineTo(x, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.closePath();
        drawMap.ctx.fillStyle = fillStyle;
        drawMap.ctx.fill();

        if (config.isShowTileBorders) {
            drawMap.ctx.strokeStyle = "#ffffff";
            drawMap.ctx.lineWidth = 1;
            drawMap.ctx.stroke();
        }
    },

    drawSelection: function(x, y) {
        drawMap.ctx.beginPath();
        drawMap.ctx.moveTo(x, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + mapWindow.tileHeight);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y);
        drawMap.ctx.lineTo(x, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);

        drawMap.ctx.strokeStyle = "#ffffff";
        drawMap.ctx.lineWidth = 3;
        drawMap.ctx.stroke();
    },

    drawLocationToMove: function(x, y) {
        drawMap.ctx.beginPath();
        drawMap.ctx.moveTo(x + 4, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + mapWindow.tileHeight - 4);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth - 4, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth - 4, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + 4);
        drawMap.ctx.lineTo(x + 4, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + 4, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);

        drawMap.ctx.strokeStyle = "#99dda0";
        drawMap.ctx.lineWidth = 2;
        drawMap.ctx.stroke();
    },

    drawLocationToAttack: function(x, y) {
        drawMap.ctx.beginPath();
        drawMap.ctx.moveTo(x + 6, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + mapWindow.tileHeight - 6);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth - 6, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth - 6, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + 6);
        drawMap.ctx.lineTo(x + 6, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + 6, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);

        drawMap.ctx.strokeStyle = "#ed2020";
        drawMap.ctx.lineWidth = 4;
        drawMap.ctx.stroke();
    },

    drawLocationToCapture: function(x, y) {
        drawMap.ctx.beginPath();
        drawMap.ctx.moveTo(x + 6, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + mapWindow.tileHeight - 6);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth - 6, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth - 6, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + 6);
        drawMap.ctx.lineTo(x + 6, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + 6, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);

        drawMap.ctx.strokeStyle = "#edde40";
        drawMap.ctx.lineWidth = 4;
        drawMap.ctx.stroke();
    }
};
