package com.tsoft.civilization.improvement.city.supply;

import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.construction.Construction;

public class ConstructionSupplyService implements HasSupply {

    private final City city;

    public ConstructionSupplyService(City city) {
        this.city = city;
    }

    @Override
    public Supply calcIncomeSupply() {
        return Supply.EMPTY_SUPPLY;
    }

    @Override
    public Supply calcOutcomeSupply() {
        int productionCost = 0;

        for (Construction construction : city.getConstructions()) {
            int cost = construction.getProductionCost();
            if (cost <= 0) {
                continue;
            }

            productionCost += cost;
        }

        return Supply.builder().production(-productionCost).build();
    }
}
