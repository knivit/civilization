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

    // For the given location test is it on civilisation's territory or not
    public boolean isOnTerritory(Point location) {
        return civilization.equals(civilization.getWorld().getTileService().getCivilizationOnTile(location));
    }

    // For the given location find out a city
    public City findCityByLocationOnTerritory(Point location) {
        return civilization.getWorld().getTileService().getCityOnTile(location);
    }

    // Look for tile's improvement by its Id
    public AbstractImprovement findImprovementById(String id) {
        AbstractImprovement improvement = civilization.getWorld().getWorldObjectService().get(id);
        return (improvement == null || !civilization.equals(improvement.getCivilization())) ? null : improvement;
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

    }
}
