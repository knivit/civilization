package com.tsoft.civilization.civilization.tile;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CivilizationSettlerService {

    private final Civilization civilization;

    // Find a location to place a Settlers
    public Point getStartLocation() {
        List<Point> possibleLocations = getTilesToStartCivilization();
        return getCivilizationStartLocation(possibleLocations);
    }

    private List<Point> getTilesToStartCivilization() {
        return civilization.getWorld().getTilesMap().tiles()
            .filter(AbstractTerrain::isCanBuildCity)
            .map(AbstractTerrain::getLocation)
            .collect(Collectors.toList());
    }

    // Find a location to place a Settlers
    // Rules:
    //   - not less than 4 tiles from other civilizations tiles
    //   - location must be passable for the Settlers
    //   - there are must be 3 tiles of Earth around the location
    private Point getCivilizationStartLocation(List<Point> possibleLocations) {
        World world = civilization.getWorld();
        Set<Point> busyLocations = world.getTileService().getAllCivilizationsTerritory();

        // exclude units and locations around them as far as 4 tiles
        for (Civilization civilization : world.getCivilizations()) {
            for (AbstractUnit unit : civilization.getUnitService().getUnits()) {
                busyLocations.add(unit.getLocation());
                busyLocations.addAll(world.getLocationsAround(unit.getLocation(), 4));
            }
        }

        possibleLocations.removeAll(busyLocations);
        if (possibleLocations.isEmpty()) {
            return null;
        }

        // must be 3 tiles of Earth around
        possibleLocations = getLocationsWithEarthAround(possibleLocations, 3);
        if (possibleLocations.isEmpty()) {
            return null;
        }

        int n = ThreadLocalRandom.current().nextInt(possibleLocations.size());
        return possibleLocations.get(n);
    }

    private List<Point> getLocationsWithEarthAround(List<Point> possibleLocations, int radius) {
        World world = civilization.getWorld();

        int numberOfLocationsAround = world.getLocationsAround(possibleLocations.get(0), radius).size();
        int minRadiusWithEarthAround = numberOfLocationsAround / 2;

        Set<Point> busyLocations = new HashSet<>();
        for (Point location : possibleLocations) {
            List<AbstractTerrain> tilesAround = world.getLocationsAround(location, radius).stream()
                .map(e -> world.getTilesMap().getTile(e))
                .filter(AbstractTerrain::isCanBuildCity)
                .collect(Collectors.toList());

            if (tilesAround.size() < minRadiusWithEarthAround) {
                busyLocations.add(location);
            }
        }

        possibleLocations.removeAll(busyLocations);
        return possibleLocations;
    }
}
