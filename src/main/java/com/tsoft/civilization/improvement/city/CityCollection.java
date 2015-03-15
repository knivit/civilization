package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.util.Point;

import java.util.Collection;
import java.util.List;

public interface CityCollection extends List<City> {
    public Collection<Point> getLocations();

    public City getCityById(String cityId);

    public AbstractBuilding getBuildingById(String buildingId);

    public City getCityAtLocation(Point location);

    public CityCollection getCitiesAtLocations(Collection<Point> locations);

    public CityCollection getCitiesWithActionsAvailable();

    public boolean isHavingTile(Point location);

    public void sortByName();
}
