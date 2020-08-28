package com.tsoft.civilization.building;

import com.tsoft.civilization.building.util.BuildingType;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.web.view.building.WallsView;
import com.tsoft.civilization.world.economic.Supply;

import java.util.UUID;

public class Walls extends AbstractBuilding<WallsView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final WallsView VIEW = new WallsView();

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.BUILDING;
    }

    /**
     * Walls increase a city's Defense Strength making the city more difficult to capture.
     * Walls are quite useful for cities located along a civilization's frontier.
     */
    @Override
    public Supply getSupply(City city) {
        return new Supply().setGold(-1);
    }

    @Override
    public int getStrength() {
        return 40;
    }

    @Override
    public int getProductionCost() {
        return 200;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.MASONRY) && civilization.getYear().getEra() < Year.INDUSTRIAL_ERA;
    }

    @Override
    public int getGoldCost() {
        return 400;
    }

    @Override
    public WallsView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
