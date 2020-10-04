package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingFactory;
import com.tsoft.civilization.building.BuildingList;
import com.tsoft.civilization.building.palace.Palace;
import com.tsoft.civilization.building.settlement.Settlement;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.world.economic.Supply;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class CityBuildingService {
    private final City city;

    private final BuildingList buildings = new BuildingList();
    private BuildingList destroyedBuildings = new BuildingList();

    // Current construction (building, unit) or null
    private Construction construction;

    public CityBuildingService(City city) {
        this.city = city;
    }

    public void addFirstBuilding(boolean isCapital) {
        if (isCapital) {
            AbstractBuilding palace = BuildingFactory.newInstance(Palace.CLASS_UUID, city);
            add(palace); // A capital
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
    }

    public void remove(AbstractBuilding building) {
        Objects.requireNonNull(building, "building can't be null");

        destroyedBuildings.add(building);
        buildings.remove(building);
    }

    public AbstractBuilding findByClassUuid(String classUuid) {
        return buildings.findByClassUuid(classUuid);
    }

    /** Can we start ANY construction NOW, or not ? */
    public boolean canStartConstruction() {
        return (city.getPassScore() > 0) && (construction == null);
    }

    public Construction getConstruction() {
        return construction;
    }

    public void startConstruction(CanBeBuilt object) {
        construction = new Construction(object);
    }

    public Supply getSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;
        for (AbstractBuilding building : buildings) {
            supply = supply.add(building.getSupply(city));
        }
        return supply;
    }

    public void startYear() {
        destroyedBuildings = new BuildingList();
    }

    // Buildings and units construction
    public Supply move(Supply citySupply) {
        if (construction == null) {
            log.debug("No construction is in progress");
            return citySupply;
        }

        int productionCost = construction.getProductionCost();
        int cityProduction = citySupply.getProduction();
        if (productionCost <= cityProduction) {
            Supply constructionExpenses = Supply.builder().production(-productionCost).build();
            citySupply = citySupply.add(constructionExpenses);

            // construction ended, the building is built
            city.constructionDone(construction);
            construction = null;
        } else {
            construction.setProductionCost(productionCost - cityProduction);

            Supply constructionExpenses = Supply.builder().production(-cityProduction).build();
            citySupply = citySupply.add(constructionExpenses);
        }

        return citySupply;
    }

    public void stopYear() {

    }
}
