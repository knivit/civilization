package com.tsoft.civilization.building.granary;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingType;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.technology.Technology;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.tile.resource.bonus.Bananas;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;

import java.util.UUID;

/**
 * Granary
 * -------
 * Building of the Ancient Era
 *
 * Cost
 * ----
 * Production	    Maintenance
 * 60 Production	1 Gold
 * Required Technology: Pottery
 * Effects
 *   +2 Food
 *   +1 Food Each source of wheat, bananas and deer worked by the city produces
 *
 * Basic growth-enhancing building of the Ancient Era.
 * Allows Food to be moved from this city along trade routes inside your civilization.
 *
 * Strategy
 * --------
 * The Granary increases the Food Food production of a city, speeding up the city's growth, especially if it has one of
 * the above-mentioned resources nearby (which need not be improved with a Farm).
 * It's always useful when you want to create a large city, and for speeding up growth of smaller ones.
 * It's also incredibly useful to help cities with poor access to fertile land.
 *
 * A granary is a building constructed to store grain for human or herd animal consumption (or both).
 * The earliest granaries yet discovered were found in a pre-pottery Neolithic settlement in the Jordan Valley
 * of the Middle East, and date back to approximately 9500 BC.
 * These first granaries measured about 10-foot by 10-foot square. They featured raised floors which allowed air
 * to circulate more freely and provided some protection for the grain from mice and other menaces.
 */
public class Granary extends AbstractBuilding {

    public static final String CLASS_UUID = UUID.randomUUID().toString();
    private static final GranaryView VIEW = new GranaryView();

    @Getter
    private final int baseProductionCost = 60;

    @Getter
    private final int cityDefenseStrength = 0;

    @Getter
    private final int localHappiness = 0;

    @Getter
    private final int globalHappiness = 0;

    public Granary(City city) {
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
     * Each source of Wheat Bananas and Deer worked by this City produce +1 Food.
     */
    @Override
    public Supply calcIncomeSupply() {
        int food = 2;
        for (Point location : getCity().getCitizenLocations()) {
            AbstractTerrain tile = getTile(location);
            if (Bananas.class.equals(tile.getLuxury().getClass())) {
                food ++;
            }
        }

        return Supply.builder().food(food).production(2).gold(-1).build();
    }

    @Override
    public Supply calcOutcomeSupply() {
        return Supply.EMPTY;
    }

    @Override
    public int getGoldCost(Civilization civilization) {
        return 340;
    }

    @Override
    public boolean requiresEraAndTechnology(Civilization civilization) {
        return civilization.getYear().isAfter(Year.ANCIENT_ERA) &&
            civilization.isResearched(Technology.POTTERY);
    }

    @Override
    public GranaryView getView() {
        return VIEW;
    }

    @Override
    public String getClassUuid() {
        return CLASS_UUID;
    }

    @Override
    public void startYear() {

    }

    @Override
    public void stopYear() {

    }
}