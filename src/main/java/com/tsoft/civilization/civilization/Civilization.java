package com.tsoft.civilization.civilization;

import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.world.L10nWorld;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingFactory;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.ImprovementFactory;
import com.tsoft.civilization.improvement.city.City;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@EqualsAndHashCode(of = "id")
public class Civilization {
    private final String id = UUID.randomUUID().toString();

    private final CivilizationView view;

    private final L10n name;
    private final Year startYear;

    private final World world;

    private final CivilizationUnitService unitService;
    private final CivilizationCityService cityService;
    private final CivilizationTerritoryService territoryService;

    private final HashSet<Technology> technologies = new TechnologySet();

    @Getter
    private Supply supply;

    // Agreements which this civilization has with others
    private final HashMap<Civilization, AgreementList> agreements = new HashMap<>();

    // true when any event that needs score to be calculated was generated
    private final EventsByYearMap events;

    @Getter
    @Setter
    private boolean isAi;

    @Getter
    private volatile MoveState moveState;

    public Civilization(World world, L10n name) {
        Objects.requireNonNull(world, "World can't be null");
        Objects.requireNonNull(name, "Civilization name can't be null");

        this.world = world;
        this.name = name;
        view = new CivilizationView(name);

        startYear = world.getYear();
        events = new EventsByYearMap(world);

        // initialize params
        supply = Supply.EMPTY_SUPPLY;
        unitService = new CivilizationUnitService(this);
        cityService = new CivilizationCityService(this);
        territoryService = new CivilizationTerritoryService(this);
    }

    public String getId() {
        return id;
    }

    public L10n getName() {
        return name;
    }

    public CivilizationUnitService units() {
        return unitService;
    }

    public CivilizationCityService cities() {
        return cityService;
    }

    public CivilizationTerritoryService territory() {
        return territoryService;
    }

    public Year getStartYear() {
        return startYear;
    }

    public Year getYear() {
        return world.getYear();
    }

    public CivilizationView getView() {
        return view;
    }

    public World getWorld() {
        return world;
    }

    public TilesMap getTilesMap() {
        return world.getTilesMap();
    }

    public boolean isDestroyed() {
        return cities().isDestroyed();
    }

    public EventsByYearMap getEvents() {
        return events;
    }

    /** Something is happened in our civilization (a citizen was born,
     * a building was built, a unit has won etc) */
    public void addEvent(Event event) {
        events.add(event);
    }

    public boolean canBuyBuilding(AbstractBuilding building) {
        int gold = supply.getGold();
        return gold >= building.getGoldCost();
    }

    public void buyBuilding(String buildingClassUuid, City city) {
        AbstractBuilding building = BuildingFactory.newInstance(buildingClassUuid, city);

        int gold = building.getGoldCost();
        Supply expenses = Supply.builder().gold(-gold).build();
        supply = supply.add(expenses);

        addEvent(new Event(Event.INFORMATION, supply, L10nCivilization.BUY_BUILDING_EVENT, building.getView().getLocalizedName()));
    }

    public void buyUnit(String unitClassUuid, City city) {
        Supply expenses = units().buyUnit(unitClassUuid, city);
        supply = supply.add(expenses);
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

    // Start point for a Civilization
    public Point getStartPoint() {
        // First, this is Settler's location
        UnitList units = unitService.findByClassUuid(Settlers.CLASS_UUID);
        if (units != null && !units.isEmpty()) {
            return units.getAny().getLocation();
        }

        // If Settlers were destroyed then it is the first city's location
        City city = cityService.getAny();
        if (city != null) {
            return city.getLocation();
        }

        // If there is no cities, then the first Unit's location
        AbstractUnit unit = unitService.getAny();
        if (unit != null) {
            return unit.getLocation();
        }

        // If there is no units then (0, 0)
        return new Point(0, 0);
    }

    public City createCity(Point location) {
        L10n cityName = cityService.findCityName();
        boolean isCapital = cityService.size() == 0;
        City city = ImprovementFactory.newInstance(City.CLASS_UUID, this, location);
        city.init(cityName, isCapital);
        cityService.addCity(city);

        Event event = new Event(Event.INFORMATION, city, L10nCity.FOUNDED_SETTLERS, cityName);
        addEvent(event);
        return city;
    }

    public void captureCity(City city, HasCombatStrength destroyer) {
        cityService.captureCity(city, destroyer, false);
    }

    public void giftReceived(Civilization sender, Supply receivedSupply) {
        supply = supply.add(receivedSupply);
        addEvent(new Event(Event.INFORMATION, this, L10nCivilization.GIFT_RECEIVED, receivedSupply.toString(), sender.getView().getLocalizedName()));
    }

    public Supply calcSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;

        // income
        supply = supply.add(cityService.getSupply());

        // expenses
        supply = supply.add(unitService.getSupply());

        return supply;
    }

    public void startYear() {
        if (isDestroyed()) {
            return;
        }

        addEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.MOVE_START_EVENT, getView().getLocalizedCivilizationName()));

        unitService.startYear();
        cityService.startYear();
        territoryService.startYear();

        moveState = MoveState.IN_PROGRESS;
    }

    public void stopYear() {
        // Update state as soon as possible to prevent stopping twice
        moveState = MoveState.DONE;

        cityService.stopYear();
        unitService.stopYear();
        territoryService.stopYear();

        supply = supply.addWithoutPopulation(calcSupply());

        addEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.MOVE_DONE_EVENT, getView().getLocalizedCivilizationName()));

        world.onCivilizationMoved(this);
    }

    @Override
    public String toString() {
        return "Civilization{" +
                "name='" + name + '\'' +
            '}';
    }
}
