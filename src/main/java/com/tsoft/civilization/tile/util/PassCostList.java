package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.technology.Technology;

import java.util.ArrayList;

public class PassCostList extends ArrayList<PassCost> {
    public PassCostList() {
        super();
    }

    public PassCostList add(Technology technology, int passCost) {
        PassCost ps = new PassCost(passCost, technology);
        super.add(ps);
        return this;
    }
}
