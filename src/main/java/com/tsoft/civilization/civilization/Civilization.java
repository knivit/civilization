package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingFactory;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.improvement.city.UnmodifiableCityList;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.technology.TechnologySet;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.agreement.AgreementList;
import com.tsoft.civilization.world.agreement.OpenBordersAgreement;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.event.Event;
import com.tsoft.civilization.world.event.EventsByYearMap;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Civilization {
    private final CivilizationView VIEW;

    private L10nMap name;
    private final String id = UUID.randomUUID().toString();
    private Year startYear;
    private boolean isMoved;
    private boolean isArtificialIntelligence;

    private World world;

    // Active cities and destroyed (on this step) cities
    private CityCollection cities = new CityList();
    private UnmodifiableCityList unmodifiableCities = new UnmodifiableCityList(cities);
    private CityCollection destroyedCities = new CityList();

    private CivilizationUnitService unitService;

    private HashSet<Technology> technologies = new TechnologySet();

    @Getter
    private Supply supply;

    // Agreements which this civilization has with others
    private HashMap<Civilization, AgreementList> agreements = new HashMap<>();

    // true when any event that needs score to be calculated was generated
    private EventsByYearMap events;

    private boolean isDestroyed;

    public Civilization(World world, int index) {
        this.world = world;
        if ((index < 0) || (index >= L10nCivilization.CIVILIZATIONS.size())) {
            log.warn("Invalid civilization index={}", index);
            index = 0;
        }
        name = L10nCivilization.CIVILIZATIONS.get(index);
        VIEW = new CivilizationView(name);

        startYear = world.getYear();
        events = new EventsByYearMap(world);

        // initialize civ's params
        supply = Supply.EMPTY_SUPPLY;
        unitService = new CivilizationUnitService(this);

        world.addCivilization(this);
    }

    public void addFirstUnits() {
        unitService.addFirstUnits();
    }

    public String getId() {
        return id;
    }

    public Year getStartYear() {
        return startYear;
    }

    public Year getYear() {
        return world.getYear();
    }

    public CivilizationView getView() {
        return VIEW;
    }

    public World getWorld() {
        return world;
    }

    public TilesMap getTilesMap() {
        return world.getTilesMap();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public boolean isArtificialIntelligence() {
        return isArtificialIntelligence;
    }

    public void setArtificialIntelligence(boolean artificialIntelligence) {
        isArtificialIntelligence = artificialIntelligence;
    }

    private void aiMove() {
    }

    public void nextMove() {
        if (isMoved || isDestroyed) {
            return;
        }

        addEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.MOVE_START_EVENT, getView().getLocalizedCivilizationName()));
        if (isArtificialIntelligence) {
            aiMove();
        }
        isMoved = true;

        addEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.MOVE_DONE_EVENT, getView().getLocalizedCivilizationName()));
    }

    public EventsByYearMap getEvents() {
        return events;
    }

    /** Something is happened in our civilization (a citizen was born,
     * a building was built, a unit has won etc) */
    public void addEvent(Event event) {
        events.add(event);
        log.debug("{}", event);
    }

    public City getCityById(String cityId) {
        return cities.getCityById(cityId);
    }

    public AbstractBuilding<?> getBuildingById(String buildingId) {
        return cities.getBuildingById(buildingId);
    }

    public CityCollection getCities() {
        return unmodifiableCities;
    }

    public void addCity(City city) {
        cities.add(city);
        city.setCivilization(this);

        world.sendEvent(new Event(Event.UPDATE_WORLD, city, L10nCity.NEW_CITY_EVENT, city.getView().getLocalizedCityName()));
    }

    public void removeCity(City city) {
        cities.remove(city);
        destroyedCities.add(city);
        world.sendEvent(new Event(Event.UPDATE_WORLD, city, L10nCity.REMOVE_CITY_EVENT, city.getView().getLocalizedCityName()));

        // civilizations is destroyed when all the cities is destroyed
        isDestroyed = cities.isEmpty();
        if (isDestroyed) {
            world.sendEvent(new Event(Event.UPDATE_WORLD, this, L10nWorld.DESTROY_CIVILIZATION_EVENT, getView().getLocalizedCivilizationName()));
        }
    }

    public City getCityAtLocation(Point location) {
        return cities.getCityAtLocation(location);
    }

    public CityCollection getCitiesAtLocations(Collection<Point> locations) {
        return cities.getCitiesAtLocations(locations);
    }

    public CityCollection getCitiesWithActionsAvailable() {
        return cities.getCitiesWithActionsAvailable();
    }

    public AbstractUnit getUnitById(String unitId) {
        return unitService.getUnitById(unitId);
    }

    public UnitList<?> getUnits() {
        return unitService.getUnits();
    }

    public UnitList<?> getUnitsAtLocation(Point location) {
        return unitService.getUnitsAtLocation(location);
    }

    public UnitList<?> getUnitsAtLocations(Collection<Point> locations) {
        return unitService.getUnitsAtLocations(locations);
    }

    public UnitList<?> getUnitsAround(Point location, int radius) {
        return unitService.getUnitsAround(location, radius);
    }

    public UnitList<?> getUnitsWithActionsAvailable() {
        return unitService.getUnitsWithActionsAvailable();
    }

    public HasCombatStrength getAttackerById(String attackerId) {
        return unitService.getAttackerById(attackerId);
    }

    public void addUnit(AbstractUnit unit, Point location) {
        unitService.addUnit(unit, location);
    }

    public void removeUnit(AbstractUnit unit) {
        unitService.removeUnit(unit);
    }

    public boolean canBuyUnit(AbstractUnit unit) {
        return unitService.canBuyUnit(unit);
    }

    public void buyUnit(String unitClassUuid, City city) {
        Supply expenses = unitService.buyUnit(unitClassUuid, city);
        supply = supply.add(expenses);
    }

    public boolean canBuyBuilding(AbstractBuilding<?> building) {
        int gold = supply.getGold();
        return gold >= building.getGoldCost();
    }

    public void buyBuilding(String buildingClassUuid, City city) {
        AbstractBuilding<?> building = BuildingFactory.newInstance(buildingClassUuid, city);

        int gold = building.getGoldCost();
        Supply expenses = Supply.builder().gold(-gold).build();
        supply = supply.add(expenses);

        addEvent(new Event(Event.INFORMATION, supply, L10nCivilization.BUY_BUILDING_EVENT, building.getView().getLocalizedName()));
    }

    public boolean isResearched(Technology technology) {
        return technologies.contains(technology);
    }

    public void addTechnology(Technology technology) {
        technologies.add(technology);
    }

    private AgreementList getAgreementsWithCivilization(Civilization otherCivilization) {
        AgreementList agrs = agreements.get(otherCivilization);
        if (agrs == null) {
            return AgreementList.EMPTY_AGREEMENTS;
        }
        return agrs;
    }

    // See if other civilization can cross this civilization's borders
    public boolean canCrossBorders(Civilization otherCivilization) {
        if (isWar(otherCivilization)) {
            return true;
        }

        AgreementList agrs = getAgreementsWithCivilization(otherCivilization);
        OpenBordersAgreement openBordersAgreement = agrs.get(OpenBordersAgreement.class);
        return openBordersAgreement != null;
    }

    public boolean isWar(Civilization otherCivilization) {
        return world.isWar(this, otherCivilization);
    }

    public boolean isHavingTile(Point location) {
        return cities.isHavingTile(location);
    }

    // Start point for a Civilization
    public Point getStartPoint() {
        // First, this is Settler's location
        UnitList<?> units = getUnits().findByClassUuid(Settlers.CLASS_UUID);
        if (units != null && !units.isEmpty()) {
            return units.getFirst().getLocation();
        }

        // If Settlers were destroyed then it is the first city's location
        if (cities.size() > 0) {
            return cities.get(0).getLocation();
        }

        // If there is no cities, then the first Unit's
        units = unitService.getUnits();
        if (!units.isEmpty()) {
            return units.getFirst().getLocation();
        }

        // If there is no units then (0, 0)
        return new Point(0, 0);
    }

    public Supply calcSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;

        // income
        for (City city : cities) {
            supply = supply.add(city.getSupply());
        }

        // expenses on cities
        if (!cities.isEmpty()) {
            // units keeping
            int unitKeepingGold = unitService.getGoldKeepingExpenses();
            if (unitKeepingGold != 0) {
                supply = supply.add(Supply.builder().gold(-unitKeepingGold).build());
            }
        }

        // science

        return supply;
    }

    public void step(Year year) {
        if (isDestroyed) return;

        unitService.clearDestroyedUnits();
        destroyedCities.clear();

        for (City city : cities) {
            city.step(year);
        }

        supply = supply.add(calcSupply());

        // reset unit's pass score
        unitService.resetPassScore();

        isMoved = false;
    }

    public void giftReceived(Civilization sender, Supply receivedSupply) {
        supply = supply.add(receivedSupply);
        addEvent(new Event(Event.INFORMATION, this, L10nCivilization.GIFT_RECEIVED, receivedSupply.toString(), sender.getView().getLocalizedName()));
    }

    @Override
    public String toString() {
        return "Civilization{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Civilization that = (Civilization) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
