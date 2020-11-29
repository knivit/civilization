package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.improvement.CanBeBuilt;

public class Construction <T extends CanBeBuilt> {
    // a building or an unit
    private final T object;

    private int productionCost;

    public Construction(T object) {
        this.object = object;

        productionCost = object.getProductionCost();
    }

    public T getObject() {
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
