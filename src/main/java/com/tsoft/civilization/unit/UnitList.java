package com.tsoft.civilization.unit;

import com.tsoft.civilization.unit.catalog.greatgeneral.GreatGeneral;
import com.tsoft.civilization.util.AList;
import com.tsoft.civilization.util.Point;

import java.util.*;
import java.util.stream.Collectors;

public class UnitList extends AList<AbstractUnit> {

    public UnitList findByClassUuid(String classUuid) {
        return filter(u -> u.getClassUuid().equals(classUuid));
    }

    public int getUnitClassCount(Class<? extends AbstractUnit> unitClass) {
        return (int)list.stream().filter(e -> e.getClass().equals(unitClass)).count();
    }

    public AbstractUnit findUnitByCategory(UnitCategory unitCategory) {
        return findAny(e -> e.getUnitCategory().equals(unitCategory));
    }

    public AbstractUnit findFirstMilitaryUnit() {
        return findAny(e -> e.getUnitCategory().isMilitary());
    }

    public AbstractUnit findFirstCivilUnit() {
        return findAny(e -> !e.getUnitCategory().isMilitary());
    }

    public int getMilitaryCount() {
        return (int)list.stream()
            .filter(e -> e.getUnitCategory().isMilitary())
            .count();
    }

    public int getCivilCount() {
        return size() - getMilitaryCount();
    }

    public int getGreatGeneralCount() {
        return getUnitClassCount(GreatGeneral.class);
    }

    public List<Point> getLocations() {
        return list.stream()
            .map(AbstractUnit::getLocation)
            .collect(Collectors.toList());
    }

    public AbstractUnit getUnitById(String unitId) {
        return findAny(e -> e.getId().equals(unitId));
    }

    public UnitList getUnitsAtLocations(Set<Point> locations) {
        return (locations == null || locations.isEmpty()) ?
            new UnitList() : filter(e -> locations.contains(e.getLocation()));
    }

    public UnitList getUnitsWithAvailableActions() {
        return filter(AbstractUnit::isActionAvailable);
    }

    public List<AbstractUnit> sortByName() {
        return stream()
            .sorted(Comparator.comparing(a -> a.getView().getLocalizedName()))
            .toList();
    }
}
