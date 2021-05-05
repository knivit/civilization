"use strict";

function Feature(feature) {
    this.name = feature.name;

         if (this.name === 'Atoll') this.drawFeature = drawAtollFeature
    else if (this.name === 'Fallout') this.drawFeature = drawFalloutFeature
    else if (this.name === 'FloodPlain') this.drawFeature = drawFloodPlainFeature
    else if (this.name === 'Forest') this.drawFeature = drawForestFeature
    else if (this.name === 'Hill') this.drawFeature = drawHillFeature
    else if (this.name === 'Jungle') this.drawFeature = drawJungleFeature
    else if (this.name === 'Mountain') this.drawFeature = drawMountainFeature
    else if (this.name === 'Marsh') this.drawFeature = drawMarshFeature
    else if (this.name === 'Oasis') this.drawFeature = drawOasisFeature
    else if (this.name === 'Coast') this.drawFeature = drawCoastFeature
    else alert("Unknown feature " + this.name);

    this.draw = function(x, y, tile) {
        this.drawFeature.draw(x, y, tile);
    }
};
