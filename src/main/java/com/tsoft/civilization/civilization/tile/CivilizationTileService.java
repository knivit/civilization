package com.tsoft.civilization.civilization.tile;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.luxury.AbstractLuxury;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.HasHistory;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CivilizationTileService implements HasHistory {

    private final Civilization civilization;

    /** A civilization's territory is a sum its cities territory */
    public List<Point> getCivilizationLocations() {
        return civilization.getCityService().stream()
            .flatMap(c -> c.getLocations().stream())
            .collect(Collectors.toList());
    }

    public List<AbstractLuxury> getLuxuryResources(TilesMap map, Set<Point> locations) {
        return locations.stream()
            .map(map::getTile)
            .map(AbstractTile::getLuxury)
            .filter(Objects::nonNull)
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
}
