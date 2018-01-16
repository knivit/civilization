package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.util.Point;

import java.util.Collection;
import java.util.List;

public interface CityCollection extends List<City> {
    Collection<Point> getLocations();

    City getCityById(String cityId);

    AbstractBuilding getBuildingById(String buildingId);

    City getCityAtLocation(Point location);

    CityCollection getCitiesAtLocations(Collection<Point> locations);

    CityCollection getCitiesWithActionsAvailable();

    boolean isHavingTile(Point location);

    void sortByName();
}
