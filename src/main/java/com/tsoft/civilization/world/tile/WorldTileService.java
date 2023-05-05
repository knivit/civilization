package com.tsoft.civilization.world.tile;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.World;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class WorldTileService {

    private final World world;

    public Civilization getCivilizationOnTile(Point location) {
        for (Civilization civilization : world.getCivilizations()) {
            if (civilization.getTileService().isOnTerritory(location)) {
                return civilization;
            }
        }
        return null;
    }

    public Set<Point> getAllCivilizationsTerritory() {
        return world.getCivilizations().stream()
            .map(e -> e.getTileService().getTerritory())
            .collect(HashSet::new, Set::addAll, Set::addAll);
    }
}
