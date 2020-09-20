package com.tsoft.civilization.building;

import java.util.*;

public class BuildingList implements Iterable<AbstractBuilding> {
    private List<AbstractBuilding> buildings = new ArrayList<>();
    private boolean isUnmodifiable;

    public BuildingList() { }

    public BuildingList(List<AbstractBuilding> buildings) {
        Objects.requireNonNull(buildings);
        this.buildings = buildings;
    }

    public BuildingList(AbstractBuilding ... buildings) {
        if (buildings != null) {
            this.buildings.addAll(Arrays.asList(buildings));
        }
        isUnmodifiable = true;
    }

    public List<AbstractBuilding> getList() {
        return new ArrayList<>(buildings);
    }

    public BuildingList unmodifiableList() {
        BuildingList list = new BuildingList();
        list.buildings.addAll(buildings);
        list.isUnmodifiable = true;
        return list;
    }

    @Override
    public Iterator<AbstractBuilding> iterator() {
        return buildings.iterator();
    }

    private void checkIsUnmodifiable() {
        if (isUnmodifiable) {
            throw new UnsupportedOperationException("The list is unmodifiable");
        }
    }

    public boolean isEmpty() {
        return buildings.isEmpty();
    }

    public int size() {
        return buildings.size();
    }

    public BuildingList add(AbstractBuilding building) {
        checkIsUnmodifiable();
        buildings.add(building);
        return this;
    }

    public BuildingList remove(AbstractBuilding building) {
        checkIsUnmodifiable();
        buildings.remove(building);
        return this;
    }

    public AbstractBuilding getBuildingById(String buildingId) {
        for (AbstractBuilding building : buildings) {
            if (building.getId().equals(buildingId)) {
                return building;
            }
        }
        return null;
    }

    public AbstractBuilding findByClassUuid(String classUuid) {
        for (AbstractBuilding building : buildings) {
            if (building.getClassUuid().equals(classUuid)) {
                return building;
            }
        }
        return null;
    }
}
