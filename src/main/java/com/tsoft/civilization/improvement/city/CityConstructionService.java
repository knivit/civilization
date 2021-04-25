package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingFactory;
import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.improvement.city.event.NewBuildingBuiltEvent;
import com.tsoft.civilization.improvement.city.event.NewUnitBuiltEvent;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
public class CityConstructionService implements HasSupply {

    private final City city;

    @Getter
    private Supply supply = Supply.EMPTY_SUPPLY;

    // Current constructions (buildings, units etc) in needed order
    // Order is important, as the player can set as a first an expensive construction
    // and all the construction process will wait (no other less expensive construction started)
    // until the City will get enough production to build the expensive construction
    private ConstructionList constructions = new ConstructionList();

    // Constructions built during the last move
    private ConstructionList builtThisYear = new ConstructionList();

    public CityConstructionService(City city) {
        this.city = city;
    }

    /** Can we start ANY construction NOW, or not ? */
    public boolean canStartConstruction() {
        return city.getPassScore() > 0;
    }

    public ConstructionList getConstructions() {
        return constructions.unmodifiableList();
    }

    public void startConstruction(CanBeBuilt object) {
        constructions.add(new Construction(object));
    }

    @Override
    public Supply calcSupply() {
        int productionCost = 0;
        for (Construction construction : constructions) {
            int cost = construction.getProductionCost();
            if (cost <= 0) {
                continue;
            }

            productionCost += cost;
        }

        return Supply.builder().production(-productionCost).build();
    }

    @Override
    public void startYear() {
        builtThisYear = new ConstructionList();
    }

    // Buildings and units construction
    @Override
    public void stopYear() {
        if (constructions.isEmpty()) {
            log.debug("No construction is in progress");
        }

        Supply citySupply = city.getSupply();
        int approvedProduction = citySupply.getProduction();

        if (approvedProduction <= 0) {
            log.debug("The production = {} is less or equal 0, any construction is postponed", approvedProduction);
        }

        Supply constructionExpenses = doConstruction(approvedProduction);
        log.debug("Construction expenses = {}", constructionExpenses);

        createBuiltThisYearList();

        createNextYearConstructionList();

        supply = constructionExpenses;
    }

    private Supply doConstruction(int approvedProduction) {
        int productionCost = 0;
        for (Construction construction : constructions) {
            int cost = construction.getProductionCost();
            if (cost <= 0) {
                continue;
            }

            if ((productionCost + cost) > approvedProduction) {
                break;
            }

            construction.useProductionCost(cost);
            productionCost += cost;
        }

        return Supply.builder().production(-productionCost).build();
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

    // Construction of a building or an unit is finished
    private void constructionDone(Construction construction) {
        CanBeBuilt obj = construction.getObject();

        if (obj instanceof AbstractBuilding) {
            AbstractBuilding building = BuildingFactory.newInstance(obj.getClassUuid(), city);
            city.addBuilding(building);

            city.getWorld().sendEvent(NewBuildingBuiltEvent.builder()
                .buildingName(building.getView().getName())
                .build());

            return;
        }

        if (obj instanceof AbstractUnit) {
            AbstractUnit unit = (AbstractUnit)obj;
            city.addUnit(unit);

            city.getWorld().sendEvent(NewUnitBuiltEvent.builder()
                .unitName(unit.getView().getName())
                .build());

            return;
        }

        String objectInfo = (obj == null ? "null" : obj.getClass().getName());
        throw new IllegalArgumentException("Unknown object is built: " + objectInfo);
    }
}
