package com.tsoft.civilization.unit;

import com.tsoft.civilization.unit.civil.greatgeneral.GreatGeneral;
import com.tsoft.civilization.util.Point;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UnitList<E extends AbstractUnit> implements Iterable<E> {
    private List<E> units = new ArrayList<>();
    private boolean isUnmodifiable;

    public UnitList() { }

    public UnitList(List<E> units) {
        Objects.requireNonNull(units);
        this.units = units;
    }

    public List<E> getListCopy() {
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

    public Stream<E> stream() {
        return units.stream();
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

    public UnitList<?> findByClassUuid(String classUuid) {
        return filter(u -> u.getClassUuid().equals(classUuid));
    }

    public int getUnitClassCount(Class<? extends AbstractUnit> unitClass) {
        return (int)units.stream().filter(e -> e.getClass().equals(unitClass)).count();
    }

    public AbstractUnit findUnitByUnitKind(UnitCategory unitCategory) {
        return findAny(e -> e.getUnitCategory().equals(unitCategory));
    }

    public AbstractUnit findFirstMilitaryUnit() {
        return findAny(e -> e.getUnitCategory().isMilitary());
    }

    public AbstractUnit findFirstCivilUnit() {
        return findAny(e -> !e.getUnitCategory().isMilitary());
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
        return findAny(e -> e.getId().equals(unitId));
    }

    public UnitList<?> getUnitsAtLocations(Collection<Point> locations) {
        return (locations == null || locations.isEmpty()) ?
            new UnitList<>() : filter(e -> locations.contains(e.getLocation()));
    }

    public UnitList<?> getUnitsWithActionsAvailable() {
        return filter(AbstractUnit::isActionAvailable);
    }

    public UnitList<?> filter(Predicate<AbstractUnit> cond) {
        UnitList<?> list = new UnitList<>();
        for (AbstractUnit unit : units) {
            if (cond.test(unit)) {
                list.add(unit);
            }
        }
        return list;
    }

    public AbstractUnit findAny(Predicate<AbstractUnit> cond) {
        return units.stream()
            .filter(cond)
            .findAny()
            .orElse(null);
    }

}
