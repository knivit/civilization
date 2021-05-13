"use strict";

function Improvement(improvement) {
    this.name = improvement;

         if (this.name === 'AncientRuins') this.drawImprovement = drawAncientRuins
    else if (this.name === 'Farm') this.drawImprovement = drawFarm
    else alert("Unknown improvement " + this.name);

    this.draw = function(x, y, tile) {
        this.drawImprovement.draw(x, y, tile);
    }
};
