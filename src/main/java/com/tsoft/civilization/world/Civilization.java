package com.tsoft.civilization.world;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.L10nMap;
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
import com.tsoft.civilization.util.DefaultLogger;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.web.view.world.CivilizationView;
import com.tsoft.civilization.world.agreement.AgreementList;
import com.tsoft.civilization.world.agreement.OpenBordersAgreement;
import com.tsoft.civilization.world.economic.CivilizationScore;
import com.tsoft.civilization.world.economic.CivilizationSupply;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.util.EventsByYearMap;

import java.util.*;

public class Civilization {
    private final CivilizationView CIVILIZATION_VIEW;

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
    private CivilizationScore civilizationScore;

    // Agreements which this civilization have with others
    private HashMap<Civilization, AgreementList> agreements = new HashMap<>();

    // true when any event that needs score to be calculated was generated
    private EventsByYearMap events;
    private boolean needCivilizationScoreRecalculation = true;

    private Point settlersLocation;
    private boolean isDestroyed;

    public Civilization(World world, int index) {
        this.world = world;
        if ((index < 0) || (index >= L10nCivilization.CIVILIZATIONS.size())) {
            DefaultLogger.warning("Invalid civilization index=" + index);
            index = 0;
        }
        L10nMap name = L10nCivilization.CIVILIZATIONS.get(index);
        CIVILIZATION_VIEW = new CivilizationView(name);

        id = UUID.randomUUID().toString();
        startYear = world.getYear();
        events = new EventsByYearMap(world);

        // find a location for the Settlers
        settlersLocation = world.getCivilizationStartLocation();

        world.addCivilization(this);

        civilizationScore = new CivilizationScore(this);
    }

    public void addFirstUnits() {
        Settlers settlers = UnitFactory.newInstance(Settlers.INSTANCE, this, settlersLocation);

        // try to place Warriors near the Settlers
        ArrayList<Point> locations = world.getLocationsAround(settlersLocation, 2);
        for (Point location : locations) {
            AbstractTile tile = world.getTilesMap().getTile(location);
            if (tile.getPassCost(settlers) != TilePassCostTable.UNPASSABLE) {
                UnitFactory.newInstance(Warriors.INSTANCE, this, location);
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

    public boolean isMoved() {
        return isMoved;
    }

    public boolean isArtificialIntelligence() {
        return isArtificialIntelligence;
    }

    public void setArtificialIntelligence(boolean artificialIntelligence) {
        isArtificialIntelligence = artificialIntelligence;
    }

    public void aiMove() {
        // TODO
    }

    public boolean nextMove() {
        isMoved = true;
        return world.nextMove();
    }

    public EventsByYearMap getEvents() {
        return events;
    }

    /** Something is happened in our civilization (a citizen was born, a building was built, a unit has won etc) */
    public void addEvent(Event event) {
        needCivilizationScoreRecalculation = true;
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

        world.sendEvent(new Event(city, L10nCity.NEW_CITY_EVENT, Event.UPDATE_WORLD));
        needCivilizationScoreRecalculation = true;
    }

    public void removeCity(City city) {
        cities.remove(city);
        destroyedCities.add(city);

        // civilizations is destroyed when all the cities is destroyed
        isDestroyed = cities.isEmpty();
        needCivilizationScoreRecalculation = true;
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
        needCivilizationScoreRecalculation = true;
    }

    public void removeUnit(AbstractUnit unit) {
        units.remove(unit);
        destroyedUnits.add(unit);
        unit.setCivilization(null);

        world.sendEvent(new Event(this, L10nUnit.UNIT_WAS_DESTROYED_EVENT, Event.UPDATE_WORLD));
        needCivilizationScoreRecalculation = true;
    }

    public boolean canBuyUnit(AbstractUnit unit) {
        int gold = getCivilizationScore().getGold();
        if (gold < unit.getGoldCost()) {
            return false;
        }
        return true;
    }

    public void buyUnit(String unitClassUuid, City city) {
        AbstractUnit catalogUnit = UnitFactory.getUnitFromCatalogByClassUuid(unitClassUuid);
        int gold = catalogUnit.getGoldCost();
        CivilizationSupply expenses = new CivilizationSupply(0, 0, gold, 0);
        getCivilizationScore().add(expenses, L10nCivilization.BUY_UNIT_EVENT);

        UnitFactory.newInstance(catalogUnit, this, city.getLocation());
    }

    public boolean canBuyBuilding(AbstractBuilding building) {
        int gold = getCivilizationScore().getGold();
        if (gold < building.getGoldCost()) {
            return false;
        }
        return true;
    }

    public void buyBuilding(String buildingClassUuid, City city) {
        AbstractBuilding catalogBuilding = BuildingCatalog.values().findByClassUuid(buildingClassUuid);
        int gold = catalogBuilding.getGoldCost();
        CivilizationSupply expenses = new CivilizationSupply(0, 0, gold, 0);
        getCivilizationScore().add(expenses, L10nCivilization.BUY_BUILDING_EVENT);

        AbstractBuilding.newInstance(buildingClassUuid, city);
    }

    public boolean isResearched(Technology technology) {
        return technologies.contains(technology);
    }

    public void addTechnology(Technology technology) {
        technologies.add(technology);
    }

    public AgreementList getAgreementsFromCivilization(Civilization otherCivilization) {
        AgreementList agrs = agreements.get(otherCivilization);
        if (agrs == null) {
            return AgreementList.EMPTY_AGREEMENTS;
        }
        return agrs;
    }

    // See if other civilization can cross this borders
    public boolean canCrossBorders(Civilization otherCivilization) {
        if (isWar(otherCivilization)) {
            return true;
        }

        AgreementList agrs = getAgreementsFromCivilization(otherCivilization);
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

    public CivilizationScore getCivilizationScore() {
        if (needCivilizationScoreRecalculation) {
            calcCivilizationScore();
        }
        return civilizationScore;
    }

    public void step() {
        if (isDestroyed) return;

        destroyedUnits.clear();
        destroyedCities.clear();

        for (City city : cities) {
            city.step();
        }

        calcCivilizationScore();

        // reset unit's pass score
        units.resetPassScore();

        isMoved = false;
    }

    private void calcCivilizationScore() {
        int gold = civilizationScore.getGold();
        CivilizationSupply accumulationSupply = new CivilizationSupply(0, 0, gold, 0);

        civilizationScore = new CivilizationScore(this);
        civilizationScore.add(accumulationSupply, L10nCivilization.ACCUMULATION_SUPPLY);

        // income from all cities
        for (City city : cities) {
            civilizationScore.add(city.getCityScore());
        }

        // expenses (if there is a city)
        if (!cities.isEmpty()) {
            // units keeping
            int unitKeepingGold = units.getGoldKeepingExpenses();
            if (unitKeepingGold != 0) {
                CivilizationSupply civilizationSupply = new CivilizationSupply(0, 0, -unitKeepingGold, 0);
                civilizationScore.add(civilizationSupply, L10nCivilization.UNIT_KEEPING_EXPENSES);
            }
        }

        // science

        // reset the flag
        needCivilizationScoreRecalculation = false;
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
