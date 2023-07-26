package com.tsoft.civilization.civilization.unit;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.unit.catalog.warriors.Warriors;
import com.tsoft.civilization.unit.action.move.MoveUnitService;
import com.tsoft.civilization.unit.action.move.PassCost;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Stream;

@Slf4j
public class CivilizationUnitService {

    private final World world;
    private final Civilization civilization;

    // Active units and destroyed (on this step) units
    private final UnitList units = new UnitList();
    private UnitList destroyedUnits = new UnitList();

    private final MoveUnitService moveUnitService = new MoveUnitService();

    @Getter
    private Supply supply = Supply.EMPTY;

    public CivilizationUnitService(Civilization civilization) {
        this.civilization = civilization;
        this.world = civilization.getWorld();
    }

    public Stream<? extends AbstractUnit> stream() {
        return units.stream();
    }

    public UnitList getUnits() {
        return units.unmodifiableCopy();
    }

    public int size() {
        return units.size();
    }

    public UnitList findByClassUuid(String classUuid) {
        return units.findByClassUuid(classUuid);
    }

    public AbstractUnit getAny() {
        return units.isEmpty() ? null : units.getAny();
    }

    public AbstractUnit getUnitById(String unitId) {
        return units.getUnitById(unitId);
    }

    public UnitList getUnitsAtLocation(Point location) {
        List<Point> locations = new ArrayList<>(1);
        locations.add(location);

        return getUnitsAtLocations(locations);
    }

    // Find a location to place warriors
    public Point getWarriorsStartLocation(Civilization civ, Point settlersLocation) {
        // try to place Warriors near the Settlers
        List<Point> locations = new ArrayList<>(world.getLocationsAround(settlersLocation, 2));
        Collections.shuffle(locations);

        Warriors warriors = UnitFactory.newInstance(civ, Warriors.CLASS_UUID);
        for (Point location : locations) {
            if (civ.getUnitService().addUnit(warriors, location)) {
                return location;
            }
        }

        return null;
    }

    public UnitList getUnitsAround(AbstractUnit unit, int radius) {
        return getUnitsAround(unit.getLocation(), radius);
    }

    public int getMilitaryCount() {
        return units.getMilitaryCount();
    }

    public int getCivilCount() {
        return units.getCivilCount();
    }

    public UnitList getUnitsAtLocations(Collection<Point> locations) {
        return units.getUnitsAtLocations(locations);
    }

    public UnitList getUnitsAround(Point location, int radius) {
        Collection<Point> locations = world.getLocationsAround(location, radius);
        return getUnitsAtLocations(locations);
    }

    public UnitList getUnitsWithActionsAvailable() {
        return units.getUnitsWithAvailableActions();
    }

    // This is not a move (or a swap), just a check, can be a unit placed here, or not
    public boolean canBePlaced(AbstractUnit unit, Point location) {
        AbstractTerrain tile = world.getTilesMap().getTile(location);
        if (moveUnitService.getPassCost(unit, tile) == PassCost.UNPASSABLE) {
            return false;
        }

        UnitList units = world.getUnitsAtLocation(location);
        AbstractUnit sameCategoryUnit = units.findUnitByCategory(unit.getUnitCategory());
        return sameCategoryUnit == null;
    }

    public boolean addUnit(AbstractUnit unit, Point location) {
        if (canBePlaced(unit, location)) {
            units.add(unit);
            unit.getMovementService().moveTo(location);

            notifyDependentServices();
            return true;
        }

        return false;
    }

    public HasCombatStrength getAttackerById(String attackerId) {
        HasCombatStrength attacker = getUnitById(attackerId);
        if (attacker == null) {
            attacker = civilization.getCityService().getCityById(attackerId);
        }
        return attacker;
    }

    public void destroyUnit(AbstractUnit unit) {
        unit.destroy();
        removeUnit(unit);
    }

    public void removeUnit(AbstractUnit unit) {
        units.remove(unit);
        destroyedUnits.add(unit);
        unit.setCivilization(null);

        notifyDependentServices();
    }

    public boolean canBuyUnit(AbstractUnit unit, City city) {
        if (canBePlaced(unit, city.getLocation())) {
            double gold = civilization.getSupply().getGold();
            return gold >= unit.getGoldCost(civilization);
        }
        return false;
    }

    public Supply buyUnit(AbstractUnit unit) {
        int gold = unit.getGoldCost(civilization);
        return Supply.builder().gold(-gold).build();
    }

    public int getGoldKeepingExpenses() {
        int goldExpenses = 0;
        for (AbstractUnit unit : units) {
            goldExpenses += unit.getGoldUnitKeepingExpenses();
        }
        return goldExpenses;
    }

    // units keeping
    public Supply calcSupply() {
        Supply supply = Supply.EMPTY;

        int unitKeepingGold = getGoldKeepingExpenses();
        if (unitKeepingGold != 0) {
            supply = Supply.builder().gold(-unitKeepingGold).build();
        }
        return supply;
    }

    public void startYear() {
        destroyedUnits = new UnitList();
        units.stream().forEach(AbstractUnit::startYear);
    }

    public void startEra() {
        units.stream().forEach(AbstractUnit::startEra);
    }

    public void stopYear() {
        units.stream().forEach(AbstractUnit::stopYear);
        supply = calcSupply();
    }

    private void notifyDependentServices() {
        civilization.getHappinessService().recalculate();
        civilization.getUnhappinessService().recalculate();
    }
}
