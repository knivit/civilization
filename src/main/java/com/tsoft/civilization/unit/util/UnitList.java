package com.tsoft.civilization.unit.util;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.GreatGeneral;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UnitList extends ArrayList<AbstractUnit> implements UnitCollection {
    public UnitList() {
        super();
    }

    public UnitList(UnitCollection units) {
        super(units);
    }

    @Override
    public AbstractUnit findByClassUuid(String classUuid) {
        for (AbstractUnit unit : this) {
            if (unit.getClassUuid().equals(classUuid)) {
                return unit;
            }
        }
        return null;
    }

    @Override
    public int getUnitClassCount(Class<? extends AbstractUnit> unitClass) {
        int count = 0;
        for (AbstractUnit unit : this) {
            if (unitClass.equals(unit.getClass())) {
                count ++;
            }
        }
        return count;
    }

    @Override
    public AbstractUnit findMilitaryUnit() {
        for (AbstractUnit unit : this) {
            if (unit.getUnitCategory().isMilitary()) {
                return unit;
            }
        }
        return null;
    }

    @Override
    public AbstractUnit findCivilUnit() {
        for (AbstractUnit unit : this) {
            if (!unit.getUnitCategory().isMilitary()) {
                return unit;
            }
        }
        return null;
    }

    @Override
    public AbstractUnit findUnitByUnitKind(UnitCategory unitCategory) {
        for (AbstractUnit unit : this) {
            if (unitCategory.equals(unit.getUnitCategory())) {
                return unit;
            }
        }
        return null;
    }

    @Override
    public int getMilitaryCount() {
        int n = 0;
        for (AbstractUnit unit : this) {
            if (unit.getUnitCategory().isMilitary()) {
                n ++;
            }
        }
        return n;
    }

    @Override
    public int getCivilCount() {
        return size() - getMilitaryCount();
    }

    @Override
    public int getGreatGeneralCount() {
        return getUnitClassCount(GreatGeneral.class);
    }

    @Override
    public List<Point> getLocations() {
        List<Point> locations = new ArrayList<>(size());
        for (AbstractUnit unit : this) {
            locations.add(unit.getLocation());
        }
        return locations;
    }

    @Override
    public AbstractUnit getUnitById(String unitId) {
        for (AbstractUnit unit : this) {
            if (unit.getId().equals(unitId)) {
                return unit;
            }
        }
        return null;
    }

    @Override
    public UnitCollection getUnitsAtLocations(Collection<Point> locations) {
        if (locations == null) {
            return new UnitList();
        }

        UnitList unitsAtLocations = new UnitList();
        for (AbstractUnit unit : this) {
            if (locations.contains(unit.getLocation())) {
                unitsAtLocations.add(unit);
            }
        }
        return unitsAtLocations;
    }

    @Override
    public UnitCollection getUnitsWithActionsAvailable() {
        UnitCollection units = new UnitList();
        for (AbstractUnit unit : this) {
            if (!unit.isDestroyed() && unit.getPassScore() > 0) {
                units.add(unit);
            }
        }
        return units;
    }

    @Override
    public void resetPassScore() {
        for (AbstractUnit unit : this) {
            unit.step();
        }
    }

    @Override
    public int getGoldKeepingExpenses() {
        int goldExpenses = 0;
        for (AbstractUnit unit : this) {
            goldExpenses += unit.getGoldUnitKeepingExpenses();
        }
        return goldExpenses;
    }

    @Override
    public void sortByName() {
        Collections.sort(this, Comparator.comparing(unit -> unit.getView().getLocalizedName()));
    }
}
