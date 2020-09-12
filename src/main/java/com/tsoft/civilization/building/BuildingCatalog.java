package com.tsoft.civilization.building;

import com.tsoft.civilization.building.granary.Granary;
import com.tsoft.civilization.building.market.Market;
import com.tsoft.civilization.building.monument.Monument;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.building.settlement.Settlement;
import com.tsoft.civilization.building.walls.Walls;

public class BuildingCatalog {
    private static final BuildingCollection buildingsCatalog = new BuildingList();
    private static final BuildingCollection unmodifiableBuildingsCatalog = new UnmodifiableBuildingList(buildingsCatalog);

    // Read-only objects, this map is to use as a catalog only
    static {
        buildingsCatalog.add(Granary.STUB);
        buildingsCatalog.add(Market.STUB);
        buildingsCatalog.add(Monument.STUB);
        buildingsCatalog.add(Palace.STUB);
        buildingsCatalog.add(Settlement.STUB);
        buildingsCatalog.add(Walls.STUB);
    }

    private BuildingCatalog() {
    }

    public static AbstractBuilding<?> findByClassUuid(String classUuid) {
        return buildingsCatalog.findByClassUuid(classUuid);
    }

    public static BuildingCollection values() {
        return unmodifiableBuildingsCatalog;
    }
}
