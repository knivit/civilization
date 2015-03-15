load("JsUnit.js");
load("Utils.js");
load("Config.js");
load("Server.js");
load("MapWindow.js");
load("DrawMap.js");
load("DrawMap.Tile.js");
load("TilesMap.js");
load("StatusPanel.js");

var tilesMapTest = {
    testInit: function() {
        var response = JSON.parse(xmlHttp.responseText);
        tilesMap.init(response.width, response.height, response.tiles);
    }
}
