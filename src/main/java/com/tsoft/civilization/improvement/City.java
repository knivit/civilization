package com.tsoft.civilization.improvement;

import com.tsoft.civilization.L10n.L10nChainedArrayList;
import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.L10n.L10nImprovement;
import com.tsoft.civilization.L10n.L10nMap;
import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.util.BuildingCollection;
import com.tsoft.civilization.building.Palace;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.combat.skill.AbstractSkill;
import com.tsoft.civilization.improvement.city.CityBuildingService;
import com.tsoft.civilization.improvement.city.CityCitizenService;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.unit.util.UnitFactory;
import com.tsoft.civilization.world.util.Event;
import com.tsoft.civilization.world.economic.BuildingScore;
import com.tsoft.civilization.world.economic.CitizenScore;
import com.tsoft.civilization.world.economic.CityScore;
import com.tsoft.civilization.improvement.city.CitySupplyStrategy;
import com.tsoft.civilization.improvement.city.Construction;
import com.tsoft.civilization.world.economic.CitySupply;
import com.tsoft.civilization.world.economic.ImprovementScore;
import com.tsoft.civilization.world.economic.ImprovementSupply;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.improvement.CityView;
import com.tsoft.civilization.world.Civilization;

import java.util.*;

public class City extends AbstractImprovement<CityView> implements HasCombatStrength {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final CombatStrength COMBAT_STRENGTH = new CombatStrength()
            .setTargetBackFireStrength(5)
            .setStrength(50)
            .setRangedAttackStrength(10)
            .setRangedAttackRadius(2);

    private final CityView VIEW;

    private Set<Point> locations = new HashSet<>();
    private Set<Point> unmodifiableLocations = Collections.unmodifiableSet(locations);

    private CityCitizenService citizenService;
    private CityBuildingService buildingService;

    private CityScore cityScore;
    private boolean needCityScoreRecalculation;

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
        cityScore = new CityScore(civilization);

        // city's tiles
        locations.add(location);
        locations.addAll(getTilesMap().getLocationsAround(location, 1));

        // first citizen
        citizenService = new CityCitizenService(this);
        addCitizen(L10nCity.FOUNDED_SETTLERS);

        // city's initial buildings
        buildingService = new CityBuildingService(this);

