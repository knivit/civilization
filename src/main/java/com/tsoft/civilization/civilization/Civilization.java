package com.tsoft.civilization.civilization;

import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.improvement.city.L10nCity;
import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.unit.UnitFactory;
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
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.world.event.Event;
import com.tsoft.civilization.world.event.EventsByYearMap;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@EqualsAndHashCode(of = "id")
public abstract class Civilization implements HasSupply {
    @Getter
    private final String id = UUID.randomUUID().toString();

    private final Year startYear;
    private final World world;

    private boolean isDestroyed;

    private final CivilizationUnitService unitService;
    private final CivilizationCityService cityService;
    private final CivilizationTerritoryService territoryService;

    private final Set<Technology> technologies = new TechnologySet();

    @Getter
    private Supply supply = Supply.EMPTY_SUPPLY;

    // Agreements which this civilization has with others
    private final HashMap<Civilization, AgreementList> agreements = new HashMap<>();

    // true when any event that needs score to be calculated was generated
    private final EventsByYearMap events;

    @Getter
    private final PlayerType playerType;

    @Getter
    private volatile MoveState moveState;

    @Getter
    private final CivilizationView view;

    @Getter
    private final CivilizationBot bot;

    protected abstract CivilizationView createView();

    protected abstract CivilizationBot createBot(World world, Civilization civilization);

    protected Civilization(World world, PlayerType playerType) {
        Objects.requireNonNull(world, "world can't be null");
        Objects.requireNonNull(playerType, "playerType can't be null");

        this.world = world;
        this.playerType = playerType;

        startYear = world.getYear();
        events = new EventsByYearMap(world);

        // initialize params
        unitService = new CivilizationUnitService(this);
        cityService = new CivilizationCityService(this);
        territoryService = new CivilizationTerritoryService(this);

        view = createView();
        bot = createBot(world, this);
    }

    public L10n getName() {
        return getView().getName();
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

    public World getWorld() {
        return world;
    }

    public TilesMap getTilesMap() {
        return world.getTilesMap();
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public void setDestroyed() {
        world.sendEvent(new Event(Event.UPDATE_WORLD, this, L10nWorld.DESTROY_CIVILIZATION_EVENT, getView().getLocalizedCivilizationName()));
        isDestroyed = true;
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

    public boolean buyUnit(String unitClassUuid, City city) {
        AbstractUnit unit = UnitFactory.newInstance(this, unitClassUuid);
        if (!unitService.addUnit(unit, city.getLocation())) {
            return false;
        }

        Supply expenses = units().buyUnit(unit);
        supply = supply.add(expenses);

        addEvent(new Event(Event.INFORMATION, expenses, L10nCivilization.BUY_UNIT_EVENT));
        return true;
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

    public City createCity(Settlers settlers) {
        Objects.requireNonNull(settlers, "settlers must be not null");

        Point location = settlers.getLocation();
        L10n cityName = cityService.findCityName();
        boolean isCapital = cityService.size() == 0;
        City city = ImprovementFactory.newInstance(City.CLASS_UUID, this, location);
        city.init(cityName, isCapital);
        cityService.addCity(city);

        Event event = new Event(Event.INFORMATION, city, L10nCity.FOUNDED_SETTLERS, cityName);
        addEvent(event);

        settlers.destroyedBy(null, false);

        return city;
    }

    public void captureCity(City city, HasCombatStrength destroyer) {
        cityService.captureCity(city, destroyer, false);
    }

    public void giftReceived(Civilization sender, Supply receivedSupply) {
        supply = supply.add(receivedSupply);
        addEvent(new Event(Event.INFORMATION, this, L10nCivilization.GIFT_RECEIVED, receivedSupply.toString(), sender.getView().getLocalizedName()));
    }

    @Override
    public Supply calcSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;

        // income
        supply = supply.add(cityService.calcSupply());

        // expenses
        supply = supply.add(unitService.calcSupply());

        return supply;
    }

    @Override
    public void startYear() {
        if (isDestroyed()) {
            return;
        }

        moveState = MoveState.IN_PROGRESS;

        addEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.MOVE_START_EVENT, getView().getLocalizedCivilizationName()));

        unitService.startYear();
        cityService.startYear();
        territoryService.startYear();

        // A bot can be helping a human player
        getBot().startYear();
    }

    @Override
    public synchronized void stopYear() {
        // There can be a helper bot still in progress
        if (!MoveState.DONE.equals(getBot().getMoveState())) {
            return;
        }

        // Update state only once
        if (moveState == MoveState.DONE) {
            return;
        }

        moveState = MoveState.DONE;

        cityService.stopYear();
        unitService.stopYear();
        territoryService.stopYear();

        supply = supply.addWithoutPopulation(calcSupply());

        addEvent(new Event(Event.UPDATE_CONTROL_PANEL, this, L10nWorld.MOVE_DONE_EVENT, getView().getLocalizedCivilizationName()));

        world.onCivilizationMoved();
    }

    @Override
    public String toString() {
        return "Civilization{" +
                "name='" + getName() + '\'' +
            '}';
    }
}
