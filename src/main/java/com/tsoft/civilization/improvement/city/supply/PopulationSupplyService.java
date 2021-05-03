package com.tsoft.civilization.improvement.city.supply;

import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.base.AbstractTile;
import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.unit.civil.citizen.CitizenPlacementTable;
import com.tsoft.civilization.util.Point;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class PopulationSupplyService implements HasSupply {

    private final TileService tileService = new TileService();

    private TileSupplyStrategy supplyStrategy = TileSupplyStrategy.MAX_FOOD;

    private final City city;

    public PopulationSupplyService(City city) {
        this.city = city;
    }

    /* Returns NULL when a location doesn't found */
    public Point findLocationForCitizen(List<Point> usedLocations) {
        Set<Point> locations = new HashSet<>(city.getLocations());
        usedLocations.forEach(locations::remove);

        AbstractTile bestTile = null;
        Supply bestTileSupply = null;
        for (Point location : locations) {
            AbstractTile tile = city.getTilesMap().getTile(location);
            Supply tileSupply = tileService.calcSupply(tile);
            log.trace("Supply of tile {} = {}", tile, tileSupply);

            // don't place a citizen on harsh tiles (terrain features)
            if (!CitizenPlacementTable.canPlaceCitizen(tile)) {
                log.trace("Can't place a citizen on this tile, skipped");
                continue;
            }

            // if the selected tile provides empty (or negative) supply, don't place a citizen here
            if (isNegative(tileSupply) || isEmpty(tileSupply)) {
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
            int cmp = supplyStrategy.compare(tileSupply, bestTileSupply);
            if (cmp > 0) {
                bestTile = tile;
                bestTileSupply = tileSupply;
            }
        }

        log.trace("Best supply tile {} = {} for the strategy = {}", bestTile, bestTileSupply, supplyStrategy);
        return (bestTile == null) ? null : bestTile.getLocation();
    }

    private boolean isNegative(Supply supply) {
        return supply.getFood() < 0 ||
            supply.getProduction() < 0 ||
            supply.getGold() < 0;
    }

    private boolean isEmpty(Supply supply) {
        return supply.getFood() == 0 &&
            supply.getProduction() == 0 &&
            supply.getGold() == 0;
    }

    public TileSupplyStrategy getSupplyStrategy() {
        return supplyStrategy;
    }

    public boolean setSupplyStrategy(TileSupplyStrategy supplyStrategy) {
        if (!this.supplyStrategy.equals(supplyStrategy)) {
            this.supplyStrategy = supplyStrategy;
            return true;
        }
        return false;
    }

    @Override
    public Supply calcIncomeSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;

        for (Citizen citizen : city.population().getCitizens()) {
            supply = supply.add(citizen.calcIncomeSupply());
        }

        Supply population = Supply.builder().population(city.getCitizenCount()).build();

        return supply.add(population);
    }

    @Override
    public Supply calcOutcomeSupply() {
        Supply supply = Supply.EMPTY_SUPPLY;

        for (Citizen citizen : city.population().getCitizens()) {
            supply = supply.add(citizen.calcOutcomeSupply());
        }

        return supply;
    }
}
