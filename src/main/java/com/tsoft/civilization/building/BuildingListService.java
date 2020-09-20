package com.tsoft.civilization.building;

import java.util.Comparator;
import java.util.List;

public class BuildingListService {

    public BuildingList sortByName(BuildingList buildings) {
        List<AbstractBuilding> list = buildings.getList();
        list.sort(Comparator.comparing(e -> e.getView().getLocalizedName()));
        return new BuildingList(list);
    }
}
