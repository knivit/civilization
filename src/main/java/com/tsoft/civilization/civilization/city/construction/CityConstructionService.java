package com.tsoft.civilization.civilization.city.construction;

import com.tsoft.civilization.civilization.building.AbstractBuilding;
import com.tsoft.civilization.civilization.building.BuildingFactory;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.event.NewBuildingBuiltEvent;
import com.tsoft.civilization.civilization.city.event.NewUnitBuiltEvent;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.world.DifficultyLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.stream.Collectors;

import static com.tsoft.civilization.world.DifficultyLevel.*;
import static com.tsoft.civilization.world.DifficultyLevel.DEITY;

@Slf4j
public class CityConstructionService {

    public static final Map<DifficultyLevel, Double> BUILDING_COST_PER_DIFFICULTY_LEVEL = Map.of(
        SETTLER, 0.5,
        CHIEFTAIN, 0.67,
        WARLORD, 0.85,
        PRINCE, 1.0,
        KING, 1.0,
        EMPEROR, 1.0,
        IMMORTAL, 1.0,
        DEITY, 1.0
    );

    private final City city;

    @Getter
    @Setter
    private ConstructionStrategy constructionStrategy;

    // Current constructions (buildings, units etc) in needed order
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

    // Buildings and units construction
    public Supply stopYear(Supply supply) {
        if (constructions.isEmpty()) {
            log.debug("No construction is in progress");
        }

        Supply constructionExpenses = doConstruction(supply.getProduction());
        log.debug("Construction expenses = {}", constructionExpenses);

        createBuiltThisYearList();

        createNextYearConstructionList();

        return constructionExpenses;
    }

    private Supply doConstruction(int approvedProduction) {
        // At the Unhappiness level of -10 ("Very Unhappy"), you can't train Settlers anymore
        boolean veryUnhappy = city.getCivilization().calcUnhappiness().getTotal() <= -10;

        int usedProductionCost = 0;

        // constructions already sorted by priority
        for (Construction construction : constructions) {
            int cost = construction.getProductionCost();
            if (cost <= 0) {
                continue;
            }

            if (veryUnhappy && Settlers.CLASS_UUID.equals(construction.getObject().getClassUuid())) {
                log.trace("Can't train Settlers, the unhappiness level is 'Very Unhappy'");
                continue;
            }

            int usedConstructionCost = Math.min(cost, approvedProduction);
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

    // Construction of a building or an unit is finished
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

        String objectInfo = (obj == null ? "null" : obj.getClass().getName());
        throw new IllegalArgumentException("Unknown object is built: " + objectInfo);
    }
}
