package com.tsoft.civilization.civilization.tile;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.HasHistory;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class CivilizationTileService implements HasHistory {

    private final Civilization civilization;

    /** A civilization's territory is a sum its cities territory */
    public Set<Point> getTerritory() {
        return civilization.getCityService().stream()
            .map(e -> e.getTileService().getTerritory())
            .collect(HashSet::new, Set::addAll, Set::addAll);
    }

    // For given location test is it an civilisation's territory or not
    public boolean isOnTerritory(Point location) {
        return civilization.getCityService().stream()
            .anyMatch(e -> e.getTileService().isOnTerritory(location));
    }

    // For given locations find out a city
    public City findCityByLocationOnTerritory(Point location) {
        return civilization.getCityService().stream()
            .filter(e -> e.getTileService().isOnTerritory(location))
            .findAny()
            .orElse(null);
    }

    // Look for tile's improvement by its Id
    public AbstractImprovement findImprovementById(String id) {
        return civilization.getTilesMap().tiles()
            .flatMap(e -> e.getImprovements().stream())
            .filter(Objects::nonNull)
            .filter(e -> id.equals(e.getId()))
            .findFirst()
            .orElse(null);
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

    }
}
