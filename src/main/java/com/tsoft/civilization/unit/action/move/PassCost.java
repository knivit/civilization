package com.tsoft.civilization.unit.action.move;

import com.tsoft.civilization.technology.Technology;

// Immutable
public class PassCost {

    public static int UNPASSABLE = Integer.MAX_VALUE;

    private final Technology technology;
    private final int passCost;

    public PassCost(Technology technology, int passCost) {
        this.technology = technology;
        this.passCost = passCost;
    }

    public int getPassCost() {
        return passCost;
    }

    public Technology getTechnology() {
        return technology;
    }
}
