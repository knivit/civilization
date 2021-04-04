"use strict";

function Feature(feature) {
    this.name = feature.name;

         if (this.name === 'a') this.drawFeature = drawAtollFeature
    else if (this.name === 'F') this.drawFeature = drawFalloutFeature
    else if (this.name === 'n') this.drawFeature = drawFloodPlainFeature
    else if (this.name === 'f') this.drawFeature = drawForestFeature
    else if (this.name === 'h') this.drawFeature = drawHillFeature
    else if (this.name === 'j') this.drawFeature = drawJungleFeature
    else if (this.name === 'M') this.drawFeature = drawMountainFeature
    else if (this.name === 'm') this.drawFeature = drawMarshFeature
    else if (this.name === 'o') this.drawFeature = drawOasisFeature
    else if (this.name === ',') this.drawFeature = drawCoastFeature
    else alert("Unknown feature " + this.name);

    this.draw = function(x, y, tile) {
        this.drawFeature.draw(x, y, tile);
    }
};
