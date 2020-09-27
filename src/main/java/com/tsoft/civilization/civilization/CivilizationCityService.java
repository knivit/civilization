package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.event.Event;

import java.util.Collection;
import java.util.function.Consumer;

public class CivilizationCityService {
    private final Civilization civilization;
    private final World world;

    // Active cities and destroyed (on this step) cities
    private CityList cities = new CityList();
    private CityList destroyedCities = new CityList();

    public CivilizationCityService(Civilization civilization) {
        this.civilization = civilization;
        world = civilization.getWorld();
    }

    public void applyToAll(Consumer<City> fun) {
        for (City city : cities) {
            fun.accept(city);
        }
    }

    public City getAny() {
        return cities.isEmpty() ? null : cities.getAny();
    }

    public void clearDestroyedCities() {
        destroyedCities = new CityList();
    }

    public void step(Year year) {
        for (City city : cities) {
            city.step(year);
        }
    }

    public Supply getSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;
        for (City city : cities) {
            supply = supply.add(city.getSupply());
        }
        return supply;
    }

    public boolean isHavingTile(Point location) {
        return cities.isHavingTile(location);
    }

    public City getCityById(String cityId) {
        return cities.getCityById(cityId);
    }


    public AbstractBuilding getBuildingById(String buildingId) {
        return cities.getBuildingById(buildingId);
    }

    public CityList getCities() {
        return cities.unmodifiableList();
    }

    public int size() {
        return cities.size();
    }

    public void addCity(City city) {
        cities.add(city);
        city.setCivilization(civilization);

        world.sendEvent(new Event(Event.UPDATE_WORLD, city, L10nCity.NEW_CITY_EVENT, city.getView().getLocalizedCityName()));
    }

    public void removeCity(City city) {
        cities.remove(city);
        destroyedCities.add(city);
        world.sendEvent(new Event(Event.UPDATE_WORLD, city, L10nCity.REMOVE_CITY_EVENT, city.getView().getLocalizedCityName()));

        // civilizations is destroyed when all its cities are destroyed
        if (isDestroyed()) {
            world.sendEvent(new Event(Event.UPDATE_WORLD, this, L10nWorld.DESTROY_CIVILIZATION_EVENT, civilization.getView().getLocalizedCivilizationName()));
        }
    }

    public boolean isDestroyed() {
        return cities.isEmpty();
    }

    public City getCityAtLocation(Point location) {
        return cities.getCityAtLocation(location);
    }

    public CityList getCitiesAtLocations(Collection<Point> locations) {
        return cities.getCitiesAtLocations(locations);
    }

    public CityList getCitiesWithActionsAvailable() {
        return cities.getCitiesWithActionsAvailable();
    }

}
