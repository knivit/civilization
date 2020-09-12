package com.tsoft.civilization.unit;

import com.tsoft.civilization.util.Point;

import java.util.Collection;
import java.util.List;

public interface UnitCollection extends List<AbstractUnit<?>> {
    UnitCollection findByClassUuid(String uuid);

    int getUnitClassCount(Class<? extends AbstractUnit<?>> unitClass);

    AbstractUnit<?> findMilitaryUnit();

    AbstractUnit<?> findCivilUnit();

    AbstractUnit<?> findUnitByUnitKind(UnitCategory unitCategory);

    int getMilitaryCount();

    int getCivilCount();

    int getGreatGeneralCount();

    List<Point> getLocations();

    AbstractUnit<?> getUnitById(String unitId);

    UnitCollection getUnitsAtLocations(Collection<Point> locations);

    UnitCollection getUnitsWithActionsAvailable();

    void sortByName();
}
