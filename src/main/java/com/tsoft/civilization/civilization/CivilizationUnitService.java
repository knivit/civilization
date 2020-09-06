package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10nCivilization;
import com.tsoft.civilization.L10n.unit.L10nUnit;
import com.tsoft.civilization.combat.HasCombatStrength;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.base.TilePassCostTable;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.civil.Settlers;
import com.tsoft.civilization.unit.military.Warriors;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.unit.util.UnitList;
import com.tsoft.civilization.unit.util.UnmodifiableUnitList;
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
    private UnitCollection units = new UnitList();
    private UnmodifiableUnitList unmodifiableUnits = new UnmodifiableUnitList(units);
    private UnitCollection destroyedUnits = new UnitList();

    public CivilizationUnitService(Civilization civilization) {
        this.civilization = civilization;
        this.world = civilization.getWorld();
    }

    public void addFirstUnits() {
        // find a location for the Settlers
        Point settlersLocation = world.getCivilizationStartLocation(civilization);
        if (settlersLocation == null) {
            return;
        }

        // create the Settlers unit
        Settlers settlers = UnitFactory.newInstance(Settlers.CLASS_UUID);
        civilization.addUnit(settlers, settlersLocation);

        // try to place Warriors near the Settlers
        Warriors warriors = UnitFactory.newInstance(Warriors.CLASS_UUID);
        ArrayList<Point> locations = world.getLocationsAround(settlersLocation, 2);
        for (Point location : locations) {
            AbstractTile<?> tile = world.getTilesMap().getTile(location);
            if (tile.getPassCost(civilization, Warriors.STUB) != TilePassCostTable.UNPASSABLE) {
                civilization.addUnit(warriors, location);
                break;
            }
        }
    }

    public AbstractUnit<?> getUnitById(String unitId) {
        return units.getUnitById(unitId);
    }

    public UnitCollection getUnits() {
        return unmodifiableUnits;
    }

    public UnitCollection getUnitsAtLocation(Point location) {
        List<Point> locations = new ArrayList<>(1);
        locations.add(location);

        return getUnitsAtLocations(locations);
    }

    public UnitCollection getUnitsAtLocations(Collection<Point> locations) {
        return units.getUnitsAtLocations(locations);
    }

    public UnitCollection getUnitsAround(Point location, int radius) {
        Collection<Point> locations = world.getLocationsAround(location, radius);
        return getUnitsAtLocations(locations);
    }

    public UnitCollection getUnitsWithActionsAvailable() {
        return units.getUnitsWithActionsAvailable();
    }

    public HasCombatStrength getAttackerById(String attackerId) {
        HasCombatStrength attacker = getUnitById(attackerId);
        if (attacker == null) {
            attacker = civilization.getCityById(attackerId);
        }
        return attacker;
    }

    public void addUnit(AbstractUnit<?> unit, Point location) {
        units.add(unit);
        unit.setLocation(location);
        unit.setCivilization(civilization);
    }

    public void removeUnit(AbstractUnit<?> unit) {
        units.remove(unit);
        destroyedUnits.add(unit);
        unit.setCivilization(null);

        world.sendEvent(new Event(Event.UPDATE_WORLD, this, L10nUnit.UNIT_WAS_DESTROYED_EVENT, unit.getView().getLocalizedName()));
    }

    public boolean canBuyUnit(AbstractUnit<?> unit) {
        int gold = civilization.getSupply().getGold();
        return gold >= unit.getGoldCost();
    }

    public Supply buyUnit(String unitClassUuid, City city) {
        AbstractUnit<?> unit = UnitFactory.newInstance(unitClassUuid);
        civilization.addUnit(unit, city.getLocation());

        int gold = unit.getGoldCost();
        Supply expenses = Supply.builder().gold(-gold).build();

        civilization.addEvent(new Event(Event.INFORMATION, expenses, L10nCivilization.BUY_UNIT_EVENT));
        return expenses;
    }

    public int getGoldKeepingExpenses() {
        int goldExpenses = 0;
        for (AbstractUnit<?> unit : units) {
            goldExpenses += unit.getGoldUnitKeepingExpenses();
        }
        return goldExpenses;
    }

    public void resetPassScore() {
        for (AbstractUnit<?> unit : units) {
            unit.step();
        }
    }

    public void clearDestroyedUnits() {
        destroyedUnits.clear();
    }
}
