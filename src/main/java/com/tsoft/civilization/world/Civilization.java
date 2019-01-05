package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.L10n.L10nWorld;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.util.BuildingCatalog;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.improvement.city.UnmodifiableCityList;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.util.TilePassCostTable;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.Settlers;
import com.tsoft.civilization.unit.util.UnitFactory;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.unit.util.UnitList;
import com.tsoft.civilization.unit.util.UnmodifiableUnitList;
import com.tsoft.civilization.unit.Warriors;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.web.view.world.CivilizationView;
import com.tsoft.civilization.world.agreement.AgreementList;
import com.tsoft.civilization.world.agreement.OpenBordersAgreement;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.util.EventsByYearMap;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Civilization {
    private final CivilizationView CIVILIZATION_VIEW;

    private L10nMap name;
    private String id;
    private Year startYear;
    private boolean isMoved;
    private boolean isArtificialIntelligence;

    private World world;

    // Active cities and destroyed (on this step) cities
    private CityCollection cities = new CityList();
    private UnmodifiableCityList unmodifiableCities = new UnmodifiableCityList(cities);
    private CityCollection destroyedCities = new CityList();

    // Active units and destroyed (on this step) units
    private UnitCollection units = new UnitList();
    private UnmodifiableUnitList unmodifiableUnits = new UnmodifiableUnitList(units);
    private UnitCollection destroyedUnits = new UnitList();

    private HashSet<Technology> technologies = new TechnologySet();
    private Supply supply;

    // Agreements which this civilization has with others
    private HashMap<Civilization, AgreementList> agreements = new HashMap<>();

    // true when any event that needs score to be calculated was generated
    private EventsByYearMap events;

    private Point settlersLocation;
    private boolean isDestroyed;

    public Civilization(World world, int index) {
        this.world = world;
        if ((index < 0) || (index >= L10nCivilization.CIVILIZATIONS.size())) {
            log.warn("Invalid civilization index={}", index);
            index = 0;
        }
        name = L10nCivilization.CIVILIZATIONS.get(index);
        CIVILIZATION_VIEW = new CivilizationView(name);

        id = UUID.randomUUID().toString();
        startYear = world.getYear();
        events = new EventsByYearMap(world);

        // find a location for the Settlers
        settlersLocation = world.getCivilizationStartLocation();

        world.addCivilization(this);

        supply = new Supply();
    }

    public void addFirstUnits() {
        Settlers settlers = UnitFactory.newInstance(Settlers.CLASS_UUID, this, settlersLocation);

        // try to place Warriors near the Settlers
        ArrayList<Point> locations = world.getLocationsAround(settlersLocation, 2);
        for (Point location : locations) {
            AbstractTile tile = world.getTilesMap().getTile(location);
            if (tile.getPassCost(settlers) != TilePassCostTable.UNPASSABLE) {
                UnitFactory.newInstance(Warriors.CLASS_UUID, this, location);
                break;
            }
        }
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
        return CIVILIZATION_VIEW;
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

        if (isArtificialIntelligence) {
            aiMove();
        }

        isMoved = true;

        Event event = new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.MOVE_DONE_EVENT, this.getView().getLocalizedCivilizationName());
        addEvent(event);
        log.debug("{}", event);
    }

    public EventsByYearMap getEvents() {
        return events;
    }

    /** Something is happened in our civilization (a citizen was born,
     * a building was built, a unit has won etc) */
    public void addEvent(Event event) {
        events.add(event);
    }

    public City getCityById(String cityId) {
        return cities.getCityById(cityId);
    }

    public AbstractBuilding getBuildingById(String buildingId) {
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

        // civilizations is destroyed when all the cities is destroyed
        isDestroyed = cities.isEmpty();
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
        return units.getUnitById(unitId);
    }

    public UnitCollection getUnits() {
        return unmodifiableUnits;
    }

    public UnitCollection getUnitsAtLocation(Point location) {
        List<Point> locations = new ArrayList<>(1);
        locations.add(location);

        return getUnitsAtLocations(locations);
    }

    public UnitCollection getUnitsAtLocations(Collection<Point> locations) {
        return units.getUnitsAtLocations(locations);
    }

    public UnitCollection getUnitsAround(Point location, int radius) {
        Collection<Point> locations = world.getLocationsAround(location, radius);
        UnitCollection unitsAround = getUnitsAtLocations(locations);
        return unitsAround;
    }

    public UnitCollection getUnitsWithActionsAvailable() {
        return units.getUnitsWithActionsAvailable();
    }

    public HasCombatStrength getAttackerById(String attackerId) {
        HasCombatStrength attacker = getUnitById(attackerId);
        if (attacker == null) {
            attacker = getCityById(attackerId);
        }
        return attacker;
    }

    public void addUnit(AbstractUnit unit) {
        units.add(unit);
        unit.setCivilization(this);
    }

    public void removeUnit(AbstractUnit unit) {
        units.remove(unit);
        destroyedUnits.add(unit);
        unit.setCivilization(null);

        world.sendEvent(new Event(Event.UPDATE_WORLD, this, L10nUnit.UNIT_WAS_DESTROYED_EVENT, unit.getView().getLocalizedName()));
    }

    public boolean canBuyUnit(AbstractUnit unit) {
        int gold = supply.getGold();
        if (gold < unit.getGoldCost()) {
            return false;
        }
        return true;
    }

    public void buyUnit(String unitClassUuid, City city) {
        AbstractUnit unit = UnitFactory.newInstance(unitClassUuid, this, city.getLocation());

        int gold = unit.getGoldCost();
        Supply expenses = new Supply().setGold(gold);

        supply.add(expenses);

        Event event = new Event(Event.INFORMATION, supply, L10nCivilization.BUY_UNIT_EVENT);
        addEvent(event);
        log.debug("{}", event);
    }

    public boolean canBuyBuilding(AbstractBuilding building) {
        int gold = supply.getGold();
        if (gold < building.getGoldCost()) {
            return false;
        }
        return true;
    }

    public void buyBuilding(String buildingClassUuid, City city) {
        AbstractBuilding catalogBuilding = BuildingCatalog.values().findByClassUuid(buildingClassUuid);
        int gold = catalogBuilding.getGoldCost();

        Supply expenses = new Supply().setGold(gold);
        supply.add(expenses);

        Event event = new Event(Event.INFORMATION, supply, L10nCivilization.BUY_BUILDING_EVENT);
        addEvent(event);
        log.debug("{}", event);

        AbstractBuilding.newInstance(buildingClassUuid, city);
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
        if (openBordersAgreement != null) {
            return true;
        }

        return false;
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
        Settlers settlers = (Settlers)getUnits().findByClassUuid(Settlers.CLASS_UUID);
        if (settlers != null) {
            return settlers.getLocation();
        }

        // If Settlers were destroyed the this is first City location
        if (cities.size() > 0) {
            return cities.get(0).getLocation();
        }

        // If there is no cities, then first Unit
        if (units.size() > 0) {
            return units.get(0).getLocation();
        }

        // If there is no units then (0, 0)
        return new Point(0, 0);
    }

    public void addSupply(Supply supply) {
        this.supply.add(supply);
    }

    public Supply calcSupply() {
        Supply supply = new Supply();

        // income
        for (City city : cities) {
            supply.add(city.getSupply());
        }

        // expenses on cities
        if (!cities.isEmpty()) {
            // units keeping
            int unitKeepingGold = units.getGoldKeepingExpenses();
            if (unitKeepingGold != 0) {
                supply.add(new Supply().setGold(-unitKeepingGold));
            }
        }

        // science

        return supply;
    }

    public void step(Year year) {
        if (isDestroyed) return;

        destroyedUnits.clear();
        destroyedCities.clear();

        for (City city : cities) {
            city.step(year);
        }

        Supply supply = calcSupply();
        supply.add(supply);

        // reset unit's pass score
        units.resetPassScore();

        isMoved = false;
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

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
