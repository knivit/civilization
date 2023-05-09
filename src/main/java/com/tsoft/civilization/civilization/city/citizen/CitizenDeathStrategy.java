package com.tsoft.civilization.civilization.city.citizen;

import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.economic.Supply;
import com.tsoft.civilization.civilization.city.supply.CitySupplyService;

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
        Civilization civilization = supplyService.getCity().getCivilization();
        return min(civilization, citizens, supplyService, Comparator.comparingDouble(Supply::getFood));
    }

    private static Citizen minProduction(CitizenList citizens, CitySupplyService supplyService) {
        Civilization civilization = supplyService.getCity().getCivilization();
        return min(civilization, citizens, supplyService, Comparator.comparingDouble(Supply::getProduction));
    }

    private static Citizen minGold(CitizenList citizens, CitySupplyService supplyService) {
        Civilization civilization = supplyService.getCity().getCivilization();
        return min(civilization, citizens, supplyService, Comparator.comparingDouble(Supply::getGold));
    }

    private static Citizen min(Civilization civilization, CitizenList citizens, CitySupplyService supplyService, Comparator<Supply> comparator) {
        return citizens.stream()
            .min((a, b) -> {
                Supply asIncome = a.calcIncomeSupply(civilization);
                Supply asOutcome = a.calcOutcomeSupply(civilization);
                Supply asTileIncome = supplyService.calcTilesSupply(a.getLocation());
                Supply as = asIncome.add(asOutcome).add(asTileIncome);

                Supply bsIncome = b.calcIncomeSupply(civilization);
                Supply bsOutcome = b.calcOutcomeSupply(civilization);
                Supply bsTileIncome = supplyService.calcTilesSupply(b.getLocation());
                Supply bs = bsIncome.add(bsOutcome).add(bsTileIncome);

                return comparator.compare(as, bs);
            })
            .orElse(null);
    }
}
