package com.tsoft.civilization.building.util;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.util.BuildingCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class BuildingList extends ArrayList<AbstractBuilding> implements BuildingCollection {
    public BuildingList() {
        super();
    }

    public BuildingList(BuildingCollection buildings) {
        super(buildings);
    }

    @Override
    public AbstractBuilding getBuildingById(String buildingId) {
        for (AbstractBuilding building : this) {
            if (building.getId().equals(buildingId)) {
                return building;
            }
        }
        return null;
    }

    @Override
    public AbstractBuilding findByClassUuid(String classUuid) {
        for (AbstractBuilding building : this) {
            if (building.getClassUuid().equals(classUuid)) {
                return building;
            }
        }
        return null;
    }

    @Override
    public void sortByName() {
        Collections.sort(this, new Comparator<AbstractBuilding>() {
            @Override
            public int compare(AbstractBuilding building1, AbstractBuilding building2) {
                return building1.getView().getLocalizedName().compareTo(building2.getView().getLocalizedName());
            }
        });
    }
}
