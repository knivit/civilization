package com.tsoft.civilization.improvement.city.supply;

import com.tsoft.civilization.economic.Supply;

import java.util.Comparator;

public enum TileSupplyStrategy {

    MAX_FOOD(Comparator.comparingInt(Supply::getFood)),
    MAX_PRODUCTION(Comparator.comparingInt(Supply::getProduction)),
    MAX_GOLD(Comparator.comparingInt(Supply::getGold));

    private final Comparator<Supply> comparator;

    TileSupplyStrategy(Comparator<Supply> comparator) {
        this.comparator = comparator;
    }

    /**
     * Compare two supplies according to the strategy.
     * Returns
     *   -1 (a < b)
     *    0 (a == b)
     *    1 (a > b)
     */
    public int compare(Supply a, Supply b) {
        return comparator.compare(a, b);
    }
}
