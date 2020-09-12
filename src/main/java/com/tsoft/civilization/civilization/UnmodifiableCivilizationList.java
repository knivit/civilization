package com.tsoft.civilization.civilization;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCollection;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.util.UnmodifiableList;

import java.util.Collection;
import java.util.List;

public class UnmodifiableCivilizationList extends UnmodifiableList<Civilization> implements CivilizationCollection {
    private CivilizationCollection civilizations;

    public  UnmodifiableCivilizationList(CivilizationCollection civilizations) {
        this.civilizations = civilizations;
    }

    @Override
    public Civilization getCivilizationById(String civilizationId) {
        return civilizations.getCivilizationById(civilizationId);
    }

    @Override
    public Civilization getCivilizationOnTile(Point location) {
        return civilizations.getCivilizationOnTile(location);
    }

    @Override
    public City getCityAtLocation(Point location) {
        return civilizations.getCityAtLocation(location);
    }

    @Override
    public CityCollection getCitiesAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return civilizations.getCitiesAtLocations(locations, excludeCivilization);
    }

    @Override
    public UnitCollection getUnitsAtLocation(Point location, Civilization excludeCivilization) {
        return civilizations.getUnitsAtLocation(location, excludeCivilization);
    }

    @Override
    public UnitCollection getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization) {
        return civilizations.getUnitsAtLocations(locations, excludeCivilization);
    }

    @Override
    public AbstractUnit getUnitById(String unitId) {
        return civilizations.getUnitById(unitId);
    }

    @Override
    public City getCityById(String cityId) {
        return civilizations.getCityById(cityId);
    }

    @Override
    public void sortByName() {
        civilizations.sortByName();
    }

    @Override
    protected List<Civilization> getList() {
        return civilizations;
    }
}
