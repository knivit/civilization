package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.building.*;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.civilization.population.Happiness;
import com.tsoft.civilization.combat.service.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.improvement.city.construction.CanBeBuilt;
import com.tsoft.civilization.improvement.city.population.CityHappinessService;
import com.tsoft.civilization.improvement.city.supply.CitySupplyService;
import com.tsoft.civilization.improvement.city.tile.CityTileService;
import com.tsoft.civilization.improvement.city.building.CityBuildingService;
import com.tsoft.civilization.combat.service.CityCombatService;
import com.tsoft.civilization.improvement.city.construction.CityConstructionService;
import com.tsoft.civilization.improvement.city.construction.ConstructionList;
import com.tsoft.civilization.improvement.city.event.CityStopYearEvent;
import com.tsoft.civilization.improvement.city.population.CityPopulationService;
import com.tsoft.civilization.improvement.city.supply.TileSupplyStrategy;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.TileType;
import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.economic.*;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.HasHistory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class City extends AbstractImprovement implements HasCombatStrength, HasHistory {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private CityTileService tileService;

    @Getter
    private CityPopulationService populationService;

    private CityBuildingService buildingService;
    private CityConstructionService constructionService;
    private CityCombatService combatService;

    @Getter
    private CitySupplyService supplyService;

    @Getter
    private CityHappinessService happinessService;

    @Getter
    private CityView view;

    @Getter
    @Setter
    private int passScore;

    @Getter
    private Supply supply = Supply.EMPTY;

    @Getter
    private Happiness happiness = Happiness.EMPTY;

    @Getter
    private boolean annexed;

    public City(Civilization civilization, Point location) {
        super(civilization, location);
    }

    public void init(L10n cityName, boolean isCapital) {
        // area
        tileService = new CityTileService(this);
        tileService.addStartLocations(location);

        // population
        populationService = new CityPopulationService(this);
        populationService.addCitizen();

        // buildings & construction
        buildingService = new CityBuildingService(this);
        buildingService.addFirstBuilding(isCapital);
        constructionService = new CityConstructionService(this);
        combatService = new CityCombatService(this);

        // economics
        supplyService = new CitySupplyService(this);
        happinessService = new CityHappinessService(this);

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

    @Override
    public CombatStrength calcCombatStrength() {
        return combatService.calcCombatStrength();
    }

    @Override
    public void destroy() {
        CombatStrength destroyed = getCombatStrength().copy()
            .isDestroyed(true)
            .build();

        combatService.setCombatStrength(destroyed);
    }

    @Override
    public UnitCategory getUnitCategory() {
        return combatService.getUnitCategory();
    }

    public List<TileSupplyStrategy> getSupplyStrategy() {
        return supplyService.getSupplyStrategy();
    }

    public void setSupplyStrategy(List<TileSupplyStrategy> supplyStrategy) {
        if (supplyService.setSupplyStrategy(supplyStrategy)) {
            populationService.reorganizeCitizensOnTiles();
        }
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

    public List<Point> getCitizenLocations() {
        return populationService.getCitizenLocations();
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
    public boolean isDestroyed() {
        return combatService.isDestroyed();
    }

    @Override
    public List<AbstractSkill> getSkills() {
        return Collections.emptyList();
    }

    /** City has moved to another Civilization (was captured) */
    public void moveToCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public void addUnit(AbstractUnit unit) {
        civilization.getUnitService().addUnit(unit, location);
    }

    public int getUnitProductionCost(String unitClassUuid) {
        AbstractUnit unit = UnitFactory.findByClassUuid(unitClassUuid);
        return unit.getProductionCost(civilization);
    }

    public int getUnitBuyCost(String unitClassUuid) {
        AbstractUnit unit = UnitFactory.findByClassUuid(unitClassUuid);
        return unit.getGoldCost(civilization);
    }

    public int getBuildingProductionCost(String buildingClassUuid) {
        AbstractBuilding building = BuildingFactory.findByClassUuid(buildingClassUuid);
        return building.getProductionCost(civilization);
    }

    public int getBuildingBuyCost(String buildingClassUuid) {
        AbstractBuilding building = BuildingFactory.findByClassUuid(buildingClassUuid);
        return building.getGoldCost(civilization);
    }

    public boolean canPlaceBuilding(AbstractBuilding building) {
        return findBuildingByClassUuid(building.getClassUuid()) == null;
    }

    public Supply calcTilesSupply() {
        return supplyService.calcTilesSupply();
    }

    public Supply calcTilesSupply(Point location) {
        return supplyService.calcTilesSupply(location);
    }

    public Happiness calcHappiness() {
        return happinessService.calcHappiness();
    }

    @Override
    public void startYear() {
        tileService.startYear();
        buildingService.startYear();
        populationService.startYear();
        constructionService.startYear();
        combatService.startYear();

        // can do actions (attack, buy, build etc)
        passScore = 1;
    }

    @Override
    public void stopYear() {
        Supply originalSupply = supply.copy().build();
        Supply incomeSupply = supplyService.calcIncomeSupply();
        Supply outcomeSupply = supplyService.calcOutcomeSupply();
        supply = originalSupply.add(incomeSupply).add(outcomeSupply);

        tileService.stopYear();

        Supply populationSupplyChanges = populationService.stopYear(supply);
        supply = supply.add(populationSupplyChanges);

        buildingService.stopYear();

        Supply constructionSupplyChanges = constructionService.stopYear(supply);
        supply = supply.add(constructionSupplyChanges);

        combatService.stopYear();

        civilization.addEvent(CityStopYearEvent.builder()
            .cityName(getName())
            .originalSupply(originalSupply)
            .incomeSupply(incomeSupply)
            .outcomeSupply(outcomeSupply)
            .totalSupply(supply)
            .build());
    }
}
