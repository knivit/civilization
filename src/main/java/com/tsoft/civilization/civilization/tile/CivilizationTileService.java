package com.tsoft.civilization.civilization.tile;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.HasHistory;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CivilizationTileService implements HasHistory {

    private final Civilization civilization;

    /** A civilization's territory is a sum its cities territory */
    public List<Point> getCivilizationLocations() {
        return civilization.getCityService().stream()
            .flatMap(c -> c.getTileService().getLocations().stream())
            .collect(Collectors.toList());
    }

    /** Points (not territory) where all cities are located */
    public Collection<Point> getCitiesLocations() {
        return civilization.getCityService().stream()
            .map(e -> e.getLocation()).collect(Collectors.toList());
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

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
}
