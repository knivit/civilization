package com.tsoft.civilization.civilization.tile;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.AbstractImprovement;
import com.tsoft.civilization.tile.TileService;
import com.tsoft.civilization.tile.terrain.AbstractTerrain;
import com.tsoft.civilization.util.Point;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class CivilizationTileSupplyService {

    private final TileService tileService = new TileService();

    private final Civilization civilization;

    public Supply calcIncomeSupply(Set<Point> locations) {
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

    public Supply calcOutcomeSupply(Set<Point> locations) {
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

        AbstractTerrain tile = civilization.getTilesMap().getTile(location);

        for (AbstractImprovement improvement : tile.getImprovements()) {
            if (improvement.getBaseState().isBlockingTileSupply()) {
                return Supply.EMPTY;
            }
        }

        return tileService.calcSupply(tile);
    }

    // Calc supply from improvements
    private Supply calcImprovementsSupply(Point location) {
        if (location == null) {
            return Supply.EMPTY;
        }

        AbstractTerrain tile = civilization.getTilesMap().getTile(location);

        Supply supply = Supply.EMPTY;
        for (AbstractImprovement improvement : tile.getImprovements()) {
            supply = supply.add(improvement.calcIncomeSupply(civilization));
        }

        return supply;
    }
}
