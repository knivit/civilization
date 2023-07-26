package com.tsoft.civilization.civilization.city.supply;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.civilization.city.City;

public class BuildingSupplyService {

    private final City city;

    public BuildingSupplyService(City city) {
        this.city = city;
    }

    public Supply getBaseSupply(Civilization civilization) {
        return Supply.EMPTY;
    }

    public Supply calcIncomeSupply(Civilization civilization) {
        Supply supply = Supply.EMPTY;
        for (AbstractBuilding building : city.getBuildings()) {
            supply = supply.add(building.calcIncomeSupply(civilization));
        }
        return supply;
    }

    public Supply calcOutcomeSupply(Civilization civilization) {
        Supply supply = Supply.EMPTY;
        for (AbstractBuilding building : city.getBuildings()) {
            supply = supply.add(building.calcOutcomeSupply(civilization));
        }
        return supply;
    }
}
