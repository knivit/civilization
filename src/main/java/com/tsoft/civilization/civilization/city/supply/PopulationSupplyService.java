package com.tsoft.civilization.civilization.city.supply;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.HasSupply;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.civilization.city.citizen.Citizen;
import com.tsoft.civilization.civilization.city.citizen.CitizenPlacementTable;
import com.tsoft.civilization.util.Point;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class PopulationSupplyService implements HasSupply {

    private final TileService tileService = new TileService();

    private final City city;
    private List<TileSupplyStrategy> supplyStrategy;

    public PopulationSupplyService(City city) {
        this.city = city;
        this.supplyStrategy = List.of(TileSupplyStrategy.MAX_FOOD, TileSupplyStrategy.MAX_PRODUCTION, TileSupplyStrategy.MAX_GOLD);
    }

    /* Returns NULL when a location doesn't found */
    public Point findLocationForCitizen(List<Point> usedLocations) {
        Set<Point> locations = new HashSet<>(city.getTileService().getLocations());
        usedLocations.forEach(locations::remove);

        AbstractTerrain bestTile = null;
        Supply bestTileSupply = null;
        for (Point location : locations) {
            AbstractTerrain tile = city.getCivilization().getTilesMap().getTile(location);
            Supply tileSupply = tileService.calcSupply(tile);

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
            for (TileSupplyStrategy strategy : supplyStrategy) {
                int cmp = strategy.compare(tileSupply, bestTileSupply);
                if (cmp < 0) {
                    break;
                }
                if (cmp > 0) {
                    bestTile = tile;
                    bestTileSupply = tileSupply;
                    break;
                }
            }
        }

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

    public List<TileSupplyStrategy> getSupplyStrategy() {
        return supplyStrategy;
    }

    public boolean setSupplyStrategy(List<TileSupplyStrategy> supplyStrategy) {
        if (!this.supplyStrategy.equals(supplyStrategy)) {
            this.supplyStrategy = supplyStrategy;
            return true;
        }
        return false;
    }

    @Override
    public Supply getBaseSupply(Civilization civilization) {
        return Supply.EMPTY;
    }

    @Override
    public Supply calcIncomeSupply(Civilization civilization) {
        Supply supply = Supply.EMPTY;

        for (Citizen citizen : city.getPopulationService().getCitizens()) {
            supply = supply.add(citizen.calcIncomeSupply(city.getCivilization()));
        }

        return supply;
    }

    @Override
    public Supply calcOutcomeSupply(Civilization civilization) {
        Supply supply = Supply.EMPTY;

        for (Citizen citizen : city.getPopulationService().getCitizens()) {
            supply = supply.add(citizen.calcOutcomeSupply(city.getCivilization()));
        }

        return supply;
    }
}
