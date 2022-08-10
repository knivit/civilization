package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.building.*;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.civilization.population.Happiness;
import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatExperience;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.*;
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
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.economic.*;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.HasHistory;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.tsoft.civilization.combat.skill.earth.combat.HillVantageCombatSkill.HILL_VANTAGE_COMBAT_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.CityBuildingsCombatSkill.CITY_BUILDINGS_COMBAT_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.CityGarrisonCombatSkill.CITY_GARRISON_COMBAT_SKILL;
import static com.tsoft.civilization.combat.skill.earth.combat.CityPopulationCombatSkill.CITY_POPULATION_COMBAT_SKILL;
import static com.tsoft.civilization.combat.skill.earth.heal.BaseHealingSkill.BASE_HEALING_SKILL;
import static com.tsoft.civilization.world.Year.*;
import static com.tsoft.civilization.world.Year.INFORMATION_ERA;

@Slf4j
public class City extends AbstractImprovement implements HasCombatStrength, HasHistory {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private Civilization civilization;

    @Getter
    private CityTileService tileService;

    @Getter
    private CityPopulationService populationService;

    private CityBuildingService buildingService;
    private CityConstructionService constructionService;
    private CityCombatService combatService;

    @Getter
    private CitySupplyService supplyService;

    @Getter @Setter
    private boolean resistanceMode;

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
    private boolean isAnnexed;

    @Getter
    private boolean isDestroyed;

    public City(AbstractTerrain tile, Civilization civilization) {
        super(tile);
        this.civilization = civilization;
    }

    public void init(L10n cityName, boolean isCapital) {
        // area
        tileService = new CityTileService(this);
        tileService.addStartLocations(getTile().getLocation());

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

        // add the city on the tile
        super.init(this);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    public L10n getName() {
        return view.getName();
    }

    @Override
    public Point getLocation() {
        return tileService.getLocation();
    }

    @Override
    public CombatStrength getBaseCombatStrength(int era) {
        // A basic factor, determined by the current era of the civilization. For example, while in the Ancient Era,
        // a city has a base CS of about 9 - 10, but in the Modern Era, the base increases to 50 - 60.
        int defenseStrength = switch (civilization.getWorld().getYear().getEra()) {
            case ANCIENT_ERA -> 10;
            case CLASSICAL_ERA -> 20;
            case MEDIEVAL_ERA -> 30;
            case RENAISSANCE_ERA -> 40;
            case INDUSTRIAL_ERA -> 50;
            case MODERN_ERA -> 60;
            case ATOMIC_ERA -> 70;
            case INFORMATION_ERA -> 80;
            default -> 100;
        };

        return CombatStrength.builder()
            .rangedAttackStrength(7)
            .rangedAttackRadius(2)
            .meleeAttackStrength(0)
            .defenseStrength(defenseStrength)
            .build();
    }

    @Override
    public SkillMap<AbstractCombatSkill> getBaseCombatSkills(int era) {
        return new SkillMap<>(
            CITY_POPULATION_COMBAT_SKILL, SkillLevel.ONE,
            CITY_BUILDINGS_COMBAT_SKILL, SkillLevel.ONE,
            CITY_GARRISON_COMBAT_SKILL, SkillLevel.ONE,
            HILL_VANTAGE_COMBAT_SKILL, SkillLevel.ONE
        );
    }

    @Override
    public SkillMap<AbstractHealingSkill> getBaseHealingSkills(int era) {
        return new SkillMap<>(
            BASE_HEALING_SKILL, SkillLevel.ONE
        );
    }

    @Override
    public CombatStrength calcCombatStrength() {
        return combatService.calcCombatStrength();
    }

    @Override
    public CombatDamage getCombatDamage() {
        return combatService.getCombatDamage();
    }

    @Override
    public void addCombatDamage(CombatDamage damage) {
        combatService.addCombatDamage(damage);
    }

    @Override
    public void addCombatExperience(CombatExperience experience) {
        combatService.addCombatExperience(experience);
    }

    @Override
    public void destroy() {
        isDestroyed = true;
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
    public boolean acceptTile(AbstractTerrain tile) {
        return (tile.getTileType() != TerrainType.SEA) && (tile.getTileType() != TerrainType.EARTH_ROUGH);
    }

    /** City has moved to another Civilization (was captured) */
    public void moveToCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    public void addUnit(AbstractUnit unit) {
        civilization.getUnitService().addUnit(unit, getTile().getLocation());
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
        supplyService.startYear();

        // can do actions (attack, buy, build etc)
        passScore = 1;
    }

    public void startEra() {
        combatService.startEra();
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
