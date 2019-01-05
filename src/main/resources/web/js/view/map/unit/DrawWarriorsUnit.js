"use strict";

var drawWarriorsUnit = {
    // A circle with an axe inside
    draw: function(x, y, unit) {
        drawMap.ctx.beginPath();
        drawMap.ctx.arc(x + 24 + 12, y + mapWindow.hexTopHeight + 4 + 12, 12, 0, 2 * Math.PI, false);
        drawMap.ctx.fillStyle = drawCivilization[unit.civ].bg;
        drawMap.ctx.fill();

        drawMap.ctx.strokeStyle = drawCivilization[unit.civ].fg;
        drawMap.ctx.lineWidth = 3;
        drawMap.ctx.stroke();
    }
};


