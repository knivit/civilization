package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TilePassCostTable;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.event.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CivilizationUnitService {
    private final World world;
    private final Civilization civilization;

    // Active units and destroyed (on this step) units
    private final UnitList<?> units = new UnitList<>();
    private UnitList<?> destroyedUnits = new UnitList<>();

    public CivilizationUnitService(Civilization civilization) {
        this.civilization = civilization;
        this.world = civilization.getWorld();
    }

    public UnitList<?> findByClassUuid(String classUuid) {
        return units.findByClassUuid(classUuid);
    }

    public void addFirstUnits() {
        // find a location for the Settlers
        Point settlersLocation = world.getCivilizationStartLocation(civilization);
        if (settlersLocation == null) {
            return;
        }

        // create the Settlers unit
        Settlers settlers = UnitFactory.newInstance(Settlers.CLASS_UUID);
        addUnit(settlers, settlersLocation);

        // try to place Warriors near the Settlers
        Warriors warriors = UnitFactory.newInstance(Warriors.CLASS_UUID);
        ArrayList<Point> locations = world.getLocationsAround(settlersLocation, 2);
        for (Point location : locations) {
            AbstractTile tile = world.getTilesMap().getTile(location);
            if (tile.getPassCost(civilization, Warriors.STUB) != TilePassCostTable.UNPASSABLE) {
                addUnit(warriors, location);
                break;
            }
        }
    }

    public AbstractUnit getAny() {
        return units.isEmpty() ? null : units.getAny();
    }

    public AbstractUnit getUnitById(String unitId) {
        return units.getUnitById(unitId);
    }

    public UnitList<?> getUnits() {
        return units.unmodifiableList();
    }

    public int size() {
        return units.size();
    }

    public UnitList<?> getUnitsAtLocation(Point location) {
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

    public UnitList<?> getUnitsAtLocations(Collection<Point> locations) {
        return units.getUnitsAtLocations(locations);
    }

    public UnitList<?> getUnitsAround(Point location, int radius) {
        Collection<Point> locations = world.getLocationsAround(location, radius);
        return getUnitsAtLocations(locations);
    }

    public UnitList<?> getUnitsWithActionsAvailable() {
        return units.getUnitsWithActionsAvailable();
    }

    public HasCombatStrength getAttackerById(String attackerId) {
        HasCombatStrength attacker = getUnitById(attackerId);
        if (attacker == null) {
            attacker = civilization.cities().getCityById(attackerId);
        }
        return attacker;
    }

    public void addUnit(AbstractUnit unit, Point location) {
        units.add(unit);
        unit.setLocation(location);
        unit.setCivilization(civilization);
    }

    public void removeUnit(AbstractUnit unit) {
        units.remove(unit);
        destroyedUnits.add(unit);
        unit.setCivilization(null);

        world.sendEvent(new Event(Event.UPDATE_WORLD, this, L10nUnit.UNIT_WAS_DESTROYED_EVENT, unit.getView().getLocalizedName()));
    }

    public boolean canBuyUnit(AbstractUnit unit) {
        int gold = civilization.getSupply().getGold();
        return gold >= unit.getGoldCost();
    }

    public Supply buyUnit(String unitClassUuid, City city) {
        AbstractUnit unit = UnitFactory.newInstance(unitClassUuid);
        addUnit(unit, city.getLocation());

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
        destroyedUnits = new UnitList<>();
        units.stream().forEach(AbstractUnit::startYear);
    }

    public void move() {
        units.stream().forEach(AbstractUnit::move);
    }

    public void stopYear() {
        units.stream().forEach(AbstractUnit::stopYear);
    }

}
