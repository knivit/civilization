package com.tsoft.civilization.civilization;

import com.tsoft.civilization.L10n.L10n;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CivilizationList implements Iterable<Civilization> {
    private final List<Civilization> civilizations = new ArrayList<>();
    private boolean isUnmodifiable;

    public CivilizationList() { }

    public CivilizationList(List<Civilization> civilizations) {
        Objects.requireNonNull(civilizations);
        this.civilizations.addAll(civilizations);
    }

    public CivilizationList unmodifiableList() {
        CivilizationList list = new CivilizationList();
        list.civilizations.addAll(civilizations);
        list.isUnmodifiable = true;
        return list;
    }

    @Override
    public Iterator<Civilization> iterator() {
        return civilizations.listIterator();
    }

    public Stream<Civilization> stream() {
        return civilizations.stream();
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The list is unmodifiable");
        }
    }

    public Civilization get(int index) {
        return civilizations.get(index);
    }

    public CivilizationList add(Civilization civilization) {
        checkIsUnmodifiable();
        civilizations.add(civilization);
        return this;
    }

    public boolean isEmpty() {
        return civilizations.isEmpty();
    }

    public int size() {
        return civilizations.size();
    }

    public Civilization getCivilizationById(String civilizationId) {
        return findAny(e -> e.getId().equals(civilizationId));
    }

    public Civilization getCivilizationByName(L10n name) {
        return findAny(e -> e.getName().equals(name));
    }

    public Civilization getCivilizationOnTile(Point location) {
        return findAny(e -> e.getCityService().isHavingTile(location));
    }

    public City getCityAtLocation(Point location) {
        for (Civilization civilization : civilizations) {
            City city = civilization.getCityService().getCityAtLocation(location);
            if (city != null) {
                return city;
            }
        }
        return null;
    }

    public CityList getCitiesAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        CityList cities = new CityList();

        for (Civilization civilization : civilizations) {
            if (civilization.equals(excludeCivilization)) {
                continue;
            }
            cities.addAll(civilization.getCityService().getCitiesAtLocations(locations));
        }
        return cities;
    }

    public UnitList getUnitsAtLocation(Point location, Civilization excludeCivilization) {
        UnitList units = new UnitList();
        if (location == null) {
            return units;
        }

        for (Civilization civilization : civilizations) {
            if (civilization.equals(excludeCivilization)) {
                continue;
            }

            units.addAll(civilization.getUnitService().getUnitsAtLocation(location));
        }
        return units;
    }

    public UnitList getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        UnitList units = new UnitList();
        for (Civilization civilization : civilizations) {
            if (civilization.equals(excludeCivilization)) {
                continue;
            }
            units.addAll(civilization.getUnitService().getUnitsAtLocations(locations));
        }
        return units;
    }

    public AbstractUnit getUnitById(String unitId) {
        if (unitId == null) {
            return null;
        }

        return civilizations.stream()
            .map(e -> e.getUnitService().getUnitById(unitId))
            .filter(Objects::nonNull)
            .findAny()
            .orElse(null);
    }

    public City getCityById(String cityId) {
        if (cityId == null) {
            return null;
        }

        return civilizations.stream()
            .map(e -> e.getCityService().getCityById(cityId))
            .filter(Objects::nonNull)
            .findAny()
            .orElse(null);
    }

    private Civilization findAny(Predicate<Civilization> cond) {
        return civilizations.stream()
            .filter(cond)
            .findAny()
            .orElse(null);
    }

    public CivilizationList sortByName() {
        List<Civilization> list = new ArrayList<>(civilizations);
        list.sort(Comparator.comparing(e -> e.getView().getLocalizedName()));
        return new CivilizationList(list);
    }
}
