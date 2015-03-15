package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.action.unit.AttackAction;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class CityList extends ArrayList<City> implements CityCollection {
    public CityList() {
        super();
    }

    public CityList(CityCollection cities) {
        super(cities);
    }

    @Override
    public Collection<Point> getLocations() {
        Collection<Point> locations = new ArrayList<Point>(size());
        for (City city : this) {
            locations.add(city.getLocation());
        }
        return locations;
    }

    @Override
    public City getCityById(String cityId) {
        for (City city : this) {
            if (city.getId().equals(cityId)) {
                return city;
            }
        }
        return null;
    }

    @Override
    public AbstractBuilding getBuildingById(String buildingId) {
        for (City city : this) {
            AbstractBuilding building = city.getBuildingById(buildingId);
            if (building != null) {
                return building;
            }
        }
        return null;
    }

    @Override
    public City getCityAtLocation(Point location) {
        for (City city : this) {
            if (city.getLocation().equals(location)) {
                return city;
            }
        }
        return null;
    }

    @Override
    public CityCollection getCitiesAtLocations(Collection<Point> locations) {
        if (locations == null) {
            return new CityList();
        }

        CityList citiesAtLocations = new CityList();
        for (City city : this) {
            if (locations.contains(city.getLocation())) {
                citiesAtLocations.add(city);
            }
        }
        return citiesAtLocations;
    }

    @Override
    public CityCollection getCitiesWithActionsAvailable() {
        CityCollection cities = new CityList();
        for (City city : this) {
            boolean canAttack = (city.getPassScore() > 0) && !AttackAction.getTargetsToAttack(city).isEmpty();
            if (!city.isDestroyed() && (canAttack || city.canStartConstruction())) {
                cities.add(city);
            }
        }
        return cities;
    }

    @Override
    public boolean isHavingTile(Point location) {
        for (City city : this) {
            if (city.isHavingTile(location)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void sortByName() {
        Collections.sort(this, new Comparator<City>() {
            @Override
            public int compare(City city1, City city2) {
                return city1.getView().getLocalizedCityName().compareTo(city2.getView().getLocalizedCityName());
            }
        });
    }
}
