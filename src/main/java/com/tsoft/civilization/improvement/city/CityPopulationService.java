package com.tsoft.civilization.improvement.city;

import com.tsoft.civilization.L10n.L10nCity;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.unit.civil.citizen.CitizenPlacementTable;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.world.Year;
import com.tsoft.civilization.world.economic.Supply;
import com.tsoft.civilization.world.economic.SupplyService;
import com.tsoft.civilization.world.event.Event;
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
    private final City city;

    private final List<Citizen> citizens = new ArrayList<>();

    private final Map<Year, Supply> supplyHistory = new HashMap<>();

    private CitySupplyStrategy supplyStrategy = CitySupplyStrategy.MAX_FOOD;
    private final SupplyService supplyService = new SupplyService();

    private int growthPool = 0;
    private boolean isStarvation = false;

    public CityPopulationService(City city) {
        Objects.requireNonNull(city, "city can't be null");

        this.city = city;
    }

    public int getCitizenCount() {
        return citizens.size();
    }

    public void addCitizen() {
        Citizen citizen = new Citizen(city);
        Point location = findLocationForCitizen(getPopulationLocations());

        if (location != null) {
            citizen.setLocation(location);
            citizens.add(citizen);
        }
    }

    public List<Point> getPopulationLocations() {
        return citizens.stream()
            .map(Citizen::getLocation)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
    }

    /* Returns NULL when a location doesn't found */
    private Point findLocationForCitizen(List<Point> usedLocations) {
        Set<Point> locations = new HashSet<>(city.getLocations());
        locations.removeAll(usedLocations);

        AbstractTile bestTile = null;
        Supply bestTileSupply = null;
        for (Point location : locations) {
            AbstractTile tile = city.getTilesMap().getTile(location);
            Supply tileSupply = tile.getSupply();
            log.trace("Supply of tile {} = {}", tile, tileSupply);

            // don't place a citizen on harsh tiles (terrain features)
            if (!CitizenPlacementTable.canPlaceCitizen(tile)) {
                log.trace("Can't place a citizen on this tile, skipped");
                continue;
            }

            // if the selected tile provides empty (or negative) supply, don't place a citizen here
            if (supplyService.isNegative(tileSupply) || supplyService.isEmpty(tileSupply)) {
                log.trace("Tile's supply iz zero or negative, skipped");
                continue;
            }

            if (bestTile == null) {
                bestTile = tile;
                bestTileSupply = tileSupply;
                continue;
            }

            // in case a tile gives the same amount of needed supply,
            // do check other supplements and select the best supply
            int cmp = supplyService.compare(supplyStrategy, tileSupply, bestTileSupply);
            if (cmp > 0) {
                bestTile = tile;
                bestTileSupply = tileSupply;
            }
        }

        log.trace("Best supply tile {} = {} for the strategy = {}", bestTile, bestTileSupply, supplyStrategy);
        return (bestTile == null) ? null : bestTile.getLocation();
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
        // 1 citizen consumes 1 food
        return getAllCitizensSupply()
            .add(Supply.builder().food(-citizens.size()).build());
    }

    public void startYear() {

    }

    // Citizen's birth, death, happiness
    public void move(Year year) {
        Supply supply = calcSupply();
        supplyHistory.put(year, supply);

        updateState(supply);

        if (supply.getFood() > 0) {
            birth();
        } else {
            death();
        }
    }

    public void stopYear() {

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
    }

    // The supply strategy has changed, reorganize citizens for best profit
    private void reorganizeCitizensOnTiles() {
        List<Point> locations = findNewLocations();
        reorganizeCitizens(locations);
    }

    // Returns NULL if reorganization is impossible
    private List<Point> findNewLocations() {
        List<Point> usedLocations = new ArrayList<>();

        for (int i = 0; i < citizens.size(); i ++) {
            Point location = findLocationForCitizen(usedLocations);

            // No tile found, the reorganization is impossible
            if (location == null) {
                log.debug("Citizen reorganization is impossible, as not all citizens can be moved to another tile");
                return null;
            }

            usedLocations.add(location);
        }
        return usedLocations;
    }

    private void reorganizeCitizens(List<Point> locations) {
        if (locations == null) {
            return;
        }

        if (locations.size() != citizens.size()) {
            throw new IllegalStateException("Locations size = " + locations.size() +
                " is not equal to number of citizens = " + citizens.size());
        }

        for (int i = 0; i < citizens.size(); i++) {
            citizens.get(i).setLocation(locations.get(i));
        }
    }
}
