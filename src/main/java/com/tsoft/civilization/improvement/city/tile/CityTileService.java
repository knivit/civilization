package com.tsoft.civilization.improvement.city.tile;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.resource.luxury.AbstractLuxuryResource;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.HasHistory;

import java.util.*;
import java.util.stream.Collectors;

public class CityTileService implements HasHistory {

    private final TileService tileService = new TileService();

    private final City city;
    private final TilesMap tilesMap;
    private Set<Point> locations = new HashSet<>();

    public CityTileService(City city) {
        this.city = city;
        tilesMap = city.getTilesMap();
    }

    public void addStartLocations(Point location) {
        locations.add(location);
        locations.addAll(tilesMap.getLocationsAround(location, 1));
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

    public List<AbstractLuxuryResource> getLuxuryResources() {
        return locations.stream()
            .map(tilesMap::getTile)
            .map(AbstractTile::getLuxury)
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
