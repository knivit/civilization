"use strict";

function Feature(feature) {
    this.name = feature.name;

    if (this.name === 'a') this.drawFeature = drawAtollFeature;
    if (this.name === 'F') this.drawFeature = drawFalloutFeature;
    if (this.name === 'n') this.drawFeature = drawFloodPlainFeature;
    if (this.name === 'f') this.drawFeature = drawForestFeature;
    if (this.name === 'h') this.drawFeature = drawHillFeature;
    if (this.name === 'j') this.drawFeature = drawJungleFeature;
    if (this.name === 'm') this.drawFeature = drawMarshFeature;
    if (this.name === 'o') this.drawFeature = drawOasisFeature;

    this.draw = function(x, y, tile) {
        this.drawFeature.draw(x, y, tile);
    }
};
