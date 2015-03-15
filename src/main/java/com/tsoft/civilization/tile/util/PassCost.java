package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.technology.Technology;

public class PassCost {
    private int passCost;
    private Technology technology;

    public PassCost(int passCost, Technology technology) {
        this.passCost = passCost;
        this.technology = technology;
    }

    public int getPassCost() {
        return passCost;
    }

    public Technology getTechnology() {
        return technology;
    }
}
