package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10nList;
import com.tsoft.civilization.civilization.event.CityCapturedEvent;
import com.tsoft.civilization.civilization.event.CityDestroyedEvent;
import com.tsoft.civilization.civilization.event.CreateCityEvent;
import com.tsoft.civilization.civilization.event.UnitHasCapturedCityEvent;
import com.tsoft.civilization.combat.CombatService;
import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CivilizationCityService implements HasSupply {

    private static final CombatService combatService = new CombatService();

    private final Civilization civilization;
    private final World world;

    // Active cities and destroyed (on this step) cities
    private final CityList cities = new CityList();
    private CityList destroyedCities = new CityList();

    @Getter
    private Supply supply = Supply.EMPTY_SUPPLY;

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

        world.sendEvent(CreateCityEvent.builder()
            .cityName(city.getName())
            .build());
    }

    public void removeCity(City city) {
        cities.remove(city);
        destroyedCities.add(city);

        world.sendEvent(CityDestroyedEvent.builder()
            .cityName(city.getName())
            .build());

        // civilizations is destroyed when all its cities are destroyed
        if (cities.isEmpty()) {
            civilization.setDestroyed();
        }
    }

    public void captureCity(City city, HasCombatStrength destroyer, boolean destroyOtherUnitsAtLocation) {
        city.destroy();
        city.setPassScore(0);

        world.sendEvent(CityCapturedEvent.builder()
            .cityName(city.getName())
            .build());

        civilization.addEvent(UnitHasCapturedCityEvent.builder()
            .unitName(destroyer.getView().getName())
            .cityName(city.getName())
            .build());

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

    @Override
    public void startYear() {
        destroyedCities = new CityList();
        cities.forEach(City::startYear);
    }

    @Override
    public void stopYear() {
        cities.forEach(City::stopYear);
        supply = calcSupply();
    }

    @Override
    public Supply calcSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;

        for (City city : cities) {
            supply = supply.add(city.calcSupply());
        }
        return supply;
    }

    public CityList getCitiesWithAvailableActions() {
        return new CityList(cities.stream()
            .filter(city -> !city.isDestroyed())
            .filter(city -> canAttack(city) || city.canStartConstruction())
            .collect(Collectors.toList()));
    }

    private boolean canAttack(City city) {
        return (city.getPassScore() > 0) && !combatService.getTargetsToAttack(city).isEmpty();
    }
}
