package com.tsoft.civilization.combat;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.terrain.grassland.Grassland;
import com.tsoft.civilization.tile.terrain.lake.Lake;
import com.tsoft.civilization.tile.terrain.ocean.Ocean;
import com.tsoft.civilization.tile.terrain.plains.Plains;
import com.tsoft.civilization.tile.terrain.snow.Snow;
import com.tsoft.civilization.tile.terrain.tundra.Tundra;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.terrain.desert.Desert;
import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.unit.military.archers.Archers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.tsoft.civilization.unit.service.move.TilePassCostTable.UNPASSABLE;

public final class MissileTilePastCostTable {

    private MissileTilePastCostTable() { }

    private static final Map<String, Integer> table = new HashMap<>();

    static {
        table.put(Archers.CLASS_UUID + Coast.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Desert.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Grassland.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Ice.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Lake.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Mountain.CLASS_UUID, UNPASSABLE);
        table.put(Archers.CLASS_UUID + Ocean.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Plains.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Snow.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Tundra.CLASS_UUID, 1);

        table.put(City.CLASS_UUID + Coast.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Desert.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Grassland.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Ice.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Lake.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Mountain.CLASS_UUID, UNPASSABLE);
        table.put(City.CLASS_UUID + Ocean.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Plains.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Snow.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Tundra.CLASS_UUID, 1);
    }

    public static int get(HasCombatStrength attacker, AbstractTerrain tile) {
        Objects.requireNonNull(attacker, "Attacker can't be null");
        Objects.requireNonNull(tile, "Tile can't be null");

        String key = attacker.getClassUuid() + tile.getClassUuid();
        Integer passCost = table.get(key);

        return (passCost == null) ? UNPASSABLE : passCost;
    }
}
