package com.tsoft.civilization.unit.service.move;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;

import java.util.HashMap;
import java.util.Map;

public class PassCostTable {

    private final Map<Class<? extends AbstractTerrain>, PassCostList> table = new HashMap<>();

    public PassCostTable() { }

    public PassCostTable add(Class<? extends AbstractTerrain> terrain, PassCostList passCost) {
        table.put(terrain, passCost);
        return this;
    }

    public int getPassCost(Civilization civilization, AbstractTerrain tile) {
        PassCostList passCosts = table.get(tile.getClass());
        return passCosts.getPassCost(civilization);
    }
}
