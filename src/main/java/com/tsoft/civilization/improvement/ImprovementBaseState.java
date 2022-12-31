package com.tsoft.civilization.improvement;

import com.tsoft.civilization.economic.Supply;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImprovementBaseState {

    private final Supply supply;
    private final boolean isBlockingTileSupply;
}
