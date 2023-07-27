package com.tsoft.civilization.civilization.city.tile;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.resource.AbstractResource;
import com.tsoft.civilization.tile.resource.ResourceCategory;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.HasHistory;
import com.tsoft.civilization.world.World;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class CityTileService implements HasHistory {

    private final City city;
    private final World world;

    @Getter
    private final TilesMap tilesMap;

    @Getter
    private Point center;

    public CityTileService(City city) {
        this.city = city;
        world = city.getCivilization().getWorld();
        tilesMap = world.getTilesMap();
    }

    public void addStartLocations(Point location) {
        center = location;

        world.getTileService().put(location, city);

        world.getTilesMap()
            .getLocationsAround(location, 1).stream()
            .filter(e -> world.getTilesMap().canBeTerritory(e))
            .forEach(e -> world.getTileService().put(e, city));
    }

    /** City's territory */
    public Set<Point> getTerritory() {
        return world.getTileService().getCityTerritory(city);
    }

    public boolean isOnTerritory(Point location) {
        return city.equals(world.getTileService().getCityOnTile(location));
    }

    public void addLocation(Point location) {
        world.getTileService().put(location, city);
    }

    public void addLocations(Collection<Point> locations) {
        world.getTileService().put(locations, city);
    }

    public List<AbstractResource> getLuxuryResources() {
        return getTerritory().stream()
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
