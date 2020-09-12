package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.util.UnmodifiableList;

import java.util.Collection;
import java.util.List;

public class UnmodifiableCityList extends UnmodifiableList<City> implements CityCollection {
    private CityCollection cities;

    public UnmodifiableCityList(CityCollection cities) {
        this.cities = cities;
    }

    @Override
    public Collection<Point> getLocations() {
        return cities.getLocations();
    }

    @Override
    public City getCityById(String cityId) {
        return cities.getCityById(cityId);
    }

    @Override
    public AbstractBuilding getBuildingById(String buildingId) {
        return cities.getBuildingById(buildingId);
    }

    @Override
    public City getCityAtLocation(Point location) {
        return cities.getCityAtLocation(location);
    }

    @Override
    public CityCollection getCitiesAtLocations(Collection<Point> locations) {
        return cities.getCitiesAtLocations(locations);
    }

    @Override
    public CityCollection getCitiesWithActionsAvailable() {
        return cities.getCitiesWithActionsAvailable();
    }

    @Override
    public boolean isHavingTile(Point location) {
        return cities.isHavingTile(location);
    }

    @Override
    protected List<City> getList() {
        return cities;
    }

    @Override
    public void sortByName() {
        cities.sortByName();
    }
}