        // City is a ranged military unit
        combatStrength = new CombatStrength(this, getBaseCombatStrength());
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }

    @Override
    public ImprovementScore getSupply() {
        ImprovementScore score = new ImprovementScore(getCivilization());
        score.add(new ImprovementSupply(0, 0, 0, -1), L10nImprovement.IMPROVEMENT_EXPENSES_SUPPLY);
        return score;
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
        needCityScoreRecalculation = true;
        this.locations.addAll(locations);
    }

    public boolean isHavingTile(Point location) {
        return locations.contains(location);
    }

    public CitySupplyStrategy getSupplyStrategy() {
        return citizenService.getSupplyStrategy();
    }

    public void setSupplyStrategy(CitySupplyStrategy supplyStrategy) {
        needCityScoreRecalculation = true;
        citizenService.setSupplyStrategy(supplyStrategy);
    }

    public void addCitizen(L10nMap description) {
        citizenService.addCitizen();

        CitySupply citySupply = new CitySupply(0, 0, 0, 1);
        cityScore.add(citySupply, description);
        needCityScoreRecalculation = true;
    }

    public int getCitizenCount() {
        return citizenService.getCitizenCount();
    }

    public BuildingCollection getBuildings() {
        return buildingService.getBuildings();
    }

    public AbstractBuilding getBuildingById(String buildingId) {
        return buildingService.getBuildingById(buildingId);
    }

    public void addBuilding(AbstractBuilding building) {
        buildingService.add(building);
        civilization.addEvent(new Event(this, L10nCity.NEW_BUILDING_BUILT_EVENT, Event.INFORMATION));

        // check is this building adds defense strength
        int strength = getCombatStrength().getStrength();
        strength += building.getStrength();
        getCombatStrength().setStrength(strength);

        needCityScoreRecalculation = true;
    }

    public void destroyBuilding(AbstractBuilding building) {
        buildingService.remove(building);
        needCityScoreRecalculation = true;
    }

    public Collection<Point> getCitizenLocations() {
        return citizenService.getCitizenLocations();
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

    // Construction of a building or unit is finished
    public void constructionDone(Construction construction) {
        needCityScoreRecalculation = true;

        CanBeBuilt obj = construction.getObject();
        if (obj instanceof AbstractBuilding) {
            AbstractBuilding building = AbstractBuilding.newInstance(obj.getClassUuid(), this);
            addBuilding(building);
            return;
        }

        if (obj instanceof AbstractUnit) {
            UnitFactory.newInstance((AbstractUnit)obj, getCivilization(), getLocation());
            getWorld().sendEvent(new Event(obj, L10nCity.NEW_UNIT_BUILT_EVENT, Event.UPDATE_WORLD));
            return;
        }

        String objectInfo = (obj == null ? "null" : obj.getClass().getName());
        throw new IllegalArgumentException("Unknown object is constructed: " + objectInfo);
    }

    public CityScore getCityScore() {
        if (needCityScoreRecalculation) {
            calcCityScore();
        }
        return cityScore;
    }

    public void step() {
        // income from laborers
        citizenService.step();

        // buildings & construction
        buildingService.step(cityScore);

        calcCityScore();

        // can do actions (attack, buy, build etc)
        passScore = 1;
    }

    private void calcCityScore() {
        CitySupply accumulationSupply = new CitySupply(0, 0, 0, getCitizenCount());

        cityScore = new CityScore(civilization);
        cityScore.add(accumulationSupply, L10nCity.ACCUMULATION_SUPPLY);

        CitizenScore citizenScore = citizenService.getCitizenScore();
        cityScore.add(citizenScore);

        BuildingScore buildingScore = buildingService.getBuildingScore();
        cityScore.add(buildingScore);

        needCityScoreRecalculation = false;
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
    public UnitCollection getUnitsAround(int radius) {
        return civilization.getUnitsAround(getLocation(), radius);
    }

    @Override
    public List<AbstractSkill> getSkills() {
        return Collections.EMPTY_LIST;
    }

    /** A city may be destroyed only during a melee attack */
    @Override
    public void destroyBy(HasCombatStrength destroyer, boolean destroyOtherUnitsAtLocation) {
        getCombatStrength().setDestroyed(true);
        setPassScore(0);

        getWorld().sendEvent(new Event(this, L10nCity.CITY_WAS_CAPTURED, Event.UPDATE_WORLD));
        destroyer.getCivilization().addEvent(new Event(destroyer, L10nCity.UNIT_HAS_CAPTURED_CITY, Event.UPDATE_WORLD));

        // capture the city
        getCivilization().removeCity(this);
        destroyer.getCivilization().addCity(this);

        // destroy all military units located in the city and capture civilians
        UnitCollection units = civilization.getWorld().getUnitsAtLocation(location);
        for (AbstractUnit unit : units) {
            if (unit.getUnitCategory().isMilitary()) {
                unit.destroyBy(destroyer, false);
            } else {
                unit.captureBy(destroyer);
            }
        }
    }

    public int getUnitProductionCost(String unitClassUuid) {
        AbstractUnit unit = UnitFactory.getUnitFromCatalogByClassUuid(unitClassUuid);
        return unit.getProductionCost();
    }

    public int getUnitBuyCost(String unitClassUuid) {
        AbstractUnit unit = UnitFactory.getUnitFromCatalogByClassUuid(unitClassUuid);
        return unit.getGoldCost();
    }

    /** Return true, if the unit can be placed (i.e. after a buy or on entering) in the city */
    public boolean canTakeUnit(AbstractUnit unit) {
        UnitCollection units = getWorld().getUnitsAtLocation(getLocation());
        AbstractUnit sameTypeUnit = units.findUnitByUnitKind(unit.getUnitCategory());
        return sameTypeUnit == null;
    }

    public int getBuildingProductionCost(String unitClassUuid) {
        AbstractBuilding building = AbstractBuilding.getBuildingFromCatalogByClassUuid(unitClassUuid);
        return building.getProductionCost();
    }

    public int getBuildingBuyCost(String unitClassUuid) {
        AbstractBuilding building = AbstractBuilding.getBuildingFromCatalogByClassUuid(unitClassUuid);
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
