package com.tsoft.civilization.building.walls;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingType;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Walls
 *
 * Building of the Ancient Era
 * Cost
 * Production	Maintenance
 * 75 Production	0 Gold
 * Required Technology: Masonry
 * Effects
 *   +5 Melee strength
 *  +50 HP
 *
 * Game Info
 * Basic defensive building of the Ancient Era.
 *
 * Strategy
 * Walls increase a city's HP and Combat Strength, making the city more difficult to capture.
 * Walls are quite useful for cities located along a civilization's frontier.
 *
 * Civilopedia entry
 * Ever since people have gathered together in groups - towns, cities, whatever - other people have wanted
 * to steal their stuff. From earliest history people have constructed defensive works of mud, wood or stone
 * to keep their enemies away from their food, wealth, and women and children.
 * By medieval times walled villages and cities dotted the European countryside, some accompanied
 * by elaborate moats and ditches. Some of these elaborate works are still visible today.
 * The city of York, England, has extensive walls which date back to Roman times.
 */
public class Walls extends AbstractBuilding {

    public static final String CLASS_UUID = UUID.randomUUID().toString();
    private static final WallsView VIEW = new WallsView();

    @Getter
    private final int baseProductionCost = 200;

    @Getter
    private final int cityDefenseStrength = 40;

    @Getter
    private final int localHappiness = 0;

    @Getter
    private final int globalHappiness = 0;

    public Walls(City city) {
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

    /**
     * Walls increase a city's Defense Strength making the city more difficult to capture.
     * Walls are quite useful for cities located along a civilization's frontier.
     */
    @Override
    public Supply calcIncomeSupply() {
        return Supply.builder().gold(-1).build();
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
        return civilization.getYear().isAfter(Year.ANCIENT_ERA) &&
            civilization.isResearched(Technology.MASONRY);
    }

    @Override
    public int getGoldCost(Civilization civilization) {
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
