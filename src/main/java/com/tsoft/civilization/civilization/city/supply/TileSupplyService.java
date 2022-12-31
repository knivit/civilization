package com.tsoft.civilization.civilization.city.supply;

import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.tile.improvement.AbstractImprovement;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;

import java.util.List;

public class TileSupplyService {

    private final TileService tileService = new TileService();

    private final City city;

    public TileSupplyService(City city) {
        this.city = city;
    }

    public Supply calcIncomeSupply(List<Point> locations) {
        Supply supply = Supply.EMPTY;

        for (Point location : locations) {
            supply = supply.add(calcIncomeSupply(location));
        }

        return supply;
    }

    public Supply calcIncomeSupply(Point location) {
        Supply tileSupply = calcTileSupply(location);
        Supply improvementSupply = calcImprovementsSupply(location);
        return tileSupply.add(improvementSupply);
    }

    public Supply calcOutcomeSupply(List<Point> locations) {
        return Supply.EMPTY;
    }

    public Supply calcOutcomeSupply(Point locations) {
        return Supply.EMPTY;
    }

    // Calc supply from tiles without improvements
    private Supply calcTileSupply(Point location) {
        if (location == null) {
            return Supply.EMPTY;
        }

        AbstractTerrain tile = city.getCivilization().getTilesMap().getTile(location);
        AbstractImprovement improvement = tile.getImprovement();
        if (improvement == null || !improvement.isBlockingTileSupply()) {
            return tileService.calcSupply(tile);
        }

        return Supply.EMPTY;
    }

    // Calc supply from improvements
    private Supply calcImprovementsSupply(Point location) {
        if (location == null) {
            return Supply.EMPTY;
        }

        AbstractTerrain tile = city.getCivilization().getTilesMap().getTile(location);
        AbstractImprovement improvement = tile.getImprovement();
        if (improvement != null) {
            return improvement.getSupply();
        }

        return Supply.EMPTY;
    }
}
