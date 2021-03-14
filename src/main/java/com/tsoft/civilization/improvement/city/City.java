package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.*;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.world.economic.*;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class City extends AbstractImprovement implements HasCombatStrength {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final Set<Point> locations = new HashSet<>();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
        .setTargetBackFireStrength(5)
        .setStrength(50)
        .setRangedAttackStrength(10)
        .setRangedAttackRadius(2);

    private CityView view;

    private CityPopulationService populationService;
    private CityBuildingService buildingService;
    private CityConstructionService constructionService;

    private Supply citySupply;

    private CombatStrength combatStrength;
    private int passScore;

    public City(Civilization civilization, Point location) {
        super(civilization, location);
    }

    public void init(L10n cityName, boolean isCapital) {
        // area
        locations.add(location);
        locations.addAll(getTilesMap().getLocationsAround(location, 1));

        // population
        populationService = new CityPopulationService(this);
        populationService.addCitizen();

        // economics
        citySupply = Supply.EMPTY_SUPPLY;

        // buildings & construction
        buildingService = new CityBuildingService(this);
        buildingService.addFirstBuilding(isCapital);
        constructionService = new CityConstructionService(this);

        // military
        combatStrength = new CombatStrength(this, getBaseCombatStrength());

        // media
        view = new CityView(cityName);
    }

    public L10n getName() {
        return view.getName();
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
        return view;
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

    public CityPopulationService population() {
        return populationService;
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
        return constructionService.canStartConstruction();
    }

    public ConstructionList getConstructions() {
        return constructionService.getConstructions();
    }

    public ConstructionList getBuiltThisYearList() {
        return constructionService.getBuiltThisYear();
    }

    public void startConstruction(CanBeBuilt obj) {
        constructionService.startConstruction(obj);
    }

    @Override
    public boolean acceptEraAndTechnology(Civilization civilization) {
        return true;
    }

    @Override
    public boolean acceptTile(AbstractTile tile) {
        return (tile.getTileType() != TileType.SEA) && (tile.getTileType() != TileType.EARTH_ROUGH);
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
    public UnitList getUnitsAround(int radius) {
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
        AbstractUnit unit = UnitFactory.newInstance(civilization, unitClassUuid);
        return unit.getProductionCost();
    }

    public int getUnitBuyCost(String unitClassUuid) {
        AbstractUnit unit = UnitFactory.newInstance(civilization, unitClassUuid);
        return unit.getGoldCost();
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


    public Supply getTilesSupply() {
        return populationService.getAllCitizensSupply();
    }

    @Override
    public Supply getSupply() {
        Supply supply = Supply.builder().population(getCitizenCount()).build();

        supply = supply
            .add(populationService.calcSupply())
            .add(buildingService.getSupply())
            .add(constructionService.getSupply(supply.getProduction()));

        return supply;
    }

    public void startYear() {
        populationService.startYear();
        buildingService.startYear();
        constructionService.startYear();

        // can do actions (attack, buy, build etc)
        passScore = 1;
    }

    public void stopYear() {
        Supply supply = Supply.EMPTY_SUPPLY;

        // population
        Supply populationSupply = populationService.stopYear();
        supply = supply.add(populationSupply);

        // buildings
        Supply buildingSupply = buildingService.stopYear();
        supply = supply.add(buildingSupply);

        // construction
        Supply constructionSupply = constructionService.stopYear(supply);
        supply = supply.add(constructionSupply);

        citySupply = citySupply.add(supply);
        log.debug("City: Year {}, supply = {}, total supply = {}", getWorld().getYear(), supply, citySupply);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
