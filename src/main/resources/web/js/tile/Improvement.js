"use strict";

function Improvement(improvement) {
    this.name = improvement;

         if (this.name === 'Mine') this.drawImprovement = drawMine
    else if (this.name === 'Farm') this.drawImprovement = drawFarm
    else alert("Unknown improvement " + this.name);

    this.draw = function(x, y, tile) {
        this.drawImprovement.draw(x, y, tile);
    }
};
