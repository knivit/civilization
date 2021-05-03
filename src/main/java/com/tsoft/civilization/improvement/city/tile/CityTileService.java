package com.tsoft.civilization.improvement.city.tile;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.world.HasHistory;

public class CityTileService implements HasHistory {

    private final TileService tileService = new TileService();

    private final City city;

    public CityTileService(City city) {
        this.city = city;
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

    }
}
