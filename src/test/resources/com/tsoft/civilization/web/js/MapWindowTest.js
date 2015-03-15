module("MapWindow.js", {
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
    }
});

test("getRoughLeftUpColRow", function() {
    deepEqual(mapWindow.getRoughLeftUpColRow(0, 0), { col: 0, row: 0});
});

test("getLeftUpColRow (0, 0)", function() {
    mapWindow.moveLeftUpXY(0, 0);

    deepEqual(mapWindow.getLeftUpColRow(0, 0), { col: 9, row: 9 });
    deepEqual(mapWindow.getLeftUpColRow(20, 0), { col: 0, row: 9 });
    deepEqual(mapWindow.getLeftUpColRow(39, 0), { col: 0, row: 9 });
    deepEqual(mapWindow.getLeftUpColRow(40, 0), { col: 0, row: 9 });
    deepEqual(mapWindow.getLeftUpColRow(41, 0), { col: 0, row: 9 });

    deepEqual(mapWindow.getLeftUpColRow(0, 1), { col: 9, row: 9 });
    deepEqual(mapWindow.getLeftUpColRow(0, 6), { col: 9, row: 9 });
    deepEqual(mapWindow.getLeftUpColRow(0, 7), { col: 0, row: 0 });

    deepEqual(mapWindow.getLeftUpColRow(1, 1), { col: 9, row: 9 });
    deepEqual(mapWindow.getLeftUpColRow(21, 5), { col: 0, row: 0 });
    deepEqual(mapWindow.getLeftUpColRow(39, 1), { col: 0, row: 9 });

    deepEqual(mapWindow.getLeftUpColRow(0, Math.round(mapWindow.hexTopHeight) - 1), { col: 9, row: 9 });
    deepEqual(mapWindow.getLeftUpColRow(39, Math.round(mapWindow.hexTopHeight) - 1), { col: 0, row: 9 });
    deepEqual(mapWindow.getLeftUpColRow(20, 20), { col: 0, row: 0 });

    deepEqual(mapWindow.getLeftUpColRow(0, Math.round(mapWindow.hexTopHeight + mapWindow.hexMiddleHeight) + 1), { col: 9, row: 1 });
    deepEqual(mapWindow.getLeftUpColRow(20, 39), { col: 0, row: 0 });
    deepEqual(mapWindow.getLeftUpColRow(39, 39), { col: 0, row: 1 });
    deepEqual(mapWindow.getLeftUpColRow(40, 20), { col: 1, row: 0 });
});


test("getLeftUpColRow (20, 20)", function() {
    mapWindow.moveLeftUpXY(20, 20);

    deepEqual(mapWindow.getLeftUpColRow(0, 0), { col: 0, row: 0 });
    deepEqual(mapWindow.getLeftUpColRow(19, 0), { col: 0, row: 0 });
    deepEqual(mapWindow.getLeftUpColRow(20, 0), { col: 1, row: 0 });
});
