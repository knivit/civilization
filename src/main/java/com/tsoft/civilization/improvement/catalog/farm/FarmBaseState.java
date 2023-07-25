package com.tsoft.civilization.improvement.catalog.farm.farm;

import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.ImprovementBaseState;

public class FarmBaseState {

    public ImprovementBaseState getBaseState() {
        return ImprovementBaseState.builder()
            .supply(Supply.builder().food(2).build())
            .build();
    }
}
