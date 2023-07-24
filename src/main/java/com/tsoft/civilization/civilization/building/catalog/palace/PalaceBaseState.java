package com.tsoft.civilization.civilization.building.catalog.palace;

import com.tsoft.civilization.civilization.building.BuildingBaseState;
import com.tsoft.civilization.economic.Supply;

import static com.tsoft.civilization.civilization.building.BuildingCategory.NATIONAL_WONDER;

public class PalaceBaseState {

    public BuildingBaseState getBaseState() {
        return BuildingBaseState.builder()
            .category(NATIONAL_WONDER)
            .defenseStrength(25)
            .incomeSupply(Supply.builder().production(3).gold(3).science(3).culture(1).build())
            .build();
    }
}
