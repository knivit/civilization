package com.tsoft.civilization.building.util;

import com.tsoft.civilization.building.AbstractBuilding;

import java.util.List;

public interface BuildingCollection extends List<AbstractBuilding> {
    public AbstractBuilding getBuildingById(String buildingId);

    public AbstractBuilding findByClassUuid(String classUuid);

    public void sortByName();
}
