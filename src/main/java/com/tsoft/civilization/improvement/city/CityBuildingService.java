package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.util.BuildingCollection;
import com.tsoft.civilization.building.util.BuildingList;
import com.tsoft.civilization.building.Palace;
import com.tsoft.civilization.building.Settlement;
import com.tsoft.civilization.building.util.UnmodifiableBuildingList;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.world.economic.Supply;

public class CityBuildingService {
    private City city;

    private BuildingList buildings = new BuildingList();
    private UnmodifiableBuildingList unmodifiableBuildings = new UnmodifiableBuildingList(buildings);
    private BuildingList destroyedBuildings = new BuildingList();

    // Current construction (building, unit) or null
    private Construction construction;

    public CityBuildingService(City city) {
        this.city = city;

        if (city.getCivilization().getCities().size() == 1) {
            AbstractBuilding palace = AbstractBuilding.newInstance(Palace.CLASS_UUID, city);
            add(palace);
        } else {
            AbstractBuilding settlement = AbstractBuilding.newInstance(Settlement.CLASS_UUID, city);
            add(settlement);
        }
    }

    public BuildingCollection getBuildings() {
        return unmodifiableBuildings;
    }

    public AbstractBuilding getBuildingById(String buildingId) {
        return buildings.getBuildingById(buildingId);
    }

    public void add(AbstractBuilding building) {
        buildings.add(building);
    }

    public void remove(AbstractBuilding building) {
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
        Supply supply = new Supply();
        for (AbstractBuilding building : buildings) {
            supply.add(building.getSupply(city));
        }
        return supply;
    }

    // Buildings and units construction
    public void step(Supply citySupply) {
        destroyedBuildings.clear();

        if (construction == null) {
            return;
        }

        int productionCost = construction.getProductionCost();
        int cityProduction = citySupply.getProduction();
        if (productionCost <= cityProduction) {
            Supply constructionExpenses = new Supply().setProduction(-productionCost);
            citySupply.add(constructionExpenses);

            // construction ended, the building is built
            city.constructionDone(construction);
            construction = null;
        } else {
            construction.setProductionCost(productionCost - cityProduction);

            Supply constructionExpenses = new Supply().setProduction(-cityProduction);
            citySupply.add(constructionExpenses);
        }
    }
}
