package com.tsoft.civilization.tile;

import com.tsoft.civilization.civilization.Civilization;

import java.util.ArrayList;
import java.util.Arrays;

public class PassCostList extends ArrayList<PassCost> {

    public static PassCostList of(PassCost ... costs) {
        PassCostList result = new PassCostList();
        result.addAll(Arrays.asList(costs));
        return result;
    }

    public PassCostList() {
        super();
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
