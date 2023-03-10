package com.tsoft.civilization.civilization.building.catalog.monument;

import com.tsoft.civilization.civilization.building.BuildingBaseState;
import com.tsoft.civilization.economic.Supply;

import static com.tsoft.civilization.civilization.building.BuildingCategory.BUILDING;

public class MonumentBaseState {

    public BuildingBaseState getBaseState() {
        return BuildingBaseState.builder()
                .category(BUILDING)
                .productionCost(40)
                .goldCost(280)
                .incomeSupply(Supply.builder().culture(2).build())
                .outcomeSupply(Supply.builder().gold(-1).build())
                .build();
    }
}
