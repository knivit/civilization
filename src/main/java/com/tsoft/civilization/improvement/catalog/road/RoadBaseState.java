package com.tsoft.civilization.improvement.catalog.road;

import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.ImprovementBaseState;

public class RoadBaseState {

    public ImprovementBaseState getBaseState() {
        return ImprovementBaseState.builder()
            .supply(Supply.builder().gold(-1).build())
            .build();
    }
}
