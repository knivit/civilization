package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10nCity;
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

    public void startYear() {
        builtThisYear = new ConstructionList();
    }

    // Buildings and units construction
    public Supply move(Supply citySupply) {
        if (constructions.isEmpty()) {
            log.debug("No construction is in progress");
            return citySupply;
        }

        int cityProduction = citySupply.getProduction();
        if (cityProduction <= 0) {
            log.debug("The city has production <= 0, any construction is postponed");
            return citySupply;
        }

        log.debug("City production = {} to be used on constructions", cityProduction);
        for (Construction construction : constructions) {
            int cost = construction.getProductionCost();
            if (cost <= 0) {
                continue;
            }

            int useProduction = Math.min(cost, cityProduction);
            construction.useProductionCost(useProduction);
            cityProduction -= useProduction;

            Supply constructionExpenses = Supply.builder().production(-useProduction).build();
            citySupply = citySupply.add(constructionExpenses);
            log.debug("Production = {} used on {}", useProduction, construction);

            if (cityProduction <= 0) {
                break;
            }
        }

        createBuiltList();

        return citySupply;
    }

    public ConstructionList getBuiltThisYear() {
        return builtThisYear.unmodifiableList();
    }

    private void createBuiltList() {
        builtThisYear = new ConstructionList(
            constructions.stream()
                .filter(c -> c.getProductionCost() == 0)
                .peek(this::constructionDone)
                .collect(Collectors.toList()));
    }

    public void stopYear() {
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
