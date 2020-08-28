package com.tsoft.civilization.building;

import com.tsoft.civilization.building.util.BuildingType;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.web.view.building.SettlementView;
import com.tsoft.civilization.world.economic.Supply;

import java.util.UUID;

public class Settlement extends AbstractBuilding<SettlementView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final SettlementView VIEW = new SettlementView();

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.BUILDING;
    }

    @Override
    public Supply getSupply(City city) {
        return Supply.builder().production(1).gold(1).culture(1).build();
    }

    @Override
    public int getStrength() {
        return 10;
    }

    @Override
    public int getProductionCost() {
        return -1;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
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
