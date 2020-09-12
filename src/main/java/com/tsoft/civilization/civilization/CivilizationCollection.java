package com.tsoft.civilization.civilization;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.CityCollection;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.UnitCollection;
import com.tsoft.civilization.util.Point;

import java.util.Collection;
import java.util.List;

public interface CivilizationCollection extends List<Civilization> {
    Civilization getCivilizationById(String civilizationId);

    Civilization getCivilizationOnTile(Point location);

    CityCollection getCitiesAtLocations(Collection<Point> locations, Civilization excludeCivilization);

    City getCityAtLocation(Point location);

    UnitCollection getUnitsAtLocation(Point location, Civilization excludeCivilization);

    UnitCollection getUnitsAtLocations(Collection<Point> locations, Civilization excludeCivilization);

    AbstractUnit getUnitById(String unitId);

    City getCityById(String cityId);

    void sortByName();
}
