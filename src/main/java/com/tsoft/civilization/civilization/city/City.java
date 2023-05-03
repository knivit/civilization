package com.tsoft.civilization.civilization.city;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.building.BuildingFactory;
import com.tsoft.civilization.civilization.building.BuildingList;
import com.tsoft.civilization.civilization.city.building.CityBuildingService;
import com.tsoft.civilization.civilization.city.construction.CanBeBuilt;
import com.tsoft.civilization.civilization.city.construction.CityConstructionService;
import com.tsoft.civilization.civilization.city.construction.ConstructionList;
import com.tsoft.civilization.civilization.city.event.CityStopYearEvent;
import com.tsoft.civilization.civilization.city.happiness.CityHappinessService;
import com.tsoft.civilization.civilization.city.citizen.CityCitizenService;
import com.tsoft.civilization.civilization.city.happiness.CityUnhappinessService;
import com.tsoft.civilization.civilization.city.specialist.CitySpecialistService;
import com.tsoft.civilization.civilization.city.supply.CitySupplyService;
import com.tsoft.civilization.civilization.tile.TileSupplyStrategy;
import com.tsoft.civilization.civilization.city.tile.CityTileService;
import com.tsoft.civilization.civilization.happiness.Unhappiness;
import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.building.catalog.palace.Palace;
import com.tsoft.civilization.civilization.happiness.Happiness;
import com.tsoft.civilization.combat.CombatDamage;
import com.tsoft.civilization.combat.CombatExperience;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.*;
import com.tsoft.civilization.combat.service.CityCombatService;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.TerrainType;
import com.tsoft.civilization.unit.*;
import com.tsoft.civilization.economic.*;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.DifficultyLevel;
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
public class City implements HasCombatStrength, HasHistory {

    public static final String CLASS_UUID = UUID.randomUUID().toString();

    @Getter
    private final String id = UUID.randomUUID().toString();

    @Getter
    private final AbstractTerrain tile;

    @Getter
    private Civilization civilization;

    @Getter
    private CityTileService tileService;

    @Getter
    private CityCitizenService populationService;

    @Getter
    private CitySpecialistService specialistService;

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
    private CityUnhappinessService unhappinessService;

    @Getter
    private CityView view;

    @Getter
    @Setter
    private int passScore;

    @Getter
    private Supply supply = Supply.EMPTY;

    @Getter
    private boolean isAnnexed;

    @Getter
    private boolean isDestroyed;

    public City(AbstractTerrain tile, Civilization civilization) {
        this.tile = tile;
        this.civilization = civilization;
    }

    public void init(L10n cityName, boolean isCapital) {
        // area
        tile.setCity(this);
        tileService = new CityTileService(this);
        tileService.addStartLocations(getTile().getLocation());

        // social
        happinessService = new CityHappinessService(this);
        unhappinessService = new CityUnhappinessService(this);

        // citizen
        populationService = new CityCitizenService(this);
        populationService.addCitizen();

        // specialists
        specialistService = new CitySpecialistService(this);

        // buildings & construction
        buildingService = new CityBuildingService(this);
        buildingService.addFirstBuilding(isCapital);
        constructionService = new CityConstructionService(this);
        combatService = new CityCombatService(this);

        // economics
        supplyService = new CitySupplyService(this);

        // media
        view = new CityView(cityName);
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    public DifficultyLevel getDifficultyLevel() {
        return civilization.getWorld().getDifficultyLevel();
    }

    public L10n getName() {
        return view.getName();
    }

    @Override
    public Point getLocation() {
        return tileService.getLocation();
    }

    @Override
    public CombatStrength getBaseCombatStrength(Civilization civilization) {
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
    public SkillMap<AbstractCombatSkill> getBaseCombatSkills(Civilization civilization) {
        return new SkillMap<>(
            CITY_POPULATION_COMBAT_SKILL, SkillLevel.ONE,
            CITY_BUILDINGS_COMBAT_SKILL, SkillLevel.ONE,
            CITY_GARRISON_COMBAT_SKILL, SkillLevel.ONE,
            HILL_VANTAGE_COMBAT_SKILL, SkillLevel.ONE
        );
    }

    @Override
    public SkillMap<AbstractHealingSkill> getBaseHealingSkills(Civilization civilization) {
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
    public void setCombatExperience(CombatExperience experience) {
        combatService.setCombatExperience(experience);
    }

    @Override
    public void destroy() {
        isDestroyed = true;
    }

    public void erase() {
        tile.setCity(null);
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

    public int getSpecialistCount() {
        return specialistService.getSpecialistCount();
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

    public List<AbstractTerrain> getCitizenTiles() {
        return populationService.getCitizenTiles();
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
        return unit.getBaseProductionCost(civilization);
    }

    public int getUnitBuyCost(String unitClassUuid) {
        AbstractUnit unit = UnitFactory.findByClassUuid(unitClassUuid);
        return unit.getGoldCost(civilization);
    }

    public int getBuildingProductionCost(String buildingClassUuid) {
        AbstractBuilding building = BuildingFactory.findByClassUuid(buildingClassUuid);
        return building.getBaseProductionCost(civilization);
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

    public Happiness getHappiness() {
        return happinessService.getHappiness();
    }

    public Unhappiness getUnhappiness() {
        return unhappinessService.getUnhappiness();
    }

    @Override
    public void startYear() {
        tileService.startYear();
        buildingService.startYear();
        populationService.startYear();
        specialistService.startYear();
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

        Supply populationSupplyChange = populationService.stopYear(supply);
        supply = supply.add(populationSupplyChange);

        Supply specialistsSupplyChange = specialistService.stopYear(supply);
        supply = supply.add(specialistsSupplyChange);

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
