package com.tsoft.civilization.civilization.city.tile;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.ResourceCategory;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.HasHistory;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class CityTileService implements HasHistory {

    private final TilesMap tilesMap;

    @Getter
    private Point center;

    private final Set<Point> locations = new HashSet<>();

    public CityTileService(City city) {
        tilesMap = city.getCivilization().getWorld().getTilesMap();
    }

    public void addStartLocations(Point location) {
        center = location;

        locations.add(location);
        locations.addAll(tilesMap.getLocationsAround(location, 1));
    }

    /** City's territory */
    public Set<Point> getTerritory() {
        return Collections.unmodifiableSet(locations);
    }

    public boolean isOnTerritory(Point location) {
        return locations.contains(location);
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
            .map(AbstractTerrain::getResource)
            .filter(Objects::nonNull)
            .filter(e -> ResourceCategory.LUXURY.equals(e.getCategory()))
            .collect(Collectors.toList());
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

    }
}
