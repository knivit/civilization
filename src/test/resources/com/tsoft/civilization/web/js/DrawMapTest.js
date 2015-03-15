load("JsUnit.js");
load("Utils.js");
load("Config.js");
load("Server.js");
load("MapWindow.js");
load("DrawMap.js");
load("DrawMap.Tile.js");
load("TilesMap.js");
load("StatusPanel.js");

var drawMapTest = {
    setup: function() {
        tilesMap.init("{ \"width\": 10, \"height\": 10, \"tiles\": \"" +
            ".........." +
            ".........." +
            ".........." +
            ".........." +
            ".........." +
            ".........." +
            ".........." +
            ".........." +
            ".........." +
            ".........." +
            "\" }");
        mapWindow.init(100, 100, 40, 40);

        drawMapTest.drawTileCallNo = -1;
        drawMapTest.drawTileParams = new Array();
        drawMap.drawTile = function(x, y, col, row) {
            drawMapTest.drawTileCallNo ++;
            var expectedParams = drawMapTest.drawTileParams[drawMapTest.drawTileCallNo];
            if (expectedParams != undefined) {
                assertEquals({ "x": x, "y": y, "col": col, "row": row }, expectedParams,
                    "Call no " + drawMapTest.drawTileCallNo + ": x=" + x + ", y=" + y + ", col=" + col + ", row=" + row);
            }
        }
    },

    test0: function() {
        mapWindow.moveLeftUpXY(0, 0);

        drawMapTest.drawTileParams[0] = { "x": -20, "y": -34, "col": 9, "row": 9 };
        drawMapTest.drawTileParams[1] = { "x": 20, "y": -34, "col": 0, "row": 9 };

        drawMapTest.drawTileParams[3] = { "x": 0, "y": 0, "col": 0, "row": 0 };
        drawMapTest.drawTileParams[5] = { "x": 80, "y": 0, "col": 2, "row": 0 };

        drawMapTest.drawTileParams[6] = { "x": -20, "y": 34, "col": 9, "row": 1 };

        drawMapTest.drawTileParams[9] = { "x": 0, "y": 67, "col": 0, "row": 2 };

        drawMap.drawTilesMap();
        assertEquals(drawMapTest.drawTileCallNo, 11); // 4 rows by 3 cols
    },

    test20: function() {
        mapWindow.moveLeftUpXY(20, 20);

        drawMapTest.drawTileParams[0] = { "x": -20, "y": -20, "col": 0, "row": 0 };
        drawMapTest.drawTileParams[1] = { "x": 20, "y": -20, "col": 1, "row": 0 };

        drawMapTest.drawTileParams[21] = { "x": -20, "y": (34 - 20), "col": 0, "row": 1 };

        drawMap.drawTilesMap();
        assertEquals(drawMapTest.drawTileCallNo, 11); // 4 rows by 3 cols
    }
}
