package com.tsoft.civilization.improvement.city.supply;

import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.util.Point;
import lombok.Getter;

import java.util.List;

public class CitySupplyService {

    @Getter
    private final TileSupplyService tileSupplyService;

    private final BuildingSupplyService buildingSupplyService;
    private final ConstructionSupplyService constructionSupplyService;
    private final PopulationSupplyService populationSupplyService;

    private final City city;

    public CitySupplyService(City city) {
        this.city = city;

        tileSupplyService = new TileSupplyService(city);
        buildingSupplyService = new BuildingSupplyService(city);
        constructionSupplyService = new ConstructionSupplyService(city);
        populationSupplyService = new PopulationSupplyService(city);
    }

    public Supply calcIncomeSupply() {
        return Supply.EMPTY
            .add(tileSupplyService.calcIncomeSupply(city.getCitizenLocations()))
            .add(buildingSupplyService.calcIncomeSupply())
            .add(constructionSupplyService.calcIncomeSupply())
            .add(populationSupplyService.calcIncomeSupply());
    }

    public Supply calcOutcomeSupply() {
        return Supply.EMPTY
            .add(tileSupplyService.calcOutcomeSupply(city.getCitizenLocations()))
            .add(buildingSupplyService.calcOutcomeSupply())
            .add(constructionSupplyService.calcOutcomeSupply())
            .add(populationSupplyService.calcOutcomeSupply());
    }

    public List<TileSupplyStrategy> getSupplyStrategy() {
        return populationSupplyService.getSupplyStrategy();
    }

    public boolean setSupplyStrategy(List<TileSupplyStrategy> supplyStrategy) {
        return populationSupplyService.setSupplyStrategy(supplyStrategy);
    }

    public Supply calcTilesSupply() {
        Supply income = tileSupplyService.calcIncomeSupply(city.getCitizenLocations());
        Supply outcome = tileSupplyService.calcOutcomeSupply(city.getCitizenLocations());
        return income.add(outcome);
    }

    public Supply calcTilesSupply(Point location) {
        Supply income = tileSupplyService.calcIncomeSupply(location);
        Supply outcome = tileSupplyService.calcOutcomeSupply(location);
        return income.add(outcome);
    }
}
