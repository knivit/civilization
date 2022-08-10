package com.tsoft.civilization.improvement.city.tile;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.HasHistory;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class CityTileService implements HasHistory {

    @Getter
    private final TilesMap tilesMap;

    private final City city;

    private Point center;
    private final Set<Point> locations = new HashSet<>();

    public CityTileService(City city) {
        this.city = city;

        tilesMap = city.getCivilization().getWorld().getTilesMap();
    }

    public void addStartLocations(Point location) {
        center = location;

        locations.add(location);
        locations.addAll(tilesMap.getLocationsAround(location, 1));
    }

    public Point getLocation() {
        return center;
    }

    public Set<Point> getLocations() {
        return Collections.unmodifiableSet(locations);
    }

    public void addLocations(Collection<Point> locations) {
        this.locations.addAll(locations);
    }

    public boolean isHavingTile(Point location) {
        return locations.contains(location);
    }

    public List<AbstractResource> getLuxuryResources() {
        return locations.stream()
            .map(tilesMap::getTile)
            .map(AbstractTerrain::getLuxury)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

    }
}
