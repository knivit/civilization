package com.tsoft.civilization.civilization.city.building;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.building.BuildingFactory;
import com.tsoft.civilization.civilization.building.BuildingList;
import com.tsoft.civilization.civilization.building.catalog.palace.Palace;
import com.tsoft.civilization.civilization.building.catalog.settlement.Settlement;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.world.HasHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
public class CityBuildingService implements HasHistory {

    private final City city;
    private final BuildingList buildings = new BuildingList();

    public void addFirstBuilding(boolean isCapital) {
        if (isCapital) {
            AbstractBuilding palace = BuildingFactory.newInstance(Palace.CLASS_UUID, city);
            add(palace);
        } else {
            AbstractBuilding settlement = BuildingFactory.newInstance(Settlement.CLASS_UUID, city);
            add(settlement);
        }
    }

    public BuildingList getBuildings() {
        return buildings.unmodifiableList();
    }

    public AbstractBuilding getBuildingById(String buildingId) {
        return buildings.getBuildingById(buildingId);
    }

    public void add(AbstractBuilding building) {
        buildings.add(building);

        notifyDependentServices();
    }

    public void remove(AbstractBuilding building) {
        Objects.requireNonNull(building, "building can't be null");

        building.destroy();
        buildings.remove(building);

        city.getCivilization().addEvent(BuildingDestroyedEvent.builder()
            .buildingName(building.getView().getName())
            .build());

        notifyDependentServices();
    }

    public AbstractBuilding findByClassUuid(String classUuid) {
        return buildings.findByClassUuid(classUuid);
    }

    public boolean alreadyExists(String classUuid) {
        return findByClassUuid(classUuid) != null;
    }

    public BuildingList getAvailableBuildings() {
        BuildingList result = new BuildingList();

        for (AbstractBuilding building : BuildingFactory.getCatalogueInstances()) {
            if (alreadyExists(building.getClassUuid())) {
                continue;
            }

            if (building.isFirstBuilding()) {
                continue;
            }

            if (building.checkEraAndTechnology(city.getCivilization())) {
                result.add(building);
            }
        }

        return result;
    }

    @Override
    public void startYear() {
    }

    @Override
    public void stopYear() {
    }

    private void notifyDependentServices() {
        city.getHappinessService().recalculate();
    }
}
