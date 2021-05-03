package com.tsoft.civilization.world;

import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.web.view.JsonBlock;
import com.tsoft.civilization.improvement.city.CityView;
import com.tsoft.civilization.tile.base.AbstractTileView;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationView;

public class WorldView {
    private final World world;

    public WorldView(World world) {
        this.world = world;
    }

    public JsonBlock getJson() {
        JsonBlock worldBlock = new JsonBlock();

        // map definition
        int width = world.getTilesMap().getWidth();
        int height = world.getTilesMap().getHeight();
        worldBlock.addParam("width", width);
        worldBlock.addParam("height", height);

        // tiles and their features
        worldBlock.startArray("tiles");
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x ++) {
                AbstractTile tile = world.getTilesMap().getTile(x, y);
                AbstractTileView tileView = tile.getView();
                worldBlock.addElement(tileView.getJson(tile).getText());
            }
        }
        worldBlock.stopArray();

        // civilizations
        worldBlock.startArray(("civilizations"));
        for (Civilization civilization : world.getCivilizations()) {
            CivilizationView civilizationView = civilization.getView();
            worldBlock.addElement(civilizationView.getJson(civilization).getText());
        }
        worldBlock.stopArray();

        // units
        worldBlock.startArray("units");
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x ++) {
                AbstractTile tile = world.getTilesMap().getTile(x, y);
                UnitList units = world.getUnitsAtLocation(tile.getLocation());
                for (AbstractUnit unit : units) {
                    JsonBlock unitBlock = unit.getView().getJson(unit);
                    worldBlock.addElement(unitBlock.getText());
                }
            }
        }
        worldBlock.stopArray();

        // cities
        worldBlock.startArray(("cities"));
        for (Civilization civilization : world.getCivilizations()) {
            civilization.getCityService().applyToAll(city -> {
                CityView cityView = city.getView();
                worldBlock.addElement(cityView.getJson(city).getText());
            });
        }
        worldBlock.stopArray();

        return worldBlock;
    }
}
