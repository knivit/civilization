package com.tsoft.civilization.civilization.city.tile;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.civilization.city.action.CityTileActionResults;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.event.TileBoughtEvent;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BuyTileService {

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

        TilesMap tilesMap = city.getTileService().getTilesMap();
        AbstractTerrain tile = tilesMap.getTile(location);
        int price = calcTilePrice(tile);
        
        if (city.getSupply().getGold() < price) {
            return CityTileActionResults.NOT_ENOUGH_GOLD;
        }

        city.getSupplyService().addExpenses(Supply.builder().gold(price).build());
        city.getTileService().addLocations(List.of(location));

        city.getCivilization().addEvent(TileBoughtEvent.builder()
            .cityName(city.getName())
            .gold(price)
            .build());
        
        return CityTileActionResults.TILE_BOUGHT;
    }

    public ActionAbstractResult canBuyTile(City city) {
        Supply supply = city.getSupply();
        if (supply.getGold() <= 0) {
            return CityTileActionResults.NOT_ENOUGH_GOLD;
        }

        List<BuyTile> tiles = getLocationsToBuy(city);
        if (tiles.isEmpty()) {
            return CityTileActionResults.NO_TILES_TO_BUY;
        }

        return CityTileActionResults.CAN_BUY_TILE;
    }

    public List<BuyTile> getLocationsToBuy(City city) {
        TilesMap tilesMap = city.getTileService().getTilesMap();
        Set<Point> around = tilesMap.getLocationsAroundTerritory(city.getTileService().getTerritory(), 2);

        List<BuyTile> result = new ArrayList<>();
        for (Point location : around) {
            AbstractTerrain tile = tilesMap.getTile(location);

            boolean cantBuy =
                (tile.hasFeature(Mountain.class)) ||
                (tile.isOcean() && !tile.hasFeature(Coast.class));

            if (cantBuy) {
                continue;
            }

            int price = calcTilePrice(tile);
            result.add(new BuyTile(location, price));
        }

        return result;
    }

    private int calcTilePrice(AbstractTerrain tile) {
        Supply tileSupply = tileService.calcSupply(tile);
        int food = Math.max(1, tileSupply.getFood());
        int production = Math.max(1, tileSupply.getProduction());
        int gold = Math.max(1, tileSupply.getGold());
        return (30 * gold) + (20 * production) + (10 * food);
    }
}
