package com.tsoft.civilization.civilization.building.catalog.settlement;

import com.tsoft.civilization.civilization.building.*;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.Year;

public class Settlement extends AbstractBuilding {

    public static final String CLASS_UUID = BuildingType.SETTLEMENT.name();

    private static final BuildingBaseState BASE_STATE = new SettlementBaseState().getBaseState();

    private static final AbstractBuildingView VIEW = new SettlementView();

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public BuildingBaseState getBaseState() {
        return BASE_STATE;
    }

    @Override
    public AbstractBuildingView getView() {
        return VIEW;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.getYear().getEra() == Year.ANCIENT_ERA;
    }

    @Override
    public boolean requiredEraAndTechnology(Civilization civilization) {
        return true;
    }
}
