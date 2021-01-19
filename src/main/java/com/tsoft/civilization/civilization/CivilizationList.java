package com.tsoft.civilization.civilization;

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

    public Civilization getCivilizationOnTile(Point location) {
        return findAny(e -> e.cities().isHavingTile(location));
    }

    public City getCityAtLocation(Point location) {
        for (Civilization civilization : civilizations) {
            City city = civilization.cities().getCityAtLocation(location);
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
            cities.add(civilization.cities().getCitiesAtLocations(locations));
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
            units.addAll(civilization.units().getUnitsAtLocation(location));
        }
        return units;
    }

    public UnitList getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        UnitList units = new UnitList();
        for (Civilization civilization : civilizations) {
            if (civilization.equals(excludeCivilization)) {
                continue;
            }
            units.addAll(civilization.units().getUnitsAtLocations(locations));
        }
        return units;
    }

    public AbstractUnit getUnitById(String unitId) {
        if (unitId == null) {
            return null;
        }

        return civilizations.stream()
            .map(e -> e.units().getUnitById(unitId))
            .filter(Objects::nonNull)
            .findAny()
            .orElse(null);
    }

    public City getCityById(String cityId) {
        if (cityId == null) {
            return null;
        }

        return civilizations.stream()
            .map(e -> e.cities().getCityById(cityId))
            .filter(Objects::nonNull)
            .findAny()
            .orElse(null);
    }

    public CivilizationList filter(Predicate<Civilization> cond) {
        CivilizationList list = new CivilizationList();
        for (Civilization civ : civilizations) {
            if (cond.test(civ)) {
                list.add(civ);
            }
        }
        return list;
    }

    public Civilization findAny(Predicate<Civilization> cond) {
        return civilizations.stream()
            .filter(cond)
            .findAny()
            .orElse(null);
    }

    public void sortByName() {
        civilizations.sort(Comparator.comparing(c -> c.getView().getLocalizedCivilizationName()));
    }
}
