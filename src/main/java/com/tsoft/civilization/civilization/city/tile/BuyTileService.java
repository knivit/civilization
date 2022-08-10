package com.tsoft.civilization.civilization.city.tile;

import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.action.ActionFailureResult;
import com.tsoft.civilization.action.ActionSuccessResult;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.L10nCity;
import com.tsoft.civilization.civilization.city.event.TileBoughtEvent;
import com.tsoft.civilization.util.Point;

import java.util.Collections;
import java.util.List;

public class BuyTileService {

    public static final ActionSuccessResult TILE_BOUGHT = new ActionSuccessResult(L10nCity.TILE_BOUGHT);

    public static final ActionFailureResult INVALID_CITY = new ActionFailureResult(L10nCity.INVALID_CITY);
    public static final ActionFailureResult INVALID_LOCATION = new ActionFailureResult(L10nCity.INVALID_LOCATION);
    public static final ActionFailureResult ALREADY_MINE = new ActionFailureResult(L10nCity.ALREADY_MINE);
    public static final ActionFailureResult NOT_ENOUGH_GOLD = new ActionFailureResult(L10nCity.NOT_ENOUGH_GOLD);

    public ActionAbstractResult buyTile(City city, Point location, int price) {
        if (city == null || city.isDestroyed()) {
            return INVALID_CITY;
        }
        
        if (location == null) {
            return INVALID_LOCATION;
        }
        
        if (city.getTileService().getLocations().contains(location)) {
            return ALREADY_MINE;
        }
        
        if (city.getSupply().getGold() < price) {
            return NOT_ENOUGH_GOLD;
        }

        city.getSupplyService().addExpenses(Supply.builder().gold(price).build());
        city.getTileService().addLocations(List.of(location));

        city.getCivilization().addEvent(TileBoughtEvent.builder()
            .cityName(city.getName())
            .gold(price)
            .build());
        
        return TILE_BOUGHT;
    }

    public ActionAbstractResult canBuyTile(City city) {
        return NOT_ENOUGH_GOLD;
    }

    public List<Point> getLocationsToBuy(City city) {
        return Collections.emptyList();
    }
}
