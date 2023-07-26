package com.tsoft.civilization.unit.action.move;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.unit.AbstractUnit;

import java.util.Objects;

public final class TilePassCostTable {


    private TilePassCostTable() { }

    public static int get(Civilization civilization, AbstractUnit unit, AbstractTerrain tile) {
        Objects.requireNonNull(civilization, "Civilization can't be null");
        Objects.requireNonNull(unit, "Unit can't be null");
        Objects.requireNonNull(tile, "Tile can't be null");

        return unit.getPassScore();
    }
}
