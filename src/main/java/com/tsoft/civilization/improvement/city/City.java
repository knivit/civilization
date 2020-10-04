package com.tsoft.civilization.improvement.city;

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

    private final Set<Point> locations = new HashSet<>();

    private final CityPopulationService populationService;
    private final CityBuildingService buildingService;

    private Supply citySupply;

    private CombatStrength combatStrength;
    private int passScore;

    public City(Civilization civilization, L10nMap cityName, Point location, boolean isCapital) {
        super(civilization, location);

        // area
        locations.add(location);
        locations.addAll(getTilesMap().getLocationsAround(location, 1));

        // population
        populationService = new CityPopulationService(this);
        populationService.addCitizen();

        // economics
        citySupply = Supply.EMPTY_SUPPLY;

        // buildings
        buildingService = new CityBuildingService(this);
        buildingService.addFirstBuilding(isCapital);

        // military
        combatStrength = new CombatStrength(this, getBaseCombatStrength());

        // media
        VIEW = new CityView(cityName);
    }

    public L10nMap getName() {
        return VIEW.getName();
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
        return Collections.unmodifiableSet(locations);
    }

    public void addLocations(Collection<Point> locations) {
        this.locations.addAll(locations);
    }

    public boolean isHavingTile(Point location) {
        return locations.contains(location);
    }

    public CitySupplyStrategy getSupplyStrategy() {
        return populationService.getSupplyStrategy();
    }

    public void setSupplyStrategy(CitySupplyStrategy supplyStrategy) {
        populationService.setSupplyStrategy(supplyStrategy);
    }

    public void addCitizen() {
        populationService.addCitizen();
    }

    public int getCitizenCount() {
        return populationService.getCitizenCount();
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
        return populationService.getPopulationLocations();
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
            .add(populationService.calcSupply())
            .add(buildingService.getSupply());

        return supply;
    }

    public Supply getTilesSupply() {
        return populationService.getAllCitizensSupply();
    }

    public void startYear() {
        populationService.startYear();
        buildingService.startYear();
    }

    public void move() {
        Year year = civilization.getYear();

        // income from citizens
        populationService.move(year);

        // buildings & construction
        citySupply = buildingService.move(citySupply);

        Supply supply = getSupply();
        citySupply = citySupply.add(supply);
        log.debug("City: Year {}, supply = {}, total supply = {}", year, supply, citySupply);

        // can do actions (attack, buy, build etc)
        passScore = 1;
    }

    public void stopYear() {
        populationService.stopYear();
        buildingService.stopYear();
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
        return civilization.units().getUnitsAround(getLocation(), radius);
    }

    @Override
    public List<AbstractSkill> getSkills() {
        return Collections.emptyList();
    }

    /** A city may be destroyed only during a melee attack */
    @Override
    public void destroyedBy(HasCombatStrength destroyer, boolean destroyOtherUnitsAtLocation) {
        destroyer.getCivilization().captureCity(this, destroyer);
    }

    /** City has moved to another Civilization (was captured) */
    public void moveToCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public void addUnit(AbstractUnit unit) {
        civilization.units().addUnit(unit, location);
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
