package com.tsoft.civilization.civilization;

import com.tsoft.civilization.util.Point;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CivilizationTerritoryService {

    private final Civilization civilization;

    public void startYear() {

    }

    /** A civilization's territory is a sum its cities territory */
    public List<Point> getCivilizationLocations() {
        return civilization.cities().stream()
            .flatMap(c -> c.getLocations().stream())
            .collect(Collectors.toList());
    }

    /** Points (not territory) where all cities are located */
    public Collection<Point> getLocations() {
        return civilization.cities().stream()
            .map(e -> e.getLocation()).collect(Collectors.toList());
    }

    public void stopYear() {

    }
}
