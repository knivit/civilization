"use strict";

function Unit(col, row, unit) {
    this.col = col;
    this.row = row;
    this.name = unit.name;
    this.civ = unit.civ;

    if (this.name === 'Archers') this.drawUnit = drawArchersUnit;
    if (this.name === 'GreatArtist') this.drawUnit = drawGreatArtistUnit;
    if (this.name === 'GreatEngineer') this.drawUnit = drawGreatEngineerUnit;
    if (this.name === 'GreatGeneral') this.drawUnit = drawGreatGeneralUnit;
    if (this.name === 'GreatMerchant') this.drawUnit = drawGreatMerchantUnit;
    if (this.name === 'GreatScientist') this.drawUnit = drawGreatScientistUnit;
    if (this.name === 'Settlers') this.drawUnit = drawSettlersUnit;
    if (this.name === 'Warriors') this.drawUnit = drawWarriorsUnit;
    if (this.name === 'Workers') this.drawUnit = drawWorkersUnit;

    this.draw = function(x, y) {
        this.drawUnit.draw(x, y, this);
    }
};
