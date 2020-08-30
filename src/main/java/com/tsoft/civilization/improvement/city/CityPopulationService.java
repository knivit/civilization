package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.improvement.City;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.unit.civil.citizen.CitizenPlacementTable;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.util.Year;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.util.Event;
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
public class CityPopulationService {
    private City city;

    private List<Citizen> citizens = new ArrayList<>();

    private Map<Year, Supply> supplyHistory = new HashMap<>();

    private CitySupplyStrategy supplyStrategy = CitySupplyStrategy.MAX_FOOD;

    private int growthPool = 0;
    private boolean isStarvation = false;

    public CityPopulationService(City city) {
        this.city = city;
    }

    public int getCitizenCount() {
        return citizens.size();
    }

    public void addCitizen() {
        Citizen citizen = new Citizen(city);
        Point location = findLocationForCitizen(getPopulationLocations());
        citizen.setLocation(location);
        citizens.add(citizen);
    }

    public List<Point> getPopulationLocations() {
        return citizens.stream()
            .map(Citizen::getLocation)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    private Point findLocationForCitizen(List<Point> usedLocations) {
        Set<Point> locations = new HashSet<>(city.getLocations());
        locations.removeAll(usedLocations);

        Point bestLocation = null;
        for (Point location : locations) {
            AbstractTile<?> tile = city.getTilesMap().getTile(location);

            // don't place a citizen on harsh tiles (terrain features)
            if (!CitizenPlacementTable.canPlaceCitizen(tile)) {
                continue;
            }

            // if the selected tile provides empty (or negative) supply, don't place a citizen here
            Supply tileSupply = tile.getSupply();
            boolean negative =
                (tileSupply.getFood() < 0) ||
                (tileSupply.getProduction() < 0) ||
                (tileSupply.getGold() < 0);
            if (negative) {
                continue;
            }

            if (bestLocation == null) {
                bestLocation = location;
                continue;
            }

            AbstractTile<?> bestTile = city.getTilesMap().getTile(bestLocation);
            Supply bestTileSupply = bestTile.getSupply();

            // in case a tile gives the same amount of needed supply,
            // check also other supplements and select the best supply
            switch (city.getSupplyStrategy()) {
                case MAX_FOOD: {
                    if (tileSupply.getFood() > bestTileSupply.getFood()) {
                        bestLocation = location;
                    }
                    if (tileSupply.getFood() == bestTileSupply.getFood() &&
                        (tileSupply.getProduction() > bestTileSupply.getProduction() ||
                         (tileSupply.getGold() > bestTileSupply.getGold()))) {
                        bestLocation = location;
                    }
                    break;
                }
                case MAX_PRODUCTION: {
                    if (tileSupply.getProduction() > bestTileSupply.getProduction()) {
                        bestLocation = location;
                    }
                    if (tileSupply.getProduction() == bestTileSupply.getProduction() &&
                        (tileSupply.getFood() > bestTileSupply.getFood() ||
                         (tileSupply.getGold() > bestTileSupply.getGold()))) {
                        bestLocation = location;
                    }
                    break;
                }
                case MAX_GOLD: {
                    if (tileSupply.getGold() > bestTileSupply.getGold()) {
                        bestLocation = location;
                    }
                    if (tileSupply.getGold() == bestTileSupply.getGold() &&
                        (tileSupply.getFood() > bestTileSupply.getFood() ||
                         (tileSupply.getProduction() > bestTileSupply.getProduction()))) {
                        bestLocation = location;
                    }
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Unknown supply strategy = " + city.getSupplyStrategy());
                }
            }
        }

        return bestLocation;
    }

    public CitySupplyStrategy getSupplyStrategy() {
        return supplyStrategy;
    }

    public void setSupplyStrategy(CitySupplyStrategy supplyStrategy) {
        if (!this.supplyStrategy.equals(supplyStrategy)) {
            this.supplyStrategy = supplyStrategy;
            reorganizeCitizensOnTiles();
        }
    }

    public Supply getAllCitizensSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;
        for (Citizen citizen : citizens) {
            supply = supply.add(citizen.getSupply());
        }
        return supply;
    }

    public Supply calcSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;
        for (Citizen citizen : citizens) {
            supply = supply.add(citizen.getSupply());
        }

        // 1 citizen consumes 1 food
        supply = getAllCitizensSupply().add(Supply.builder().food(-citizens.size()).build());

        return supply;
    }

    // Citizen's birth, death, happiness
    public void step(Year year) {
        Supply supply = calcSupply();
        supplyHistory.put(year, supply);

        updateState(supply);

        if (supply.getFood() > 0) {
            birth();
        } else {
            death();
        }
    }

    public boolean starvation() {
        return isStarvation;
    }

    private void updateState(Supply supply) {
        // Food income is positive
        if (supply.getFood() >= 0) {
            if (isStarvation) {
                isStarvation = false;

                Event event = new Event(Event.INFORMATION, city, L10nCity.STARVATION_ENDED);
                city.getCivilization().addEvent(event);
                log.debug("{}", event);
            }

            // Excess food above the 2 per citizen goes into a pool for growth
            int excessFood = supply.getFood() - (2 * citizens.size());
            if (excessFood > 0) {
                growthPool += excessFood;
            }
            return;
        }

        // Can we use food from the growth pool ?
        int absSupplyFood = -supply.getFood();
        if (growthPool >= absSupplyFood) {
            growthPool -= absSupplyFood;
            return;
        }

        // Starvation
        growthPool = 0;
        isStarvation = true;

        Event event = new Event(Event.INFORMATION, city, L10nCity.STARVATION_STARTED);
        city.getCivilization().addEvent(event);
    }

    private void birth() {
        int birthFood = 2 * citizens.size();
        if (growthPool <= birthFood) return;;

        growthPool -= birthFood;

        addCitizen();

        Event event = new Event(Event.INFORMATION, city, L10nCity.CITIZEN_WAS_BORN, city.getView().getLocalizedCityName());
        city.getCivilization().addEvent(event);
        log.debug("{}", event);
    }

    // If the growth pool is emptied, one citizen is dying of starvation
    private void death() {
        if (growthPool >= 0) return;

        if (citizens.size() == 0) return;

        // A citizen with minimal food produce have to die
        Citizen target = citizens.get(0);
        Supply targetSupply = target.getSupply();
        if (targetSupply.getFood() > 0) {
            for (int i = 1; i < citizens.size(); i ++) {
                Citizen current = citizens.get(i);
                Supply currentSupply = current.getSupply();
                if (currentSupply.getFood() == 0) {
                    target = current;
                    break;
                }

                if (currentSupply.getFood() < targetSupply.getFood()) {
                    target = current;
                    targetSupply = currentSupply;
                }
            }
        }

        citizens.remove(target);

        Event event = new Event(Event.INFORMATION, city, L10nCity.CITIZEN_HAS_DIED, city.getView().getLocalizedCityName());
        city.getCivilization().addEvent(event);
        log.debug("{}", event);
    }

    // The supply strategy has changed, reorganize citizens for best profit
    private void reorganizeCitizensOnTiles() {
        // Find new places
        List<Point> locations = new ArrayList<>();
        for (int i = 0; i < citizens.size(); i ++) {
            Point location = findLocationForCitizen(locations);

            // No tile found, the reorganization is impossible
            if (location == null) {
                return;
            }

            locations.add(location);
        }

        // Set citizens on them
        for (int i = 0; i < citizens.size(); i ++) {
            citizens.get(i).setLocation(locations.get(i));
        }
    }
}
