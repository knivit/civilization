package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10nList;
import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.event.Event;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class CivilizationCityService {
    private final Civilization civilization;
    private final World world;

    // Active cities and destroyed (on this step) cities
    private final CityList cities = new CityList();
    private CityList destroyedCities = new CityList();

    public CivilizationCityService(Civilization civilization) {
        this.civilization = civilization;
        world = civilization.getWorld();
    }

    public Stream<City> stream() {
        return cities.stream();
    }

    public CityList getCities() {
        return cities.unmodifiableList();
    }

    public int size() {
        return cities.size();
    }

    public void applyToAll(Consumer<City> fun) {
        cities.stream().forEach(fun);
    }

    public City getAny() {
        return cities.isEmpty() ? null : cities.getAny();
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

    public L10n findCityName() {
        L10n civilizationName = civilization.getView().getName();
        L10nList names = L10nCity.CITIES.get(civilizationName);
        int index = size() % names.size();
        return names.get(index);
    }

    public void addCity(City city) {
        Objects.requireNonNull(city, "city can't be null");

        cities.add(city);
        world.sendEvent(new Event(Event.UPDATE_WORLD, city, L10nCity.NEW_CITY_EVENT, city.getView().getLocalizedCityName()));
    }

    public void removeCity(City city) {
        cities.remove(city);
        destroyedCities.add(city);
        world.sendEvent(new Event(Event.UPDATE_WORLD, city, L10nCity.REMOVE_CITY_EVENT, city.getView().getLocalizedCityName()));

        // civilizations is destroyed when all its cities are destroyed
        if (cities.isEmpty()) {
            civilization.setDestroyed();
        }
    }

    public void captureCity(City city, HasCombatStrength destroyer, boolean destroyOtherUnitsAtLocation) {
        city.getCombatStrength().setDestroyed(true);
        city.setPassScore(0);

        Event worldEvent = new Event(Event.UPDATE_WORLD, this, L10nCity.CITY_WAS_CAPTURED, city.getView().getLocalizedCityName());
        world.sendEvent(worldEvent);

        Event event = new Event(Event.UPDATE_WORLD, destroyer, L10nCity.UNIT_HAS_CAPTURED_CITY);
        civilization.addEvent(event);

        // remove the city from its civilization
        city.getCivilization().cities().removeCity(city);

        // destroy all military units located in the city and capture civilians
        UnitList units = world.getUnitsAtLocation(city.getLocation());
        for (AbstractUnit unit : units) {
            if (unit.getUnitCategory().isMilitary()) {
                unit.destroyedBy(destroyer, false);
            } else {
                unit.capturedBy(destroyer);
            }
        }

        // pass the city to the winner's civilization
        city.moveToCivilization(civilization);
        civilization.cities().addCity(city);
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

    public void startYear() {
        destroyedCities = new CityList();
        cities.forEach(City::startYear);
    }

    public void stopYear() {
        cities.forEach(City::stopYear);
    }

    public Supply getSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;

        for (City city : cities) {
            supply = supply.add(city.getSupply());
        }
        return supply;
    }
}
