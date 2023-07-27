package com.tsoft.civilization.world.service;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;

import java.util.*;
import java.util.stream.Collectors;

public class WorldTileService {

    private final Map<Point, City> territory = new HashMap<>();
    private final Map<City, Set<Point>> cityTerritory = new HashMap<>();
    private final Map<Civilization, Set<Point>> civilizationTerritory = new HashMap<>();

    public WorldTileService(World world) {
        for (AbstractTerrain tile : world.getTilesMap()) {
            put(tile.getLocation(), null);
        }
    }

    public City getCityOnTile(Point location) {
        Objects.requireNonNull(location, "location must be not null");
        return territory.get(location);
    }

    public City getCityOnTile(AbstractTerrain tile) {
        return getCityOnTile((tile == null) ? null : tile.getLocation());
    }

    public Civilization getCivilizationOnTile(Point location) {
        City city = getCityOnTile(location);
        return (city == null) ? null : city.getCivilization();
    }

    public Civilization getCivilizationOnTile(AbstractTerrain tile) {
        return getCivilizationOnTile((tile == null) ? null : tile.getLocation());
    }

    public Set<Point> getCityTerritory(City city) {
        Objects.requireNonNull(city, "city must be not null");
        return Collections.unmodifiableSet(cityTerritory.get(city));
    }

    public Set<Point> getCivilizationTerritory(Civilization civilization) {
        Objects.requireNonNull(civilization, "civilization must be not null");
        return Collections.unmodifiableSet(civilizationTerritory.get(civilization));
    }

    public Set<Point> getAllCivilizationsTerritory() {
        return territory.entrySet().stream()
            .filter(e -> Objects.nonNull(e.getValue()))
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }

    public void put(Collection<Point> locations, City city) {
        Objects.requireNonNull(locations, "location must be not null");
        locations.forEach(e -> put(e, city));
    }

    // city can be null
    public void put(Point location, City city) {
        Objects.requireNonNull(location, "location must be not null");
        territory.put(location, city);

        putToCityTerritory(location, city);

        Civilization civilization = (city == null) ? null : city.getCivilization();
        putToCivilizationTerritory(location, civilization);
    }

    private void putToCityTerritory(Point location, City city) {
        // remove from prev city's territory
        for (Set<Point> territory : cityTerritory.values()) {
            if (territory.remove(location)) {
                break;
            }
        }

        // add to curr city
        if (city != null) {
            Set<Point> territory = cityTerritory.computeIfAbsent(city, e -> new HashSet<>());
            territory.add(location);
        }
    }

    private void putToCivilizationTerritory(Point location, Civilization civilization) {
        // remove from prev civilization's territory
        for (Set<Point> territory : civilizationTerritory.values()) {
            if (territory.remove(location)) {
                break;
            }
        }

        // add to curr city
        if (civilization != null) {
            Set<Point> territory = civilizationTerritory.computeIfAbsent(civilization, e -> new HashSet<>());
            territory.add(location);
        }
    }
}
