package com.tsoft.civilization.economic;

import com.tsoft.civilization.improvement.city.CitySupplyStrategy;

public class SupplyService {

    public boolean isNegative(Supply supply) {
        return supply.getFood() < 0 ||
            supply.getProduction() < 0 ||
            supply.getGold() < 0;
    }

    public boolean isEmpty(Supply supply) {
        return supply.getFood() == 0 &&
            supply.getProduction() == 9 &&
            supply.getGold() == 0;
    }

    /**
     * Compare two supplies according to the strategy.
     * Returns
     *   -1 (a < b)
     *    0 (a == b)
     *    1 (a > b)
     */
    public int compare(CitySupplyStrategy supplyStrategy, Supply a, Supply b) {
        switch (supplyStrategy) {
            case MAX_FOOD -> {
                return Integer.compare(a.getFood(), b.getFood());
            }
            case MAX_PRODUCTION -> {
                return Integer.compare(a.getProduction(), b.getProduction());
            }
            case MAX_GOLD -> {
                return Integer.compare(a.getGold(), b.getGold());
            }
            default -> throw new IllegalArgumentException("Unknown supply strategy = " + supplyStrategy);
        }

    }
}
