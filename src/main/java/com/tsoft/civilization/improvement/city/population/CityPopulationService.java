package com.tsoft.civilization.improvement.city.population;

import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.improvement.city.event.CitizenHasDiedEvent;
import com.tsoft.civilization.improvement.city.event.CitizenWasBornEvent;
import com.tsoft.civilization.improvement.city.event.StarvationEndedEvent;
import com.tsoft.civilization.improvement.city.event.StarvationStartedEvent;
import com.tsoft.civilization.improvement.city.supply.PopulationSupplyService;
import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.unit.civil.citizen.CitizenList;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * Excess food above the 2 per citizen required to feed the existing population goes
 * into a pool for growth. When this pool is filled, the city's population will grow by one.
 *
 * If the city is not producing enough food to feed the current population,
 * it will enter "Starvation" mode, and will draw pool from the growth pool.
 *
 * If the growth pool is emptied and the city is still Starving, one population point
 * will die of starvation.
 */
@Slf4j
public class CityPopulationService {
    private final PopulationSupplyService populationSupplyService;

    private final City city;
    private final CitizenList citizens = new CitizenList();

    @Getter
    @Setter
    private CitizenDeathStrategy citizenDeathStrategy;

    private int growthPool = 0;
    private boolean isStarvation = false;

    public CityPopulationService(City city) {
        Objects.requireNonNull(city, "city can't be null");

        this.city = city;

        populationSupplyService = new PopulationSupplyService(city);
        citizenDeathStrategy = CitizenDeathStrategy.MIN_FOOD;
    }

    public int getCitizenCount() {
        return citizens.size();
    }

    public void addCitizen() {
        Citizen citizen = new Citizen(city);
        Point location = populationSupplyService.findLocationForCitizen(getCitizenLocations());

        if (location != null) {
            citizen.setLocation(location);
        }

        citizens.add(citizen);
    }

    public List<Point> getCitizenLocations() {
        return citizens.getLocations();
    }

    public CitizenList getCitizens() {
        return citizens.unmodifiableList();
    }

    public void startYear() {
        citizens.forEach(Citizen::startYear);
    }

    // Citizen's birth, death, happiness
    public Supply stopYear(Supply supply) {
        Supply growthPoolSupply = updateGrowthPool(supply);
        supply = supply.add(growthPoolSupply);

        // At the Unhappiness level of -10 ("Very Unhappy"), population growth stops completely
        if (city.getCivilization().getUnhappiness().getTotal() > -10) {
            if (supply.getFood() > 0) {
                birth();
            }
        }

        if (supply.getFood() <= 0) {
            death();
        }

        return supply;
    }

    public boolean isStarvation() {
        return isStarvation;
    }

    private Supply updateGrowthPool(Supply supply) {
        // Food is positive
        if (supply.getFood() >= 0) {
            if (isStarvation) {
                isStarvation = false;

                city.getCivilization().addEvent(StarvationEndedEvent.builder()
                    .cityName(city.getName())
                    .build());
            }

            // Excess food above the 2 per citizen goes into a pool for growth
            int excessFood = supply.getFood() - (2 * citizens.size());
            if (excessFood > 0) {
                growthPool += excessFood;
                return Supply.builder().food(-excessFood).build();
            }

            return Supply.EMPTY_SUPPLY;
        }

        // Can we use food from the growth pool ?
        int absSupplyFood = -supply.getFood();
        if (growthPool >= absSupplyFood) {
            growthPool -= absSupplyFood;
            return Supply.builder().food(absSupplyFood).build();
        }

        // Starvation
        Supply remains = Supply.builder().food(growthPool).build();
        growthPool = 0;
        isStarvation = (remains.getFood() <= 0);

        city.getCivilization().addEvent(StarvationStartedEvent.builder()
            .cityName(city.getName())
            .build());

        return remains;
    }

    private void birth() {
        int birthFood = 2 * citizens.size();
        if (growthPool <= birthFood) {
            return;
        }

        growthPool -= birthFood;

        addCitizen();

        city.getCivilization().addEvent(CitizenWasBornEvent.builder()
            .cityName(city.getName())
            .build());
    }

    // If the growth pool is emptied, one citizen is dying of starvation
    private void death() {
        if (!isStarvation) {
            return;
        }

        if (citizens.size() == 0) {
            return;
        }

        Citizen victim = citizenDeathStrategy.findStarvationVictims(citizens, city.getSupplyService());
        citizens.remove(victim);

        city.getCivilization().addEvent(CitizenHasDiedEvent.builder()
            .cityName(city.getName())
            .build());
    }

    // The supply strategy has changed, reorganize citizens for best profit
    public void reorganizeCitizensOnTiles() {
        List<Point> newLocations = findNewLocations();
        reorganizeCitizens(newLocations);
    }

    // Returns NULL if reorganization is impossible
    private List<Point> findNewLocations() {
        List<Point> newLocations = new ArrayList<>();

        for (int i = 0; i < city.population().getCitizens().size(); i ++) {
            Point location = populationSupplyService.findLocationForCitizen(newLocations);

            // No tile found, the reorganization is impossible
            if (location == null) {
                log.debug("Citizen reorganization is impossible, as not all citizens can be moved to another tile");
                return null;
            }

            newLocations.add(location);
        }

        return newLocations;
    }

    private void reorganizeCitizens(List<Point> newLocations) {
        if (newLocations == null || newLocations.isEmpty()) {
            return;
        }

        if (newLocations.size() != citizens.size()) {
            throw new IllegalStateException("Locations size = " + newLocations.size() +
                " is not equal to number of citizens = " + citizens.size());
        }

        for (int i = 0; i < citizens.size(); i++) {
            citizens.get(i).setLocation(newLocations.get(i));
        }
    }
}
