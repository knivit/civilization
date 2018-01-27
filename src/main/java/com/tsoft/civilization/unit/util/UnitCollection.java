package com.tsoft.civilization.unit.util;

import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCategory;
import com.tsoft.civilization.util.Point;

import java.util.Collection;
import java.util.List;

public interface UnitCollection extends List<AbstractUnit> {
    AbstractUnit findByClassUuid(String uuid);

    int getUnitClassCount(Class<? extends AbstractUnit> unitClass);

    AbstractUnit findMilitaryUnit();

    AbstractUnit findCivilUnit();

    AbstractUnit findUnitByUnitKind(UnitCategory unitCategory);

    int getMilitaryCount();

    int getCivilCount();

    int getGreatGeneralCount();

    List<Point> getLocations();

    AbstractUnit getUnitById(String unitId);

    UnitCollection getUnitsAtLocations(Collection<Point> locations);

    UnitCollection getUnitsWithActionsAvailable();

    void resetPassScore();

    int getGoldKeepingExpenses();

    void sortByName();
}
