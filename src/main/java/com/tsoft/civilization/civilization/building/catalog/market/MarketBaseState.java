package com.tsoft.civilization.civilization.building.catalog.market;

import com.tsoft.civilization.civilization.building.BuildingBaseState;
import com.tsoft.civilization.economic.Supply;

import static com.tsoft.civilization.civilization.building.BuildingCategory.BUILDING;

public class MarketBaseState {

    public BuildingBaseState getBaseState() {
        return BuildingBaseState.builder()
                .category(BUILDING)
                .productionCost(120)
                .goldCost(580)
                .incomeSupply(Supply.builder().food(2).build())
                .outcomeSupply(Supply.EMPTY)
                .build();
    }
}
