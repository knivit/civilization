package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.*;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.improvement.city.event.CityStopYearEvent;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TileType;
import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.economic.*;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class City extends AbstractImprovement implements HasCombatStrength, HasSupply {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private final Set<Point> locations = new HashSet<>();

    private CityPopulationService populationService;
    private CityBuildingService buildingService;
    private CityConstructionService constructionService;
    private CityCombatService combatService;

    @Getter
    private CityView view;

    @Getter
    private Supply supply = Supply.EMPTY_SUPPLY;

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

        // buildings & construction
        buildingService = new CityBuildingService(this);
        buildingService.addFirstBuilding(isCapital);
        constructionService = new CityConstructionService(this);
        combatService = new CityCombatService(this);

        // media
        view = new CityView(cityName);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    public L10n getName() {
        return view.getName();
    }

    @Override
    public CombatStrength getBaseCombatStrength() {
        return combatService.getBaseCombatStrength();
    }

    @Override
    public CombatStrength getCombatStrength() {
        return combatService.getCombatStrength();
    }

    @Override
    public void setCombatStrength(CombatStrength combatStrength) {
        combatService.setCombatStrength(combatStrength);
    }

    public void destroy() {
        CombatStrength destroyed = getCombatStrength().copy()
            .isDestroyed(true)
            .build();

        combatService.setCombatStrength(destroyed);
    }

    @Override
    public CombatStrength calcCombatStrength() {
        return combatService.calcCombatStrength();
    }

    public int getPassScore() {
        return passScore;
    }

    @Override
    public UnitCategory getUnitCategory() {
        return combatService.getUnitCategory();
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
        return combatService.isDestroyed();
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

    public Supply calcTilesSupply() {
        return populationService.calcAllCitizensSupply();
    }

    @Override
    public Supply calcSupply() {
        Supply supply = Supply.builder().population(getCitizenCount()).build();

        return supply
            .add(populationService.calcSupply())
            .add(buildingService.calcSupply())
            .add(constructionService.calcSupply());
    }

    @Override
    public void startYear() {
        populationService.startYear();
        buildingService.startYear();
        constructionService.startYear();
        combatService.startYear();

        // can do actions (attack, buy, build etc)
        passScore = 1;
    }

    @Override
    public void stopYear() {
        // population
        populationService.stopYear();
        Supply populationSupply = populationService.getSupply();
        supply = supply.add(populationSupply);

        // buildings
        buildingService.stopYear();
        Supply buildingsSupply = buildingService.getSupply();
        supply = supply.add(buildingsSupply);

        // construction
        constructionService.stopYear();
        Supply constructionSupply = constructionService.getSupply();
        supply = supply.add(constructionSupply);

        // military
        combatService.stopYear();

        // event
        civilization.addEvent(CityStopYearEvent.builder()
            .cityName(getName())
            .populationSupply(populationSupply)
            .buildingsSupply(buildingsSupply)
            .constructionSupply(constructionSupply)
            .totalSupply(supply)
            .build());
    }
}
