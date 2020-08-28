package com.tsoft.civilization.building.util;

import com.tsoft.civilization.building.*;

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

    private BuildingCatalog() {
    }

    public static AbstractBuilding<?> findByClassUuid(String classUuid) {
        return buildingsCatalog.findByClassUuid(classUuid);
    }

    public static BuildingCollection values() {
        return unmodifiableBuildingsCatalog;
    }
}
