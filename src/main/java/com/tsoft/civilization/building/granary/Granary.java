package com.tsoft.civilization.building.granary;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingType;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.tile.luxury.Bananas;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.economic.Supply;

import java.util.UUID;

public class Granary extends AbstractBuilding {
    public static final Granary STUB = new Granary(null);

    public static final String CLASS_UUID = UUID.randomUUID().toString();
    private static final GranaryView VIEW = new GranaryView();

    public Granary(City city) {
        super(city);
    }

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
            AbstractTile<?> tile = getTile(location);
            if (Bananas.class.equals(tile.getLuxury().getClass())) {
                food ++;
            }
        }

        return Supply.builder().food(food).production(2).gold(-1).build();
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