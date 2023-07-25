package com.tsoft.civilization.improvement.catalog.ancientruins;

import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.ImprovementBaseState;

public class AncientRuinsBaseState {

    public ImprovementBaseState getBaseState() {
        return ImprovementBaseState.builder()
            .supply(Supply.EMPTY)
            .build();
    }
}
