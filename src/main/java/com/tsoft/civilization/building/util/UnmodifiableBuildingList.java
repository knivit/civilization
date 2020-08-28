package com.tsoft.civilization.building.util;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.util.UnmodifiableList;

import java.util.List;

public class UnmodifiableBuildingList extends UnmodifiableList<AbstractBuilding<?>> implements BuildingCollection {
    private BuildingCollection buildings;

    public UnmodifiableBuildingList(BuildingCollection buildings) {
        this.buildings = buildings;
    }

    @Override
    protected List<AbstractBuilding<?>> getList() {
        return buildings;
    }

    @Override
    public AbstractBuilding<?> getBuildingById(String buildingId) {
        return buildings.getBuildingById(buildingId);
    }

    @Override
    public AbstractBuilding<?> findByClassUuid(String classUuid) {
        return buildings.findByClassUuid(classUuid);
    }

    @Override
    public void sortByName() {
        buildings.sortByName();
    }
}
