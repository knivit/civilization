package com.tsoft.civilization.tile.util;

import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.civilization.Civilization;

import java.util.ArrayList;

public class PassCostList extends ArrayList<PassCost> {
    public static PassCostList of(Object ... vals) {
        PassCostList result = new PassCostList();
        for (int i = 0; i < vals.length; i += 2) {
            result.add((Technology)vals[i], (int)vals[i + 1]);
        }
        return result;
    }

    public PassCostList() {
        super();
    }

    private void add(Technology technology, int passCost) {
        PassCost ps = new PassCost(passCost, technology);
        super.add(ps);
    }

    public int getPassCost(Civilization civilization) {
        int passCost = get(0).getPassCost();
        if (size() == 1) {
            return passCost;
        }

        // check for technologies
        // the technologies must be sorted by year (i.e. needed turns)
        // so start from the latest technology available
        for (int i = size() - 1; i > 0; i --) {
            if (civilization.isResearched(get(i).getTechnology())) {
                return get(i).getPassCost();
            }
        }

        return passCost;
    }
}
