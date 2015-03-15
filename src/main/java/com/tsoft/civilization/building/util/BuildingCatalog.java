package com.tsoft.civilization.building.util;

import com.tsoft.civilization.building.Granary;
import com.tsoft.civilization.building.Market;
import com.tsoft.civilization.building.Monument;
import com.tsoft.civilization.building.Palace;
import com.tsoft.civilization.building.Settlement;
import com.tsoft.civilization.building.Walls;

public class BuildingCatalog {
    private static final BuildingCollection buildingsCatalog = new BuildingList();
    private static final BuildingCollection unmodifiableBuildingsCatalog = new UnmodifiableBuildingList(buildingsCatalog);

    // Read-only objects, this map is to use as a catalog only
    static {
        buildingsCatalog.add(new Granary());
        buildingsCatalog.add(new Market());
        buildingsCatalog.add(new Monument());
        buildingsCatalog.add(new Palace());
        buildingsCatalog.add(new Settlement());
        buildingsCatalog.add(new Walls());
    }

    private BuildingCatalog() { }

    public static BuildingCollection values() {
        return unmodifiableBuildingsCatalog;
    }
}
