package com.tsoft.civilization.world;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCollection;
import com.tsoft.civilization.web.view.JSONBlock;
import com.tsoft.civilization.improvement.city.CityView;
import com.tsoft.civilization.tile.base.AbstractTileView;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationView;

public class WorldView {
    private World world;

    public WorldView(World world) {
        this.world = world;
    }

    public JSONBlock getJSON() {
        JSONBlock worldBlock = new JSONBlock();

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
                worldBlock.addElement(tileView.getJSON(tile).getText());
            }
        }
        worldBlock.stopArray();

        // civilizations
        worldBlock.startArray(("civilizations"));
        for (Civilization civilization : world.getCivilizations()) {
            CivilizationView civilizationView = civilization.getView();
            worldBlock.addElement(civilizationView.getJSON(civilization).getText());
        }
        worldBlock.stopArray();

        // units
        worldBlock.startArray("units");
        for (int y = 0; y < height; y ++) {
            for (int x = 0; x < width; x ++) {
                AbstractTile tile = world.getTilesMap().getTile(x, y);
                UnitCollection units = world.getUnitsAtLocation(tile.getLocation());
                for (AbstractUnit unit : units) {
                    JSONBlock unitBlock = unit.getView().getJSON(unit);
                    worldBlock.addElement(unitBlock.getText());
                }
            }
        }
        worldBlock.stopArray();

        // cities
        worldBlock.startArray(("cities"));
        for (Civilization civilization : world.getCivilizations()) {
            for (City city : civilization.getCities()) {
                CityView cityView = city.getView();
                worldBlock.addElement(cityView.getJSON(city).getText());
            }
        }
        worldBlock.stopArray();

        return worldBlock;
    }
}
