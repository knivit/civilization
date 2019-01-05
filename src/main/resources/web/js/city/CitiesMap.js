"use strict";

var citiesMap = {
    mapWidth: {},
    mapHeight: {},
    citiesMap: [],

    create: function(width, height, cities) {
        this.mapWidth = new Number(width);
        this.mapHeight = new Number(height);

        this.update(cities);
    },

    update: function(cities) {
        // re-create the map as some cities may be destroyed etc
        this.citiesMap = utils.createArray(this.mapWidth, this.mapHeight);

        for (var i = 0; i < cities.length; i ++) {
            var col = cities[i].col;
            var row = cities[i].row;

            // mark City's territory (this is includes the City)
            for (var k = 0; k < cities[i].locations.length; k ++) {
                var cityTile = new CityTile(cities[i].civilization);
                var location = cities[i].locations[k];
                this.citiesMap[location.col][location.row] = cityTile;
            }

            // set a City
            var cityTile = this.citiesMap[col][row];
            cityTile.addCity(col, row, cities[i]);
        }

        // define territory's boundaries
        var evenDirs = [ [-1, -1], [0, -1], [1, 0], [0, 1], [-1, 1], [-1, 0] ];
        var oddDirs = [ [0, -1], [1, -1], [1, 0], [1, 1], [0, 1], [-1, 0] ];
        for (var row = 0; row < this.mapHeight; row ++) {
            for (var col = 0; col < this.mapWidth; col ++) {
                var cityTile = this.citiesMap[col][row];
                if (!cityTile) {
                    continue;
                }

                var dirs = evenDirs;
                if ((row % 2) !== 0) dirs = oddDirs;

                for (var i = 0; i < dirs.length; i ++) {
                    var c = col + dirs[i][0];
                    if (c < 0) c = this.mapWidth - 1;
                    if (c >= this.mapWidth) c = 0;

                    var r = row + dirs[i][1];
                    if (r < 0) r = this.mapHeight - 1;
                    if (r >= this.mapHeight) r = 0;

                    var ct = this.citiesMap[c][r];
                    if (ct && (ct.civilization === cityTile.civilization)) {
                        continue;
                    }

                    // mark the border
                    cityTile.markBorder(i);
                }
            }
        }
    },

    getCityTile: function(col, row) {
        return this.citiesMap[col][row];
    }
}