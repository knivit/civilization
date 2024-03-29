package com.tsoft.civilization.civilization.city.construction;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.AbstractView;
import com.tsoft.civilization.world.HasId;
import com.tsoft.civilization.world.HasView;

public class Construction implements HasView, HasId {

    private final CanBeBuilt object;            // constructing a building, or training a unit, or working on a particular project
    private final double totalProductionCost;   // total production cost
    private double usedProductionCost;          // used production cost

    public Construction(Civilization civilization, CanBeBuilt object) {
        this.object = object;

        totalProductionCost = object.getBaseProductionCost(civilization);
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

    public double getProductionCost() {
        return Math.max(0, totalProductionCost - usedProductionCost);
    }

    public void useProductionCost(double cost) {
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
