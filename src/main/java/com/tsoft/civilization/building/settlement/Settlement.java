package com.tsoft.civilization.building.settlement;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingType;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

public class Settlement extends AbstractBuilding {
    public static final Settlement STUB = new Settlement(null);

    public static final String CLASS_UUID = UUID.randomUUID().toString();
    private static final SettlementView VIEW = new SettlementView();

    @Getter
    private Supply supply = Supply.EMPTY_SUPPLY;

    public Settlement(City city) {
        super(city);
    }

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.BUILDING;
    }

    @Override
    public Supply calcSupply() {
        return Supply.builder().production(1).gold(1).culture(1).build();
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {
        supply = calcSupply();
    }

    @Override
    public int getDefenseStrength() {
        return 10;
    }

    @Override
    public int getProductionCost() {
        return -1;
    }

    @Override
    public boolean requiresEraAndTechnology(Civilization civilization) {
        return true;
    }

    @Override
    public int getGoldCost() {
        return -1;
    }

    @Override
    public SettlementView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
