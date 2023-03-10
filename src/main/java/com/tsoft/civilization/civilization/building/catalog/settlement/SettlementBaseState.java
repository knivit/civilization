package com.tsoft.civilization.civilization.building.catalog.settlement;

import com.tsoft.civilization.civilization.building.BuildingBaseState;
import com.tsoft.civilization.economic.Supply;

import static com.tsoft.civilization.civilization.building.BuildingCategory.NATIONAL_WONDER;

public class SettlementBaseState {

    public BuildingBaseState getBaseState() {
        return BuildingBaseState.builder()
                .category(NATIONAL_WONDER)
                .defenseStrength(10)
                .incomeSupply(Supply.builder().production(1).gold(1).culture(1).build())
                .build();
    }
}
