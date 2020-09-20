package com.tsoft.civilization.building;

import com.tsoft.civilization.building.granary.Granary;
import com.tsoft.civilization.building.market.Market;
import com.tsoft.civilization.building.monument.Monument;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.building.settlement.Settlement;
import com.tsoft.civilization.building.walls.Walls;

public class BuildingCatalog {
    private static BuildingList catalog;

    // Read-only objects, this map is to use as a catalog only
    static {
        BuildingList buildings = new BuildingList(
            Granary.STUB,
            Market.STUB,
            Monument.STUB,
            Palace.STUB,
            Settlement.STUB,
            Walls.STUB
        );

        catalog = buildings.unmodifiableList();
    }

    private BuildingCatalog() {
    }

    public static AbstractBuilding findByClassUuid(String classUuid) {
        return catalog.findByClassUuid(classUuid);
    }

    public static BuildingList values() {
        return catalog;
    }
}
