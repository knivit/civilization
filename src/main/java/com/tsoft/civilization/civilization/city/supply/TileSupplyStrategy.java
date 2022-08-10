package com.tsoft.civilization.civilization.city.supply;

import com.tsoft.civilization.economic.Supply;

import java.util.Comparator;

public enum TileSupplyStrategy {

    MAX_FOOD(Comparator.comparingInt(Supply::getFood)),
    MAX_PRODUCTION(Comparator.comparingInt(Supply::getProduction)),
    MAX_GOLD(Comparator.comparingInt(Supply::getGold)),
    MAX_SCIENCE(Comparator.comparingInt(Supply::getScience)),
    MAX_CULTURE(Comparator.comparingInt(Supply::getCulture)),
    MAX_FAITH(Comparator.comparingInt(Supply::getFaith)),
    MAX_GREAT_PERSON(Comparator.comparingInt(Supply::getGreatPerson));

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
