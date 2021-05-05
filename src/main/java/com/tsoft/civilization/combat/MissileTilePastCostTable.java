package com.tsoft.civilization.combat;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.tile.AbstractTile;
import com.tsoft.civilization.tile.tile.grassland.Grassland;
import com.tsoft.civilization.tile.tile.lake.Lake;
import com.tsoft.civilization.tile.tile.ocean.Ocean;
import com.tsoft.civilization.tile.tile.plain.Plain;
import com.tsoft.civilization.tile.tile.snow.Snow;
import com.tsoft.civilization.tile.tile.tundra.Tundra;
import com.tsoft.civilization.tile.feature.coast.Coast;
import com.tsoft.civilization.tile.tile.desert.Desert;
import com.tsoft.civilization.tile.feature.ice.Ice;
import com.tsoft.civilization.tile.feature.mountain.Mountain;
import com.tsoft.civilization.unit.military.archers.Archers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.tsoft.civilization.unit.move.TilePassCostTable.UNPASSABLE;

public final class MissileTilePastCostTable {

    private MissileTilePastCostTable() { }

    private static Map<String, Integer> table = new HashMap<>();

    static {
        table.put(Archers.CLASS_UUID + Coast.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Desert.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Grassland.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Ice.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Lake.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Mountain.CLASS_UUID, UNPASSABLE);
        table.put(Archers.CLASS_UUID + Ocean.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Plain.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Snow.CLASS_UUID, 1);
        table.put(Archers.CLASS_UUID + Tundra.CLASS_UUID, 1);

        table.put(City.CLASS_UUID + Coast.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Desert.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Grassland.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Ice.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Lake.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Mountain.CLASS_UUID, UNPASSABLE);
        table.put(City.CLASS_UUID + Ocean.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Plain.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Snow.CLASS_UUID, 1);
        table.put(City.CLASS_UUID + Tundra.CLASS_UUID, 1);
    }

    public static int get(HasCombatStrength attacker, AbstractTile tile) {
        Objects.requireNonNull(attacker, "Attacker can't be null");
        Objects.requireNonNull(tile, "Tile can't be null");

        String key = attacker.getClassUuid() + tile.getClassUuid();
        Integer passCost = table.get(key);

        return (passCost == null) ? UNPASSABLE : passCost;
    }
}
