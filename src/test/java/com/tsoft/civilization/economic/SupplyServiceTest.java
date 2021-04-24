package com.tsoft.civilization.economic;

import com.tsoft.civilization.improvement.city.CitySupplyStrategy;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupplyServiceTest {

    private final SupplyService supplyService = new SupplyService();

    @Test
    public void maxFoodStrategy() {
        Supply a = Supply.builder().food(-1).build();
        Supply b = Supply.builder().food(0).build();
        assertEquals(-1, supplyService.compare(CitySupplyStrategy.MAX_FOOD, a, b));
        assertEquals(1, supplyService.compare(CitySupplyStrategy.MAX_FOOD, b, a));

        a = Supply.builder().food(1).build();
        b = Supply.builder().food(0).build();
        assertEquals(1, supplyService.compare(CitySupplyStrategy.MAX_FOOD, a, b));
        assertEquals(-1, supplyService.compare(CitySupplyStrategy.MAX_FOOD, b, a));

        a = Supply.builder().food(0).build();
        b = Supply.builder().food(0).build();
        assertEquals(0, supplyService.compare(CitySupplyStrategy.MAX_FOOD, a, b));
        assertEquals(0, supplyService.compare(CitySupplyStrategy.MAX_FOOD, b, a));
    }
}
