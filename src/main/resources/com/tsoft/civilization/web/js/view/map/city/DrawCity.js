"use strict";

var drawCity = {
    capitalImg: new Image(),
    tradeImg: new Image(),

    draw: function(x, y, city) {
        // city's image
        drawMap.ctx.beginPath();
        drawMap.ctx.moveTo(x + 3, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + mapWindow.tileHeight - 3);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth - 3, y + mapWindow.tileHeight2of3 + mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth - 3, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth/2, y + 3);
        drawMap.ctx.lineTo(x + 3, y + mapWindow.tileHeight1of3 - mapWindow.screenKoeff);
        drawMap.ctx.closePath();
        drawMap.ctx.strokeStyle = drawCivilization[city.civilization].fg;
        drawMap.ctx.lineWidth = 2;
        drawMap.ctx.stroke();

        // city's title
        drawMap.ctx.beginPath();
        drawMap.ctx.moveTo(x - 10, y - 4);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth + 10, y - 4);
        drawMap.ctx.lineTo(x + mapWindow.tileWidth + 10, y + 20);
        drawMap.ctx.lineTo(x - 10, y + 20);
        drawMap.ctx.closePath();
        drawMap.ctx.fillStyle = drawCivilization[city.civilization].bg;
        drawMap.ctx.fill();
        drawMap.ctx.strokeStyle = drawCivilization[city.civilization].fg;
        drawMap.ctx.lineWidth = 3;
        drawMap.ctx.stroke();
        drawMap.ctx.lineWidth = 1;
        drawMap.ctx.fillStyle = drawCivilization[city.civilization].fg;
        drawMap.ctx.font = "italic 18pt Arial";
        drawMap.ctx.fillText(city.name, x - 2, y + 16);

        if (city.isCapital) {
            // city's state
            drawMap.ctx.beginPath();
            drawMap.ctx.moveTo(x + mapWindow.tileWidth/2 - 20, y + 20);
            drawMap.ctx.lineTo(x + mapWindow.tileWidth/2 + 20, y + 20);
            drawMap.ctx.lineTo(x + mapWindow.tileWidth/2 + 20, y + 44);
            drawMap.ctx.lineTo(x + mapWindow.tileWidth/2 - 20, y + 44);
            drawMap.ctx.closePath();
            drawMap.ctx.fillStyle = drawCivilization[city.civilization].bg;
            drawMap.ctx.fill();
            drawMap.ctx.strokeStyle = drawCivilization[city.civilization].fg;
            drawMap.ctx.lineWidth = 3;
            drawMap.ctx.stroke();

            drawMap.ctx.drawImage(drawCity.capitalImg, x + mapWindow.tileWidth/2 - 8, y + 22);
        }
    }
};

drawCity.capitalImg.src = "images/map/capital.png";
drawCity.tradeImg.src = "images/map/trade.png";

