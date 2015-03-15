"use strict";

function CityTile(civilization) {
    this.civilization = civilization;
    this.hasCity = false;

    // 0  / \ 1
    // 5 |   | 2
    //  4 \ / 3
    this.borders = [ false, false, false, false, false, false ];

    // a City is located here
    this.addCity = function(col, row, city) {
        this.col = col;
        this.row = row;
        this.name = city.name;
        this.drawCityOnTile = drawCity;
        this.hasCity = true;
        this.isCapital = city.isCapital;
    },

    this.markBorder = function(i) {
        this.borders[i] = true;
    },

    this.drawCity = function(x, y) {
        this.drawCityOnTile.draw(x, y, this);
    },

    // Draw boundaries of the city's territory
    this.drawBoundaries = function(x, y) {
        for (var i = 0; i < this.borders.length; i ++) {
            if (!this.borders[i]) {
                continue;
            }

            var x1 = Math.round(x + mapWindow.hexBordersXY[i][0]);
            var y1 = Math.round(y + mapWindow.hexBordersXY[i][1]);
            var x2 = Math.round(x + mapWindow.hexBordersXY[i + 1][0]);
            var y2 = Math.round(y + mapWindow.hexBordersXY[i + 1][1]);
            var color = drawCivilization[this.civilization].fg;

            drawMap.ctx.beginPath();
            drawMap.ctx.moveTo(x1, y1);
            drawMap.ctx.lineTo(x2, y2);
            drawMap.ctx.strokeStyle = color;
            drawMap.ctx.lineWidth = 3;
            drawMap.ctx.stroke();

            if (config.DEBUG_DRAW_CITY) {
                console.log("line(", x1, y2, x2, y2, color, ")");
            }
        }
    }
};
