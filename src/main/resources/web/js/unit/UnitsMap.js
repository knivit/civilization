"use strict";

var unitsMap = {
    updateInProgress: false,

    mapWidth: {},
    mapHeight: {},
    unitsMap: [],

    create: function(width, height, units) {
        unitsMap.mapWidth = new Number(width);
        unitsMap.mapHeight = new Number(height);

        unitsMap.update(units);
    },

    update: function(units) {
        if (unitsMap.updateInProgress) {
            return;
        }

        unitsMap.updateInProgress = true;
        try {
            // re-create the map as some units may be destroyed etc
            unitsMap.unitsMap = utils.createArray(unitsMap.mapWidth, unitsMap.mapHeight);

            for (var i = 0; i < units.length; i ++) {
                var col = units[i].col;
                var row = units[i].row;
                var unit = new Unit(col, row, units[i]);
                if (unitsMap.unitsMap[col][row]) {
                    unitsMap.unitsMap[col][row].push(unit);
                } else {
                    unitsMap.unitsMap[col][row] = [ unit ];
                }
            }
        } finally {
            unitsMap.updateInProgress = false;
        }
    },

    getUnits: function(col, row) {
        var us = unitsMap.unitsMap[col][row];
        if (!us) {
            us = [];
        }
        return us;
    }
}