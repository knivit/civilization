package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.building.AbstractBuilding;
import com.tsoft.civilization.building.BuildingFactory;
import com.tsoft.civilization.improvement.CanBeBuilt;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.event.Event;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
public class CityConstructionService {

    private final City city;

    private ConstructionList constructions = new ConstructionList(); // Current constructions (buildings, units etc)
    private ConstructionList builtThisYear = new ConstructionList(); // Constructions built during the last move

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

    public Supply getSupply(int approvedProduction) {
        return calcSupply(approvedProduction, false);
    }

    private Supply doConstruction(int approvedProduction) {
        return calcSupply(approvedProduction, true);
    }

    private Supply calcSupply(int approvedProduction, boolean doConstruction) {
        Supply supply = Supply.EMPTY_SUPPLY;

        for (Construction construction : constructions) {
            int cost = construction.getProductionCost();
            if (cost <= 0) {
                continue;
            }

            int useProduction = Math.min(cost, approvedProduction);
            if (doConstruction) {
                construction.useProductionCost(useProduction);
            }
            approvedProduction -= useProduction;

            Supply constructionExpenses = Supply.builder().production(-useProduction).build();
            supply = supply.add(constructionExpenses);

            if (approvedProduction <= 0) {
                break;
            }
        }

        return supply;
    }

    public void startYear() {
        builtThisYear = new ConstructionList();
    }

    // Buildings and units construction
    public Supply stopYear(Supply supply) {
        if (constructions.isEmpty()) {
            log.debug("No construction is in progress");
        }

        int approvedProduction = supply.getProduction();

        if (approvedProduction <= 0) {
            log.debug("The production = {} is less or equal 0, any construction is postponed", approvedProduction);
        }

        Supply constructionExpenses = doConstruction(approvedProduction);
        log.debug("Construction expenses = {}", constructionExpenses);

        createBuiltThisYearList();

        createNextYearConstructionList();

        return constructionExpenses;
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
            city.getWorld().sendEvent(new Event(Event.UPDATE_WORLD, obj, L10nCity.NEW_BUILDING_BUILT_EVENT, building.getView().getLocalizedName()));
            return;
        }

        if (obj instanceof AbstractUnit) {
            AbstractUnit unit = (AbstractUnit)obj;
            city.addUnit(unit);
            city.getWorld().sendEvent(new Event(Event.UPDATE_WORLD, obj, L10nCity.NEW_UNIT_BUILT_EVENT, unit.getView().getLocalizedName()));
            return;
        }

        String objectInfo = (obj == null ? "null" : obj.getClass().getName());
        throw new IllegalArgumentException("Unknown object is built: " + objectInfo);
    }
}
