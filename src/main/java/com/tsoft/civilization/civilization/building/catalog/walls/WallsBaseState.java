package com.tsoft.civilization.civilization.building.catalog.walls;

import com.tsoft.civilization.civilization.building.BuildingBaseState;
import com.tsoft.civilization.economic.Supply;

import static com.tsoft.civilization.civilization.building.BuildingCategory.BUILDING;

public class WallsBaseState {

    public BuildingBaseState getBaseState() {
        return BuildingBaseState.builder()
                .category(BUILDING)
                .productionCost(200)
                .goldCost(400)
                .defenseStrength(40)
                .outcomeSupply(Supply.builder().gold(-1).build())
                .build();
    }
}
