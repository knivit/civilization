package com.tsoft.civilization.civilization.city;

import com.tsoft.civilization.util.AList;
import com.tsoft.civilization.util.Point;

import java.util.*;
import java.util.stream.Collectors;

public class CityList extends AList<City> {

    public City getCityAtLocation(Point location) {
        for (City city : list) {
            if (city.getLocation().equals(location)) {
                return city;
            }
        }
        return null;
    }

    public CityList getCitiesAtLocations(Collection<Point> locations) {
        if (locations == null || locations.isEmpty()) {
            return new CityList();
        }

        CityList result = new CityList();
        for (City city : list) {
            if (locations.contains(city.getLocation())) {
                result.add(city);
            }
        }
        return result;
    }

    public List<City> sortByName() {
        return list.stream()
            .sorted(Comparator.comparing(e -> e.getView().getLocalizedName()))
            .collect(Collectors.toList());
    }
}
