package com.tsoft.civilization.civilization.city.supply;

import com.tsoft.civilization.civilization.tile.CivilizationTileSupplyService;
import com.tsoft.civilization.civilization.tile.TileSupplyStrategy;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.util.Point;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CitySupplyService {

    private final CivilizationTileSupplyService tileSupplyService;
    private final BuildingSupplyService buildingSupplyService;
    private final PopulationSupplyService populationSupplyService;

    @Getter
    private final City city;

    private Supply supply = Supply.EMPTY;

    private final List<Supply> income = new ArrayList<>();
    private final List<Supply> expenses = new ArrayList<>();

    public CitySupplyService(City city) {
        this.city = city;

        tileSupplyService = new CivilizationTileSupplyService(city.getCivilization());
        buildingSupplyService = new BuildingSupplyService(city);
        populationSupplyService = new PopulationSupplyService(city);
    }

    public void addIncome(Supply income) {
        this.income.add(income);
    }

    public void addExpenses(Supply expenses) {
        this.expenses.add(expenses);
    }

    public Supply getSupply() {
        Supply currentIncome = income.stream().reduce(Supply.EMPTY, Supply::add);
        Supply currentExpenses = expenses.stream().reduce(Supply.EMPTY, Supply::add);
        return supply.add(currentIncome).add(currentExpenses);
    }

    public Supply calcSupply() {
        return supply.add(calcIncomeSupply()).add(calcOutcomeSupply());
    }

    public Supply calcIncomeSupply() {
        if (city.isResistanceMode()) {
            return Supply.EMPTY;
        }

        Supply tiles = tileSupplyService.calcIncomeSupply(city.getCitizenLocations());
        Supply buildings = buildingSupplyService.calcIncomeSupply(city.getCivilization());
        Supply population = populationSupplyService.calcIncomeSupply(city.getCivilization());
        Supply currentIncome = income.stream().reduce(Supply.EMPTY, Supply::add);

        return tiles.add(buildings).add(population).add(currentIncome);
    }

    public Supply calcOutcomeSupply() {
        Supply tiles = tileSupplyService.calcOutcomeSupply(city.getCitizenLocations());
        Supply buildings = buildingSupplyService.calcOutcomeSupply(city.getCivilization());
        Supply population = populationSupplyService.calcOutcomeSupply(city.getCivilization());
        Supply currentExpenses = expenses.stream().reduce(Supply.EMPTY, Supply::add);

        return tiles.add(buildings).add(population).add(currentExpenses);
    }

    public List<TileSupplyStrategy> getSupplyStrategy() {
        return populationSupplyService.getSupplyStrategy();
    }

    public boolean setSupplyStrategy(List<TileSupplyStrategy> supplyStrategy) {
        return populationSupplyService.setSupplyStrategy(supplyStrategy);
    }

    public Supply calcTilesSupply(Point location) {
        Supply income = tileSupplyService.calcIncomeSupply(location);
        Supply outcome = tileSupplyService.calcOutcomeSupply(location);
        return income.add(outcome);
    }

    public void startYear() {
        income.clear();
        expenses.clear();
    }

    public void stopYear(Supply supply) {
        this.supply = supply;
    }
}
