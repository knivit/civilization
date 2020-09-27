package com.tsoft.civilization.unit;

import com.tsoft.civilization.unit.civil.greatgeneral.GreatGeneral;
import com.tsoft.civilization.util.Point;

import java.util.*;
import java.util.stream.Collectors;

public class UnitList<E extends AbstractUnit> implements Iterable<E> {
    private List<E> units = new ArrayList<>();
    private boolean isUnmodifiable;

    public UnitList() { }

    public UnitList(List<E> units) {
        Objects.requireNonNull(units);
        this.units = units;
    }

    public List<E> getList() {
        return new ArrayList<>(units);
    }

    public UnitList<E> unmodifiableList() {
        UnitList<E> list = new UnitList<>();
        list.units.addAll(units);
        list.isUnmodifiable = true;
        return list;
    }

    @Override
    public Iterator<E> iterator() {
        return units.iterator();
    }

    public E getAny() {
        return units.isEmpty() ? null : units.get(0);
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The list is unmodifiable");
        }
    }

    public UnitList<E> add(AbstractUnit unit) {
        checkIsUnmodifiable();
        units.add((E)unit);
        return this;
    }

    public UnitList<E> addAll(UnitList<?> otherUnits) {
        checkIsUnmodifiable();

        if (otherUnits != null && !otherUnits.isEmpty()) {
            for (AbstractUnit unit : otherUnits ) {
                units.add((E)unit);
            }
        }
        return this;
    }

    public UnitList<E> remove(AbstractUnit unit) {
        units.remove((E)unit);
        return this;
    }

    public boolean isEmpty() {
        return units.isEmpty();
    }

    public int size() {
        return units.size();
    }

    public UnitList<E> findByClassUuid(String classUuid) {
        UnitList<E> list = new UnitList<>();
        units.stream().filter(u -> u.getClassUuid().equals(classUuid)).forEach(list::add);
        return list;
    }

    public int getUnitClassCount(Class<? extends AbstractUnit> unitClass) {
        return (int)units.stream().filter(e -> e.getClass().equals(unitClass)).count();
    }

    public AbstractUnit findUnitByUnitKind(UnitCategory unitCategory) {
        return units.stream().filter(e -> e.getUnitCategory().equals(unitCategory)).findFirst().orElse(null);
    }

    public AbstractUnit findFirstMilitaryUnit() {
        for (AbstractUnit unit : units) {
            if (unit.getUnitCategory().isMilitary()) {
                return unit;
            }
        }
        return null;
    }

    public AbstractUnit findFirstCivilUnit() {
        for (AbstractUnit unit : units) {
            if (!unit.getUnitCategory().isMilitary()) {
                return unit;
            }
        }
        return null;
    }

    public int getMilitaryCount() {
        return (int)units.stream().filter(e -> e.getUnitCategory().isMilitary()).count();
    }

    public int getCivilCount() {
        return size() - getMilitaryCount();
    }

    public int getGreatGeneralCount() {
        return getUnitClassCount(GreatGeneral.class);
    }

    public List<Point> getLocations() {
        return units.stream().map(e -> e.getLocation()).collect(Collectors.toList());
    }

    public AbstractUnit getUnitById(String unitId) {
        return units.stream().filter(e -> e.getId().equals(unitId)).findAny().orElse(null);
    }

    public UnitList<?> getUnitsAtLocations(Collection<Point> locations) {
        if (locations == null || locations.isEmpty()) {
            return new UnitList<>();
        }

        UnitList<?> unitsAtLocations = new UnitList<>();
        for (AbstractUnit unit : units) {
            if (locations.contains(unit.getLocation())) {
                unitsAtLocations.add(unit);
            }
        }
        return unitsAtLocations;
    }

    public UnitList<?> getUnitsWithActionsAvailable() {
        UnitList<?> units = new UnitList<>();
        for (AbstractUnit unit : units) {
            if (!unit.isDestroyed() && unit.getPassScore() > 0) {
                units.add(unit);
            }
        }
        return units;
    }
}
