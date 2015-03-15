package com.tsoft.civilization.world.util;

import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.util.UnitCollection;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Civilization;

import java.util.Collection;
import java.util.List;

public interface CivilizationCollection extends List<Civilization> {
    public Civilization getCivilizationById(String civilizationId);

    public Civilization getCivilizationOnTile(Point location);

    public CityCollection getCitiesAtLocations(Collection<Point> locations, Civilization excludeCivilization);

    public City getCityAtLocation(Point location);

    public UnitCollection getUnitsAtLocation(Point location, Civilization excludeCivilization);

    public UnitCollection getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization);

    public AbstractUnit getUnitById(String unitId);

    public City getCityById(String cityId);

    public void sortByName();
}
