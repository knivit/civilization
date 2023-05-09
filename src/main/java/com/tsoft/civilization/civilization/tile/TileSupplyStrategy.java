package com.tsoft.civilization.civilization.tile;

import com.tsoft.civilization.economic.Supply;

import java.util.Comparator;

public enum TileSupplyStrategy {

    MAX_FOOD(Comparator.comparingDouble(Supply::getFood)),
    MAX_PRODUCTION(Comparator.comparingDouble(Supply::getProduction)),
    MAX_GOLD(Comparator.comparingDouble(Supply::getGold)),
    MAX_SCIENCE(Comparator.comparingDouble(Supply::getScience)),
    MAX_CULTURE(Comparator.comparingDouble(Supply::getCulture)),
    MAX_FAITH(Comparator.comparingDouble(Supply::getFaith)),
    MAX_GREAT_PERSON(Comparator.comparingDouble(Supply::getGreatPerson));

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
