package com.tsoft.civilization.civilization.building;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BuildingList implements Iterable<AbstractBuilding> {

    private final List<AbstractBuilding> buildings = new ArrayList<>();
    private boolean isUnmodifiable;

    public BuildingList() { }

    public BuildingList(List<AbstractBuilding> buildings) {
        Objects.requireNonNull(buildings);
        this.buildings.addAll(buildings);
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

    public Stream<AbstractBuilding> stream() {
        return buildings.stream();
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

    public BuildingList clear() {
        checkIsUnmodifiable();
        buildings.clear();
        return this;
    }

    public BuildingList add(AbstractBuilding building) {
        Objects.requireNonNull(building, "building can't be null");

        checkIsUnmodifiable();
        buildings.add(building);
        return this;
    }

    public BuildingList addAll(BuildingList list) {
        checkIsUnmodifiable();

        buildings.addAll(list.buildings);
        return this;
    }

    public BuildingList remove(AbstractBuilding building) {
        Objects.requireNonNull(building, "building can't be null");

        checkIsUnmodifiable();
        buildings.remove(building);
        return this;
    }

    public AbstractBuilding getBuildingById(String buildingId) {
        Objects.requireNonNull(buildingId, "buildingId can't be null");
        return buildings.stream()
            .filter(e -> e.getId().equals(buildingId))
            .findFirst()
            .orElse(null);
    }

    public AbstractBuilding findByClassUuid(String classUuid) {
        Objects.requireNonNull(classUuid, "classUuid can't be null");
        return buildings.stream()
            .filter(e -> e.getClassUuid().equals(classUuid))
            .findFirst()
            .orElse(null);
    }

    public BuildingList sortByName() {
        return new BuildingList(stream()
            .sorted(Comparator.comparing(a -> a.getView().getLocalizedName()))
            .collect(Collectors.toList()));
    }
}
