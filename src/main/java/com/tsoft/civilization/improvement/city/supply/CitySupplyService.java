package com.tsoft.civilization.improvement.city.supply;

import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.util.Point;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CitySupplyService {

    @Getter
    private final TileSupplyService tileSupplyService;

    private final BuildingSupplyService buildingSupplyService;
    private final PopulationSupplyService populationSupplyService;

    private final City city;

    private final List<Supply> expenses = new ArrayList<>();

    public CitySupplyService(City city) {
        this.city = city;

        tileSupplyService = new TileSupplyService(city);
        buildingSupplyService = new BuildingSupplyService(city);
        populationSupplyService = new PopulationSupplyService(city);
    }

    public void addExpenses(Supply expenses) {
        this.expenses.add(expenses);
    }

    public Supply calcIncomeSupply() {
        if (city.isResistanceMode()) {
            return Supply.EMPTY;
        }

        Supply tiles = tileSupplyService.calcIncomeSupply(city.getCitizenLocations());
        Supply buildings = buildingSupplyService.calcIncomeSupply();
        Supply population = populationSupplyService.calcIncomeSupply();
        return tiles.add(buildings).add(population);
    }

    public Supply calcOutcomeSupply() {
        Supply tiles = tileSupplyService.calcOutcomeSupply(city.getCitizenLocations());
        Supply buildings = buildingSupplyService.calcOutcomeSupply();
        Supply population = populationSupplyService.calcOutcomeSupply();

        Supply totalExpenses = Supply.EMPTY;
        for (Supply supply : expenses) {
            totalExpenses.add(supply);
        }

        return tiles.add(buildings).add(population).add(totalExpenses);
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

    public void startYear() {
        expenses.clear();
    }
}
