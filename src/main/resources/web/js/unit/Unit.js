"use strict";

function Unit(col, row, unit) {
    this.col = col;
    this.row = row;
    this.name = unit.name;
    this.civ = unit.civ;

         if (this.name === 'Archers') this.drawUnit = drawArchersUnit
    else if (this.name === 'GreatArtist') this.drawUnit = drawGreatArtistUnit
    else if (this.name === 'GreatEngineer') this.drawUnit = drawGreatEngineerUnit
    else if (this.name === 'GreatGeneral') this.drawUnit = drawGreatGeneralUnit
    else if (this.name === 'GreatMerchant') this.drawUnit = drawGreatMerchantUnit
    else if (this.name === 'GreatScientist') this.drawUnit = drawGreatScientistUnit
    else if (this.name === 'Settlers') this.drawUnit = drawSettlersUnit
    else if (this.name === 'Warriors') this.drawUnit = drawWarriorsUnit
    else if (this.name === 'Workers') this.drawUnit = drawWorkersUnit
    else alert("Unknown unit " + this.name);

    this.draw = function(x, y) {
        this.drawUnit.draw(x, y, this);
    }
};
