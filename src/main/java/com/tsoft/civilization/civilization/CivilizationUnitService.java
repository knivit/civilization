package com.tsoft.civilization.civilization;

import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TilePassCostTable;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.event.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class CivilizationUnitService {
    private final World world;
    private final Civilization civilization;

    // Active units and destroyed (on this step) units
    private final UnitList units = new UnitList();
    private UnitList destroyedUnits = new UnitList();

    private final TileService tileService = new TileService();

    public CivilizationUnitService(Civilization civilization) {
        this.civilization = civilization;
        this.world = civilization.getWorld();
    }

    public Stream<? extends AbstractUnit> stream() {
        return units.stream();
    }

    public UnitList getUnits() {
        return units.unmodifiableList();
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

    public HasCombatStrength getAttackerById(String attackerId) {
        HasCombatStrength attacker = getUnitById(attackerId);
        if (attacker == null) {
            attacker = civilization.cities().getCityById(attackerId);
        }
        return attacker;
    }

    // This is not a move (or swap), just a check, can be a unit placed here, or not
    public boolean canBePlaced(AbstractUnit unit, Point location) {
        AbstractTile tile = world.getTilesMap().getTile(location);
        if (tileService.getPassCost(unit, tile) == TilePassCostTable.UNPASSABLE) {
            return false;
        }

        UnitList units = world.getUnitsAtLocation(location);
        AbstractUnit sameCategoryUnit = units.findUnitByCategory(unit.getUnitCategory());
        return sameCategoryUnit == null;
    }

    public boolean addUnit(AbstractUnit unit, Point location) {
        if (canBePlaced(unit, location)) {
            units.add(unit);
            unit.setLocation(location);
            return true;
        }

        return false;
    }

    public void removeUnit(AbstractUnit unit) {
        units.remove(unit);
        destroyedUnits.add(unit);
        unit.setCivilization(null);
    }

    public boolean canBuyUnit(AbstractUnit unit, City city) {
        if (canBePlaced(unit, city.getLocation())) {
            int gold = civilization.getSupply().getGold();
            return gold >= unit.getGoldCost();
        }
        return false;
    }

    public Supply buyUnit(String unitClassUuid, City city) {
        AbstractUnit unit = UnitFactory.newInstance(civilization, unitClassUuid);
        if (!addUnit(unit, city.getLocation())) {
            throw new IllegalStateException("Can't buy a unit");
        }

        int gold = unit.getGoldCost();
        Supply expenses = Supply.builder().gold(-gold).build();

        civilization.addEvent(new Event(Event.INFORMATION, expenses, L10nCivilization.BUY_UNIT_EVENT));
        return expenses;
    }

    public int getGoldKeepingExpenses() {
        int goldExpenses = 0;
        for (AbstractUnit unit : units) {
            goldExpenses += unit.getGoldUnitKeepingExpenses();
        }
        return goldExpenses;
    }

    // units keeping
    public Supply getSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;

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

    public void stopYear() {
        units.stream().forEach(AbstractUnit::stopYear);
    }
}
