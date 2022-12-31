package com.tsoft.civilization.improvement;

import com.tsoft.civilization.economic.Supply;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.improvement.ImprovementType.*;

public class ImprovementCatalog {

    private static final Map<ImprovementType, ImprovementBaseState> IMPROVEMENTS = new HashMap<>() {{
        put(ANCIENT_RUINS, ImprovementBaseState.builder()
            .supply(Supply.EMPTY)
            .build());

        put(FARM, ImprovementBaseState.builder()
            .supply(Supply.builder().food(2).build())
            .build());

        put(MINE, ImprovementBaseState.builder()
            .supply(Supply.builder().production(1).build())
            .build());

        put(ROAD, ImprovementBaseState.builder()
            .supply(Supply.builder().gold(-1).build())
            .build());
    }};

    private ImprovementCatalog() { }

    public static ImprovementBaseState getBaseState(ImprovementType type) {
        return IMPROVEMENTS.get(type);
    }
}
