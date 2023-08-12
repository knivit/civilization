package com.tsoft.civilization.civilization;

import com.tsoft.civilization.util.AList;
import com.tsoft.civilization.util.l10n.L10n;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.CityList;
import com.tsoft.civilization.unit.UnitList;
import com.tsoft.civilization.util.Point;

import java.util.*;

public class CivilizationList extends AList<Civilization> {

    public Civilization get(int index) {
        return list.get(index);
    }

    public Civilization getCivilizationByName(L10n name) {
        return findAny(e -> e.getName().equals(name));
    }

    public City getCityAtLocation(Point location) {
        for (Civilization civilization : list) {
            City city = civilization.getCityService().getCityAtLocation(location);
            if (city != null) {
                return city;
            }
        }
        return null;
    }

    public CityList getCitiesAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        CityList cities = new CityList();

        for (Civilization civilization : list) {
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

        for (Civilization civilization : list) {
            if (civilization.equals(excludeCivilization)) {
                continue;
            }

            units.addAll(civilization.getUnitService().getUnitsAtLocation(location));
        }
        return units;
    }

    public UnitList getUnitsAtLocations(Set<Point> locations, Civilization excludeCivilization) {
        UnitList units = new UnitList();

        for (Civilization civilization : list) {
            if (civilization.equals(excludeCivilization)) {
                continue;
            }
            units.addAll(civilization.getUnitService().getUnitsAtLocations(locations));
        }
        return units;
    }

    public List<Civilization> sortByName() {
        return stream()
            .sorted(Comparator.comparing(a -> a.getView().getLocalizedName()))
            .toList();
    }
}
