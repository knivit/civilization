"use strict";

function Improvement(improvement) {
    this.name = improvement.name;

         if (this.improvement === 'AncientRuins') this.drawFeature = drawAncientRuins
    else if (this.name === 'Farm') this.drawFeature = drawFarm
    else alert("Unknown feature " + this.name);

    this.draw = function(x, y, tile) {
        this.drawImprovement.draw(x, y, tile);
    }
};
