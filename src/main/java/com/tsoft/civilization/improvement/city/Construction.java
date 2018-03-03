package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.improvement.CanBeBuilt;

public class Construction {
    // a building or an unit
    private CanBeBuilt object;

    private int productionCost;

    public Construction(CanBeBuilt object) {
        this.object = object;

        productionCost = object.getProductionCost();
    }

    public CanBeBuilt getObject() {
        return object;
    }

    public int getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(int productionCost) {
        this.productionCost = productionCost;
    }

    @Override
    public String toString() {
        return "Construction{" +
                "object=" + object +
                ", productionCost=" + productionCost +
                '}';
    }
}
