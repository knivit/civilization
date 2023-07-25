package com.tsoft.civilization.improvement.catalog.mine;

import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.ImprovementBaseState;

public class MineBaseState {

    public ImprovementBaseState getBaseState() {
        return ImprovementBaseState.builder()
            .supply(Supply.builder().production(1).build())
            .build();
    }
}
