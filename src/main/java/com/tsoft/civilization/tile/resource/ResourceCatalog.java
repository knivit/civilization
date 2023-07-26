package com.tsoft.civilization.tile.resource;

import com.tsoft.civilization.economic.Supply;

import java.util.HashMap;
import java.util.Map;

import static com.tsoft.civilization.tile.resource.ResourceType.*;

public final class ResourceCatalog {

    private static final Map<ResourceType, ResourceBaseState> RESOURCES = new HashMap<>() {{

        /** Luxury */

        put(COTTON, ResourceBaseState.builder()
            .supply(Supply.builder().gold(2).build())
            .build());

        put(SALT, ResourceBaseState.builder()
            .supply(Supply.builder().food(1).gold(1).build())
            .build());

        put(SILK, ResourceBaseState.builder()
            .supply(Supply.builder().gold(2).build())
            .build());

        put(SPICES, ResourceBaseState.builder()
            .supply(Supply.builder().gold(2).build())
            .build());

        put(SUGAR, ResourceBaseState.builder()
            .supply(Supply.builder().gold(2).build())
            .build());

        put(WHALES, ResourceBaseState.builder()
            .supply(Supply.builder().food(1).gold(1).build())
            .build());

        put(WINE, ResourceBaseState.builder()
            .supply(Supply.builder().gold(2).build())
            .build());

        /** Strategic */

        put(ALUMINIUM, ResourceBaseState.builder()
            .supply(Supply.EMPTY)
            .build());

        put(COAL, ResourceBaseState.builder()
            .supply(Supply.EMPTY)
            .build());

        put(HORSES, ResourceBaseState.builder()
            .supply(Supply.builder().production(1).build())
            .build());

        put(IRON, ResourceBaseState.builder()
            .supply(Supply.EMPTY)
            .build());

        put(OIL, ResourceBaseState.builder()
            .supply(Supply.EMPTY)
            .build());

        put(URANIUM, ResourceBaseState.builder()
            .supply(Supply.EMPTY)
            .build());
    }};

    private ResourceCatalog() { }

    public static ResourceBaseState getBaseState(ResourceType type) {
        return RESOURCES.get(type);
    }
}
