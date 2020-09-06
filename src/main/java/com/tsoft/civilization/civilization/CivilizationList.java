package com.tsoft.civilization.civilization;

import com.tsoft.civilization.civilization.CivilizationCollection;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.improvement.city.CityList;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.unit.util.UnitList;
import com.tsoft.civilization.util.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class CivilizationList extends ArrayList<Civilization> implements CivilizationCollection {
    public CivilizationList() {
        super();
    }

    public CivilizationList(CivilizationCollection civilizations) {
        super(civilizations);
    }

    @Override
    public Civilization getCivilizationById(String civilizationId) {
        for (Civilization civilization : this) {
            if (civilization.getId().equals(civilizationId)) {
                return civilization;
            }
        }
        return null;
    }

    @Override
    public Civilization getCivilizationOnTile(Point location) {
        for (Civilization civilization : this) {
            if (civilization.isHavingTile(location)) {
                return civilization;
            }
        }
        return null;
    }

    @Override
    public City getCityAtLocation(Point location) {
        for (Civilization civilization : this) {
            City city = civilization.getCityAtLocation(location);
            if (city != null) {
                return city;
            }
        }
        return null;
    }

    @Override
    public CityCollection getCitiesAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        CityList cities = new CityList();
        for (Civilization civilization : this) {
            if ((excludeCivilization != null) && (civilization.equals(excludeCivilization))) {
                continue;
            }
            cities.addAll(civilization.getCitiesAtLocations(locations));
        }
        return cities;
    }

    @Override
    public UnitCollection getUnitsAtLocation(Point location, Civilization excludeCivilization) {
        UnitList units = new UnitList();
        if (location == null) {
            return units;
        }

        for (Civilization civilization : this) {
            if ((excludeCivilization != null) && (civilization.equals(excludeCivilization))) {
                continue;
            }
            units.addAll(civilization.getUnitsAtLocation(location));
        }
        return units;
    }

    @Override
    public UnitCollection getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        UnitList units = new UnitList();
        for (Civilization civilization : this) {
            if ((excludeCivilization != null) && (civilization.equals(excludeCivilization))) {
                continue;
            }
            units.addAll(civilization.getUnitsAtLocations(locations));
        }
        return units;
    }

    @Override
    public AbstractUnit getUnitById(String unitId) {
        if (unitId == null) {
            return null;
        }

        for (Civilization civilization : this) {
            AbstractUnit unit = civilization.getUnitById(unitId);
            if (unit != null) {
                return unit;
            }
        }
        return null;
    }

    public City getCityById(String cityId) {
        if (cityId == null) {
            return null;
        }

        for (Civilization civilization : this) {
            City city = civilization.getCityById(cityId);
            if (city != null) {
                return city;
            }
        }
        return null;
    }

    @Override
    public void sortByName() {
        // sort by name
        Collections.sort(this, new Comparator<Civilization>() {
            @Override
            public int compare(Civilization c1, Civilization c2) {
                return c1.getView().getLocalizedCivilizationName().compareTo(c2.getView().getLocalizedCivilizationName());
            }
        });
    }
}
