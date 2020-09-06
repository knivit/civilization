package com.tsoft.civilization.unit.util;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.util.UnmodifiableList;

import java.util.Collection;
import java.util.List;

public class UnmodifiableUnitList extends UnmodifiableList<AbstractUnit<?>> implements UnitCollection {
    private UnitCollection units;

    public UnmodifiableUnitList(UnitCollection units) {
        this.units = units;
    }

    @Override
    public UnitCollection findByClassUuid(String classUuid) {
        return units.findByClassUuid(classUuid);
    }

    @Override
    public int getUnitClassCount(Class<? extends AbstractUnit<?>> unitClass) {
        return units.getUnitClassCount(unitClass);
    }

    @Override
    public AbstractUnit<?> findMilitaryUnit() {
        return units.findMilitaryUnit();
    }

    @Override
    public AbstractUnit<?> findCivilUnit() {
        return units.findCivilUnit();
    }

    @Override
    public AbstractUnit<?> findUnitByUnitKind(UnitCategory unitCategory) {
        return units.findUnitByUnitKind(unitCategory);
    }

    @Override
    public int getMilitaryCount() {
        return units.getMilitaryCount();
    }

    @Override
    public int getCivilCount() {
        return units.getCivilCount();
    }

    @Override
    public int getGreatGeneralCount() {
        return units.getGreatGeneralCount();
    }

    @Override
    public List<Point> getLocations() {
        return units.getLocations();
    }

    @Override
    public AbstractUnit<?> getUnitById(String unitId) {
        return units.getUnitById(unitId);
    }

    @Override
    public UnitCollection getUnitsAtLocations(Collection<Point> locations) {
        return units.getUnitsAtLocations(locations);
    }

    @Override
    public UnitCollection getUnitsWithActionsAvailable() {
        return units.getUnitsWithActionsAvailable();
    }

    @Override
    protected List<AbstractUnit<?>> getList() {
        return units;
    }

    @Override
    public void sortByName() {
        units.sortByName();
    }
}
