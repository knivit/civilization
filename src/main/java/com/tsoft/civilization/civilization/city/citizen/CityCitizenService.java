package com.tsoft.civilization.civilization.city.citizen;

import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.civilization.city.event.CitizenHasDiedEvent;
import com.tsoft.civilization.civilization.city.event.CitizenWasBornEvent;
import com.tsoft.civilization.civilization.city.event.StarvationEndedEvent;
import com.tsoft.civilization.civilization.city.event.StarvationStartedEvent;
import com.tsoft.civilization.civilization.city.supply.PopulationSupplyService;
import com.tsoft.civilization.tile.TilesMap;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.economic.Supply;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.stream.Collectors;

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
public class CityCitizenService {
    private final PopulationSupplyService populationSupplyService;

    private final City city;
    private final CitizenList citizens = new CitizenList();

    @Getter
    @Setter
    private CitizenDeathStrategy citizenDeathStrategy;

    private double growthPool = 0.0;
    private boolean isStarvation = false;

    public CityCitizenService(City city) {
        Objects.requireNonNull(city, "city can't be null");

        this.city = city;

        populationSupplyService = new PopulationSupplyService(city);
        citizenDeathStrategy = CitizenDeathStrategy.MIN_FOOD;
    }

    public int getCitizenCount() {
        return citizens.size();
    }

    public void addCitizen() {
        Citizen citizen = new Citizen();
        citizen.init(city);

        Point location = populationSupplyService.findLocationForCitizen(getCitizenLocations());

        if (location != null) {
            citizen.setLocation(location);
        }

        citizens.add(citizen);

        notifyDependentServices();
    }

    public Set<Point> getCitizenLocations() {
        return citizens.getLocations();
    }

    public List<AbstractTerrain> getCitizenTiles() {
        TilesMap map = city.getCivilization().getTilesMap();
        return citizens.stream()
            .map(e -> map.getTile(e.getLocation()))
            .collect(Collectors.toList());
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
            double excessFood = supply.getFood() - (2 * citizens.size());
            if (excessFood > 0) {
                growthPool += excessFood;
                return Supply.builder().food(-excessFood).build();
            }

            return Supply.EMPTY;
        }

        // Can we use food from the growth pool ?
        double absSupplyFood = -supply.getFood();
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

        if (citizens.isEmpty()) {
            return;
        }

        Citizen victim = citizenDeathStrategy.findStarvationVictims(citizens, city.getSupplyService());
        citizens.remove(victim);

        city.getCivilization().addEvent(CitizenHasDiedEvent.builder()
            .cityName(city.getName())
            .build());

        notifyDependentServices();
    }

    // The supply strategy has changed, reorganize citizens for best profit
    public void reorganizeCitizensOnTiles() {
        Set<Point> newLocations = findNewLocations();
        reorganizeCitizens(newLocations);
    }

    // Returns NULL if reorganization is impossible
    private Set<Point> findNewLocations() {
        Set<Point> newLocations = new HashSet<>();

        for (int i = 0; i < city.getCitizenCount(); i ++) {
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

    private void reorganizeCitizens(Set<Point> locations) {
        if (locations == null || locations.isEmpty()) {
            return;
        }

        if (locations.size() != citizens.size()) {
            throw new IllegalStateException("Locations size = " + locations.size() +
                " is not equal to number of citizens = " + citizens.size());
        }

        Iterator<Point> liter = locations.iterator();
        Iterator<Citizen> citer = citizens.iterator();
        while (liter.hasNext()) {
            citer.next().setLocation(liter.next());
        }
    }

    private void notifyDependentServices() {
        city.getUnhappinessService().recalculate();
    }
}
