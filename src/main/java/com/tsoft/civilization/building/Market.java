package com.tsoft.civilization.building;

import com.tsoft.civilization.building.util.BuildingType;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.web.view.building.MarketView;
import com.tsoft.civilization.world.economic.Supply;

import java.util.UUID;

public class Market extends AbstractBuilding<MarketView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final MarketView VIEW = new MarketView();

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.BUILDING;
    }

    /**
     * The Market significantly increases a city's output of gold.
     */
    @Override
    public Supply getSupply(City city) {
        Supply tileScore = city.getTilesSupply();
        int gold = tileScore.getGold();
        if (gold > 0) {
            gold = (int) Math.round(gold * 0.25);
            if (gold == 0) gold = 1;
        }

        return Supply.builder().gold(2 + gold).build();
    }

    @Override
    public int getStrength() {
        return 0;
    }

    @Override
    public int getProductionCost() {
        return 120;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.CURRENCY);
    }

    @Override
    public int getGoldCost() {
        return 580;
    }

    @Override
    public MarketView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}
