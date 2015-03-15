"use strict";

var drawSettlersUnit = {
    // A triangle with a flag inside
    draw: function(x, y, unit) {
        drawMap.ctx.beginPath();
        drawMap.ctx.moveTo(x + 24, y + mapWindow.hexTopHeight + 4);
        drawMap.ctx.lineTo(x + 24 + 24, y + mapWindow.hexTopHeight + 4);
        drawMap.ctx.lineTo(x + 24 + 12, y + mapWindow.hexTopHeight + 4 + 24);
        drawMap.ctx.closePath();
        drawMap.ctx.fillStyle = drawCivilization[unit.civ].bg;
        drawMap.ctx.fill();

        drawMap.ctx.strokeStyle = drawCivilization[unit.civ].fg;
        drawMap.ctx.lineWidth = 3;
        drawMap.ctx.stroke();
    }
};

