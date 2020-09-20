package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10nChainedArrayList;
import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.building.*;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.world.economic.*;
import com.tsoft.civilization.world.event.Event;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class City extends AbstractImprovement implements HasCombatStrength {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
            .setTargetBackFireStrength(5)
            .setStrength(50)
            .setRangedAttackStrength(10)
            .setRangedAttackRadius(2);

    private final CityView VIEW;

    private Set<Point> locations = new HashSet<>();
    private Set<Point> unmodifiableLocations = Collections.unmodifiableSet(locations);

    private CityPopulationService citizenService;
    private CityBuildingService buildingService;

    private Supply citySupply;

    private CombatStrength combatStrength;
    private int passScore;

    public City(Civilization civilization, Point location) {
        super(civilization, location);

        // city's name
        L10nMap civilizationName = civilization.getView().getName();
        L10nChainedArrayList<L10nMap> cities = L10nCity.CITIES.get(civilizationName);
        int index = civilization.getCities().size();
        if (index >= cities.size()) {
            index -= cities.size();
        }
        VIEW = new CityView(cities.get(index));

        // add the city to a civilization
        civilization.addCity(this);

        // economics
        citySupply = Supply.EMPTY_SUPPLY;

        // city's tiles
        locations.add(location);
        locations.addAll(getTilesMap().getLocationsAround(location, 1));

        // first citizen
        citizenService = new CityPopulationService(this);
        addCitizen();
        Event event = new Event(Event.INFORMATION, citizenService, L10nCity.FOUNDED_SETTLERS, VIEW.getLocalizedCityName());
        civilization.addEvent(event);

        // city's initial buildings
        buildingService = new CityBuildingService(this);

        // City is a ranged military unit
        combatStrength = new CombatStrength(this, getBaseCombatStrength());
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    @Override
    public CombatStrength getBaseCombatStrength() {
        return COMBAT_STRENGTH;
    }

    @Override
    public CombatStrength getCombatStrength() {
        return combatStrength;
    }

    @Override
    public CityView getView() {
        return VIEW;
    }

    public int getPassScore() {
        return passScore;
    }

    @Override
    public UnitCategory getUnitCategory() {
        return UnitCategory.MILITARY_RANGED_CITY;
    }

    public Set<Point> getLocations() {
        return unmodifiableLocations;
    }

    public void addLocations(Collection<Point> locations) {
        this.locations.addAll(locations);
    }

    public boolean isHavingTile(Point location) {
        return locations.contains(location);
    }

    public CitySupplyStrategy getSupplyStrategy() {
        return citizenService.getSupplyStrategy();
    }

    public void setSupplyStrategy(CitySupplyStrategy supplyStrategy) {
        citizenService.setSupplyStrategy(supplyStrategy);
    }

    public void addCitizen() {
        citizenService.addCitizen();
    }

    public int getCitizenCount() {
        return citizenService.getCitizenCount();
    }

    public BuildingList getBuildings() {
        return buildingService.getBuildings();
    }

    public AbstractBuilding getBuildingById(String buildingId) {
        return buildingService.getBuildingById(buildingId);
    }

    public void addBuilding(AbstractBuilding building) {
        buildingService.add(building);

        // check is this building adds defense strength
        int strength = getCombatStrength().getStrength();
        strength += building.getStrength();
        getCombatStrength().setStrength(strength);
    }

    public void destroyBuilding(AbstractBuilding building) {
        buildingService.remove(building);
    }

    public List<Point> getCitizenLocations() {
        return citizenService.getPopulationLocations();
    }

    public AbstractBuilding findBuildingByClassUuid(String classUuid) {
        return buildingService.findByClassUuid(classUuid);
    }

    public boolean isCapital() {
        return findBuildingByClassUuid(Palace.CLASS_UUID) != null;
    }

    public boolean canStartConstruction() {
        return buildingService.canStartConstruction();
    }

    public Construction getConstruction() {
        return buildingService.getConstruction();
    }

    public void startConstruction(CanBeBuilt obj) {
        buildingService.startConstruction(obj);
    }

    // Construction of a building or an unit is finished
    public void constructionDone(Construction construction) {
        CanBeBuilt obj = construction.getObject();

        if (obj instanceof AbstractBuilding) {
            AbstractBuilding building = BuildingFactory.newInstance(obj.getClassUuid(), this);
            addBuilding(building);
            getWorld().sendEvent(new Event(Event.UPDATE_WORLD, obj, L10nCity.NEW_BUILDING_BUILT_EVENT, building.getView().getLocalizedName()));
            return;
        }

        if (obj instanceof AbstractUnit) {
            AbstractUnit unit = (AbstractUnit)obj;
            addUnit(unit);
            getWorld().sendEvent(new Event(Event.UPDATE_WORLD, obj, L10nCity.NEW_UNIT_BUILT_EVENT, unit.getView().getLocalizedName()));
            return;
        }

        String objectInfo = (obj == null ? "null" : obj.getClass().getName());
        throw new IllegalArgumentException("Unknown object is built: " + objectInfo);
    }

    @Override
    public Supply getSupply() {
        Supply supply = Supply.builder().population(getCitizenCount()).build();

        supply = supply
            .add(citizenService.calcSupply())
            .add(buildingService.getSupply());

        return supply;
    }

    public Supply getTilesSupply() {
        return citizenService.getAllCitizensSupply();
    }

    public void step(Year year) {
        // income from citizens
        citizenService.step(year);

        // buildings & construction
        citySupply = buildingService.step(citySupply);

        Supply supply = getSupply();
        citySupply = citySupply.add(supply);
        log.debug("City: Year {}, supply = {}, total supply = {}", year, supply, citySupply);

        // can do actions (attack, buy, build etc)
        passScore = 1;
    }

    @Override
    public void setPassScore(int passScore) {
        this.passScore = passScore;
    }

    @Override
    public boolean isDestroyed() {
        return getCombatStrength().isDestroyed();
    }

    @Override
    public UnitList<?> getUnitsAround(int radius) {
        return civilization.getUnitsAround(getLocation(), radius);
    }

    @Override
    public List<AbstractSkill> getSkills() {
        return Collections.emptyList();
    }

    /** A city may be destroyed only during a melee attack */
    @Override
    public void destroyedBy(HasCombatStrength destroyer, boolean destroyOtherUnitsAtLocation) {
        getCombatStrength().setDestroyed(true);
        setPassScore(0);

        Event worldEvent = new Event(Event.UPDATE_WORLD, this, L10nCity.CITY_WAS_CAPTURED, VIEW.getLocalizedCityName());
        getWorld().sendEvent(worldEvent);

        Event event = new Event(Event.UPDATE_WORLD, destroyer, L10nCity.UNIT_HAS_CAPTURED_CITY);
        destroyer.getCivilization().addEvent(event);

        // capture the city
        getCivilization().removeCity(this);
        destroyer.getCivilization().addCity(this);

        // destroy all military units located in the city and capture civilians
        UnitList<?> units = civilization.getWorld().getUnitsAtLocation(location);
        for (AbstractUnit unit : units) {
            if (unit.getUnitCategory().isMilitary()) {
                unit.destroyedBy(destroyer, false);
            } else {
                unit.capturedBy(destroyer);
            }
        }
    }

    public void addUnit(AbstractUnit unit) {
        civilization.addUnit(unit, location);
    }

    public int getUnitProductionCost(String unitClassUuid) {
        AbstractUnit unit = UnitFactory.createUnit(unitClassUuid);
        return unit.getProductionCost();
    }

    public int getUnitBuyCost(String unitClassUuid) {
        AbstractUnit unit = UnitFactory.createUnit(unitClassUuid);
        return unit.getGoldCost();
    }

    /** Return true, if the unit can be placed (i.e. after a buy or on entering) in the city */
    public boolean canPlaceUnit(AbstractUnit unit) {
        UnitList<?> units = getWorld().getUnitsAtLocation(getLocation());
        AbstractUnit sameTypeUnit = units.findUnitByUnitKind(unit.getUnitCategory());
        return sameTypeUnit == null;
    }

    public int getBuildingProductionCost(String buildingClassUuid) {
        AbstractBuilding building = BuildingCatalog.findByClassUuid(buildingClassUuid);
        return building.getProductionCost();
    }

    public int getBuildingBuyCost(String buildingClassUuid) {
        AbstractBuilding building = BuildingCatalog.findByClassUuid(buildingClassUuid);
        return building.getGoldCost();
    }

    public boolean canPlaceBuilding(AbstractBuilding building) {
        return findBuildingByClassUuid(building.getClassUuid()) == null;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
