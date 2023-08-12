package com.tsoft.civilization.civilization.city.supply;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.tile.TileSupplyStrategy;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.economic.TileService;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.civilization.city.citizen.Citizen;
import com.tsoft.civilization.civilization.city.citizen.CitizenPlacementTable;
import com.tsoft.civilization.util.Point;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.tsoft.civilization.civilization.tile.TileSupplyStrategy.*;

@Slf4j
public class PopulationSupplyService {

    private final TileService tileService = new TileService();

    private final City city;

    @Getter
    private List<TileSupplyStrategy> supplyStrategy;

    public PopulationSupplyService(City city) {
        this.city = city;
        this.supplyStrategy = List.of(MAX_FOOD, MAX_PRODUCTION, MAX_GOLD, MAX_SCIENCE, MAX_CULTURE, MAX_FAITH);
    }

    /* Returns NULL when a location doesn't found */
    public Point findLocationForCitizen(Set<Point> usedLocations) {
        Set<Point> locations = new HashSet<>(city.getTileService().getTerritory());
        usedLocations.forEach(locations::remove);

        // find out potential tiles
        List<AbstractTerrain> tiles = new ArrayList<>();
        Map<AbstractTerrain, Supply> supplies = new HashMap<>();
        for (Point location : locations) {
            AbstractTerrain tile = city.getCivilization().getTilesMap().getTile(location);

            // don't place a citizen on harsh tiles (terrain features)
            if (!CitizenPlacementTable.canPlaceCitizen(tile)) {
                log.trace("Can't place a citizen on tile {}", tile);
                continue;
            }

            // a citizen won't work on tiles without supply
            Supply tileSupply = tileService.calcSupply(tile);
            if (isEmptySupply(tileSupply)) {
                continue;
            }

            tiles.add(tile);
            supplies.put(tile, tileSupply);
        }

        // sort by supply
        tiles.sort((t1, t2) -> {
            Supply s1 = supplies.get(t1);
            Supply s2 = supplies.get(t2);

            for (TileSupplyStrategy strategy : supplyStrategy) {
                // reverse, from best to worst
                int c = -strategy.compare(s1, s2);

                if (c < 0) {
                    return c;
                }

                if (c > 0) {
                    return c;
                }

                // equal, go to the next strategy
            }

            return 0;
        });

        // return the best
        return tiles.isEmpty() ? null : tiles.get(0).getLocation();
    }

    private boolean isEmptySupply(Supply supply) {
        return supply.getFood() <= 0 &&
            supply.getProduction() <= 0 &&
            supply.getGold() <= 0;
    }

    public boolean setSupplyStrategy(List<TileSupplyStrategy> supplyStrategy) {
        if (!this.supplyStrategy.equals(supplyStrategy)) {
            this.supplyStrategy = supplyStrategy;
            return true;
        }
        return false;
    }

    public Supply getBaseSupply(Civilization civilization) {
        return Supply.EMPTY;
    }

    public Supply calcIncomeSupply(Civilization civilization) {
        Supply supply = Supply.EMPTY;

        for (Citizen citizen : city.getPopulationService().getCitizens()) {
            supply = supply.add(citizen.calcIncomeSupply(city.getCivilization()));
        }

        return supply;
    }

    public Supply calcOutcomeSupply(Civilization civilization) {
        Supply supply = Supply.EMPTY;

        for (Citizen citizen : city.getPopulationService().getCitizens()) {
            supply = supply.add(citizen.calcOutcomeSupply(city.getCivilization()));
        }

        return supply;
    }
}
