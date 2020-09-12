package com.tsoft.civilization.building.palace;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingType;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.world.economic.Supply;

import java.util.UUID;

public class Palace extends AbstractBuilding<PalaceView> {
    public static final Palace STUB = new Palace(null);

    public static final String CLASS_UUID = UUID.randomUUID().toString();
    private static final PalaceView VIEW = new PalaceView();

    public Palace(City city) {
        super(city);
    }

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.BUILDING;
    }

    /**
     * Indicates this City is the Capital of the empire.
     * Connecting other Cities to the Capital by Road will produce additional Gold.
     */
    @Override
    public Supply getSupply(City city) {
        return Supply.builder().production(3).gold(3).science(3).culture(1).build();
    }

    @Override
    public int getStrength() {
        return 25;
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
    public PalaceView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
