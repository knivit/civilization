package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.unit.action.AttackAction;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.util.Point;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CityList implements Iterable<City> {
    private List<City> cities = new ArrayList<>();
    private boolean isUnmodifiable;

    public CityList() { }

    public CityList(List<City> cities) {
        Objects.requireNonNull(cities);
        this.cities = cities;
    }

    public List<City> getList() {
        return new ArrayList<>(cities);
    }

    public CityList unmodifiableList() {
        CityList list = new CityList();
        list.cities.addAll(cities);
        list.isUnmodifiable = true;
        return list;
    }

    @Override
    public Iterator<City> iterator() {
        return cities.iterator();
    }

    public Stream<City> stream() {
        return cities.stream();
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The list is unmodifiable");
        }
    }

    public boolean isEmpty() {
        return cities.isEmpty();
    }

    public int size() {
        return cities.size();
    }

    public City getAny() {
        return (cities.size() == 0) ? null : cities.get(0);
    }

    public CityList add(City city) {
        checkIsUnmodifiable();
        cities.add(city);
        return this;
    }

    public CityList add(CityList other) {
        checkIsUnmodifiable();
        if (other != null && !other.isEmpty()) {
            cities.addAll(other.cities);
        }
        return this;
    }

    public CityList remove(City city) {
        checkIsUnmodifiable();
        cities.remove(city);
        return this;
    }

    public City getCityById(String cityId) {
        Objects.requireNonNull(cityId, "cityId can't be null");
        return cities.stream().filter(e -> e.getId().equals(cityId)).findAny().orElse(null);
    }

    public AbstractBuilding getBuildingById(String buildingId) {
        for (City city : cities) {
            AbstractBuilding building = city.getBuildingById(buildingId);
            if (building != null) {
                return building;
            }
        }
        return null;
    }

    public City getCityAtLocation(Point location) {
        for (City city : cities) {
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
        for (City city : cities) {
            if (locations.contains(city.getLocation())) {
                result.add(city);
            }
        }
        return result;
    }

    public CityList getCitiesWithActionsAvailable() {
        CityList cities = new CityList();
        for (City city : cities) {
            boolean canAttack = (city.getPassScore() > 0) && !AttackAction.getTargetsToAttack(city).isEmpty();
            if (!city.isDestroyed() && (canAttack || city.canStartConstruction())) {
                cities.add(city);
            }
        }
        return cities;
    }

    public boolean isHavingTile(Point location) {
        for (City city : cities) {
            if (city.isHavingTile(location)) {
                return true;
            }
        }
        return false;
    }
}
