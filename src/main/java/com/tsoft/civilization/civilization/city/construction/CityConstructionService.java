package com.tsoft.civilization.civilization.city.construction;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.building.BuildingFactory;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.event.NewBuildingBuiltEvent;
import com.tsoft.civilization.civilization.city.event.NewUnitBuiltEvent;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.unit.catalog.settlers.Settlers;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
public class CityConstructionService {

    private final City city;

    @Getter
    @Setter
    private ConstructionStrategy constructionStrategy;

    // Current constructions (buildings, units etc.) in needed order.
    // Order is important, as the player can set as a first an expensive construction
    // and all the construction process will wait (no other less expensive construction started)
    // until the City will get enough production to build the expensive construction
    private ConstructionList constructions = new ConstructionList();

    // Constructions built during the last move
    private ConstructionList builtThisYear = new ConstructionList();

    public CityConstructionService(City city) {
        this.city = city;
        this.constructionStrategy = ConstructionStrategy.AUTO;
    }

    /** Can we start ANY construction NOW, or not ? */
    public boolean canStartConstruction() {
        return city.getPassScore() > 0;
    }

    public ConstructionList getConstructions() {
        return constructions.unmodifiableList();
    }

    public void startConstruction(CanBeBuilt object) {
        Construction construction = new Construction(city.getCivilization(), object);
        constructionStrategy.add(constructions, construction);
    }

    public void startYear() {
        builtThisYear = new ConstructionList();
    }

    public double calcConstructionTurns(Construction construction) {
        if (constructions.isEmpty()) {
            return 0;
        }

        Supply supply = city.calcSupply();
        double production = supply.getProduction();
        if (production <= 0) {
            return -1;
        }

        double cost = construction.getProductionCost();
        return cost / production + 1.0;
    }

    // Buildings and units construction
    public Supply stopYear(Supply supply) {
        if (constructions.isEmpty()) {
            log.debug("{} no construction is in progress", city.getName());
            return Supply.EMPTY;
        }

        Supply constructionExpenses = doConstruction(supply.getProduction());
        log.debug("{} construction expenses = {}", city.getName(), constructionExpenses);

        createBuiltThisYearList();

        createNextYearConstructionList();

        return constructionExpenses;
    }

    private Supply doConstruction(double approvedProduction) {
        // At the Unhappiness level of -10 ("Very Unhappy"), you can't train Settlers anymore
        boolean veryUnhappy = city.getCivilization().getUnhappiness().getTotal() <= -10;

        int usedProductionCost = 0;

        // constructions already sorted by priority
        for (Construction construction : constructions) {
            double cost = construction.getProductionCost();
            if (cost <= 0) {
                continue;
            }

            if (veryUnhappy && Settlers.CLASS_UUID.equals(construction.getObject().getClassUuid())) {
                log.trace("{} can't train Settlers, the unhappiness level is 'Very Unhappy'", city.getName());
                continue;
            }

            double usedConstructionCost = Math.min(cost, approvedProduction);
            construction.useProductionCost(usedConstructionCost);

            usedProductionCost += usedConstructionCost;
            approvedProduction -= usedProductionCost;

            if (approvedProduction <= 0) {
                break;
            }
        }

        return Supply.builder().production(-usedProductionCost).build();
    }

    public ConstructionList getBuiltThisYear() {
        return builtThisYear.unmodifiableList();
    }

    private void createBuiltThisYearList() {
        builtThisYear = new ConstructionList(
            constructions.stream()
                .filter(c -> c.getProductionCost() == 0)
                .peek(this::constructionDone)
                .collect(Collectors.toList()));
    }

    private void createNextYearConstructionList() {
        constructions = new ConstructionList(
            constructions.stream()
                .filter(c -> c.getProductionCost() > 0)
                .collect(Collectors.toList()));
    }

    // Construction of a building or a unit is finished
    private void constructionDone(Construction construction) {
        CanBeBuilt obj = construction.getObject();

        if (obj instanceof AbstractBuilding) {
            AbstractBuilding building = BuildingFactory.newInstance(obj.getClassUuid(), city);
            city.addBuilding(building);

            city.getCivilization().getWorld().sendEvent(NewBuildingBuiltEvent.builder()
                .buildingName(building.getView().getName())
                .build());

            return;
        }

        if (obj instanceof AbstractUnit) {
            AbstractUnit unit = (AbstractUnit)obj;
            city.addUnit(unit);

            city.getCivilization().getWorld().sendEvent(NewUnitBuiltEvent.builder()
                .unitName(unit.getView().getName())
                .build());

            return;
        }

        String objectInfo = (obj == null) ? "null" : obj.getClass().getName();
        throw new IllegalArgumentException(city.getName() + " unknown object is built: " + objectInfo);
    }
}
