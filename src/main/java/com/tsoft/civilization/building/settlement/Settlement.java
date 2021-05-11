package com.tsoft.civilization.building.settlement;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingType;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.world.Year;
import lombok.Getter;

import java.util.UUID;

public class Settlement extends AbstractBuilding {

    public static final String CLASS_UUID = UUID.randomUUID().toString();
    private static final SettlementView VIEW = new SettlementView();

    @Getter
    private final int baseProductionCost = -1;

    @Getter
    private final int cityDefenseStrength = 10;

    @Getter
    private final int localHappiness = 0;

    @Getter
    private final int globalHappiness = 0;

    public Settlement(City city) {
        super(city);
    }

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.BUILDING;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }

    @Override
    public Supply calcIncomeSupply() {
        return Supply.builder().production(1).gold(1).culture(1).build();
    }

    @Override
    public Supply calcOutcomeSupply() {
        return Supply.EMPTY;
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

    }

    @Override
    public boolean requiresEraAndTechnology(Civilization civilization) {
        return true;
    }

    @Override
    public int getGoldCost(Civilization civilization) {
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
