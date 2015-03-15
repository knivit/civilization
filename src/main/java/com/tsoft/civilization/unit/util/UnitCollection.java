package com.tsoft.civilization.unit.util;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;

import java.util.Collection;
import java.util.List;

public interface UnitCollection extends List<AbstractUnit> {
    public AbstractUnit findByClassUuid(String uuid);

    public int getUnitClassCount(Class<? extends AbstractUnit> unitClass);

    public AbstractUnit findMilitaryUnit();

    public AbstractUnit findCivilUnit();

    public AbstractUnit findUnitByUnitType(UnitType unitType);

    public int getMilitaryCount();

    public int getCivilCount();

    public int getGreatGeneralCount();

    public List<Point> getLocations();

    public AbstractUnit getUnitById(String unitId);

    public UnitCollection getUnitsAtLocations(Collection<Point> locations);

    public UnitCollection getUnitsWithActionsAvailable();

    public void resetPassScore();

    public int getGoldKeepingExpenses();

    public void sortByName();
}
