package com.tsoft.civilization.building;

import com.tsoft.civilization.building.AbstractBuilding;

import java.util.List;

public interface BuildingCollection extends List<AbstractBuilding<?>> {
    AbstractBuilding<?> getBuildingById(String buildingId);

    AbstractBuilding<?> findByClassUuid(String classUuid);

    void sortByName();
}
