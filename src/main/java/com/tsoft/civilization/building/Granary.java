package com.tsoft.civilization.building;

import com.tsoft.civilization.building.util.BuildingType;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.world.Civilization;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.luxury.Bananas;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.view.building.GranaryView;
import com.tsoft.civilization.world.economic.Supply;

import java.util.UUID;

public class Granary extends AbstractBuilding<GranaryView> {
    public static final String CLASS_UUID = UUID.randomUUID().toString();

    private static final GranaryView VIEW = new GranaryView();

    @Override
    public BuildingType getBuildingType() {
        return BuildingType.BUILDING;
    }

    /**
     * Each source of Wheat Bananas and Deer worked by this City produce +1 Food.
     */
    @Override
    public Supply getSupply(City city) {
        int food = 0;
        for (Point location : getCity().getCitizenLocations()) {
            AbstractTile tile = getTile(location);
            if (Bananas.class.equals(tile.getLuxury().getClass())) {
                food ++;
            }
        }

        Supply supply = new Supply().setFood(food).setProduction(2).setGold(-1);
        return supply;
    }

    @Override
    public int getStrength() {
        return 0;
    }

    @Override
    public int getProductionCost() {
        return 60;
    }

    @Override
    public boolean checkEraAndTechnology(Civilization civilization) {
        return civilization.isResearched(Technology.POTTERY);
    }

    @Override
    public int getGoldCost() {
        return 340;
    }

    @Override
    public GranaryView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }
}