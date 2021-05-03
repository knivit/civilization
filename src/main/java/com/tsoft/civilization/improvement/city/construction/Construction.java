package com.tsoft.civilization.improvement.city.construction;

import com.tsoft.civilization.common.AbstractView;
import com.tsoft.civilization.common.HasId;
import com.tsoft.civilization.common.HasView;

public class Construction implements HasView, HasId {

    private final CanBeBuilt object;       // constructing a building, or training a unit, or working on a particular project
    private final int totalProductionCost; // total production cost
    private int usedProductionCost;        // used production cost

    public Construction(CanBeBuilt object) {
        this.object = object;

        totalProductionCost = object.getProductionCost();
        usedProductionCost = 0;
    }

    public CanBeBuilt getObject() {
        return object;
    }

    @Override
    public AbstractView getView() {
        return object.getView();
    }

    @Override
    public String getId() {
        return object.getId();
    }

    public int getProductionCost() {
        return Math.max(0, totalProductionCost - usedProductionCost);
    }

    public void useProductionCost(int cost) {
        this.usedProductionCost += cost;
    }

    @Override
    public String toString() {
        return "Construction{" +
                "object=" + object +
                ", totalProductionCost=" + totalProductionCost +
                ", usedProductionCost=" + usedProductionCost +
        '}';
    }
}
