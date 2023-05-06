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

    drawLocationToMove: function(x, y, loc) {
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

    drawLocationToAttack: function(x, y, loc) {
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

    drawLocationToCapture: function(x, y, loc) {
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
    },

    drawLocationToBuy: function(x, y, loc) {
        drawMap.ctx.beginPath();
        drawMap.ctx.moveTo(x + 6, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + mapWindow.tileHeight - 6);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth - 6, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth - 6, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + 6);
        drawMap.ctx.lineTo(x + 6, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + 6, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.strokeStyle = "#ed7e60";
        drawMap.ctx.lineWidth = 4;
        drawMap.ctx.stroke();

        drawMap.ctx.beginPath();
        drawMap.ctx.moveTo(x + mapWindow.tileWidth/2 - 20, y + 20);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2 + 20, y + 20);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2 + 20, y + 44);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2 - 20, y + 44);
        drawMap.ctx.closePath();
        drawMap.ctx.fillStyle = 'white';
        drawMap.ctx.fill();
        drawMap.ctx.strokeStyle = 'yellow';
        drawMap.ctx.lineWidth = 3;
        drawMap.ctx.stroke();
        drawMap.ctx.strokeStyle = 'black';
        drawMap.ctx.lineWidth = 1;
        drawMap.ctx.fillStyle = 'black';
        drawMap.ctx.font = "italic 14pt Arial";
        drawMap.ctx.fillText(loc.price, x + mapWindow.tileWidth/2 - 16, y + 40);
    }
};
