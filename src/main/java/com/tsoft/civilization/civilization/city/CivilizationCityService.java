package com.tsoft.civilization.civilization.city;

import com.tsoft.civilization.L10n.L10nList;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.event.CityDestroyedEvent;
import com.tsoft.civilization.civilization.event.CreateCityEvent;
import com.tsoft.civilization.combat.BaseCombatService;
import com.tsoft.civilization.combat.CombatService;
import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.combat.CaptureService;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CivilizationCityService {

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

    public Set<Point> getLocations() {
        Set<Point> locations = new HashSet<>();
        cities.stream()
            .forEach(c -> locations.addAll(c.getLocations()));
        return locations;
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

    private static final BaseCombatService baseCombatService = new BaseCombatService();
    private static final CaptureService captureService = new CaptureService();

    public City getCityAtLocation(Point location) {
        return cities.getCityAtLocation(location);
    }

    public CityList getCitiesAtLocations(Collection<Point> locations) {
        return cities.getCitiesAtLocations(locations);
    }

    public void startYear() {
        destroyedCities = new CityList();
        cities.forEach(City::startYear);
    }

    public void stopYear() {
        cities.forEach(City::stopYear);
        supply = calcSupply();
    }

    public Supply calcSupply() {
        Supply income = Supply.EMPTY_SUPPLY;
        Supply outcome = Supply.EMPTY_SUPPLY;

        for (City city : cities) {
            income = income.add(city.getSupplyService().calcIncomeSupply());
            outcome = outcome.add(city.getSupplyService().calcOutcomeSupply());
        }
        return income.add(outcome);
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
