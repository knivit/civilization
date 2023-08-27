package com.tsoft.civilization.civilization.city.action;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.city.tile.TileCost;
import com.tsoft.civilization.civilization.city.ui.CityTileActionResults;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.event.TileBoughtEvent;
import com.tsoft.civilization.economic.TileService;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BuyTileAction {

    private final TileService tileService = new TileService();

    public ActionAbstractResult buyTile(City city, Point location) {
        if (city == null || city.isDestroyed()) {
            return CityTileActionResults.INVALID_CITY;
        }
        
        if (location == null) {
            return CityTileActionResults.INVALID_LOCATION;
        }
        
        if (city.getTileService().getTerritory().contains(location)) {
            return CityTileActionResults.ALREADY_MINE;
        }

        Double price = calcTilePrice(city, location);
        if (price == null) {
            return CityTileActionResults.CANT_BUY_TILE;
        }
        
        if (city.getSupplyService().getSupply().getGold() < price) {
            return CityTileActionResults.NOT_ENOUGH_GOLD;
        }

        city.getSupplyService().addExpenses(Supply.builder().gold(-price).build());
        city.getTileService().addLocation(location);

        city.getCivilization().addEvent(TileBoughtEvent.builder()
            .cityName(city.getName())
            .gold(price)
            .build());
        
        return CityTileActionResults.TILE_BOUGHT;
    }

    public ActionAbstractResult canBuyTile(City city) {
        Supply supply = city.getSupplyService().calcSupply();
        if (supply.getGold() <= 0) {
            return CityTileActionResults.NOT_ENOUGH_GOLD;
        }

        List<TileCost> tiles = getLocationsToBuy(city);
        if (tiles.isEmpty()) {
            return CityTileActionResults.NO_TILES_TO_BUY;
        }

        return CityTileActionResults.CAN_BUY_TILE;
    }

    public List<TileCost> getLocationsToBuy(City city) {
        TilesMap tilesMap = city.getTileService().getTilesMap();
        Set<Point> around = tilesMap.getLocationsAroundTerritory(city.getTileService().getTerritory(), 2);

        List<TileCost> result = new ArrayList<>();
        for (Point location : around) {
            Double price = calcTilePrice(city, location);
            if (price != null) {
                result.add(new TileCost(location, (int) Math.round(price)));
            }
        }

        return result;
    }

    private Double calcTilePrice(City city, Point location) {
        TilesMap tilesMap = city.getTileService().getTilesMap();
        boolean canBuy = tilesMap.canBeTerritory(location);
        if (!canBuy) {
            return null;
        }

        AbstractTerrain tile = tilesMap.getTile(location);

        Supply tileSupply = tileService.calcSupply(tile);
        double food = Math.max(1, tileSupply.getFood());
        double production = Math.max(1, tileSupply.getProduction());
        double gold = Math.max(1, tileSupply.getGold());
        return (30.0 * gold) + (20.0 * production) + (10.0 * food);
    }
}
