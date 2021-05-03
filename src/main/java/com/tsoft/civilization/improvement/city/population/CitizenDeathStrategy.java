package com.tsoft.civilization.improvement.city.population;

import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.improvement.city.supply.CitySupplyService;
import com.tsoft.civilization.unit.civil.citizen.Citizen;
import com.tsoft.civilization.unit.civil.citizen.CitizenList;

import java.util.Comparator;
import java.util.function.BiFunction;

public enum CitizenDeathStrategy {

    MIN_FOOD(CitizenDeathStrategy::minFood),
    MIN_PRODUCTION(CitizenDeathStrategy::minProduction),
    MIN_GOLD(CitizenDeathStrategy::minGold);

    private final BiFunction<CitizenList, CitySupplyService, Citizen> strategy;

    CitizenDeathStrategy(BiFunction<CitizenList, CitySupplyService, Citizen> strategy) {
        this.strategy = strategy;
    }

    public Citizen findStarvationVictims(CitizenList citizens, CitySupplyService supplyService) {
        return strategy.apply(citizens, supplyService);
    }

    // A citizen with minimal food produce will die
    private static Citizen minFood(CitizenList citizens, CitySupplyService supplyService) {
        return min(citizens, supplyService, Comparator.comparingInt(Supply::getFood));
    }

    private static Citizen minProduction(CitizenList citizens, CitySupplyService supplyService) {
        return min(citizens, supplyService, Comparator.comparingInt(Supply::getProduction));
    }

    private static Citizen minGold(CitizenList citizens, CitySupplyService supplyService) {
        return min(citizens, supplyService, Comparator.comparingInt(Supply::getGold));
    }

    private static Citizen min(CitizenList citizens, CitySupplyService supplyService, Comparator<Supply> comparator) {
        return citizens.stream()
            .min((a, b) -> {
                Supply asIncome = a.calcIncomeSupply();
                Supply asOutcome = a.calcOutcomeSupply();
                Supply asTileIncome = supplyService.calcTilesSupply(a.getLocation());
                Supply as = asIncome.add(asOutcome).add(asTileIncome);

                Supply bsIncome = b.calcIncomeSupply();
                Supply bsOutcome = b.calcOutcomeSupply();
                Supply bsTileIncome = supplyService.calcTilesSupply(b.getLocation());
                Supply bs = bsIncome.add(bsOutcome).add(bsTileIncome);

                return comparator.compare(as, bs);
            })
            .orElse(null);
    }
}
