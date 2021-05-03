package com.tsoft.civilization.improvement.city.supply;

import com.tsoft.civilization.economic.Supply;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TileSupplyStrategyTest {

    @Test
    public void maxFoodStrategy() {
        Supply a = Supply.builder().food(-1).build();
        Supply b = Supply.builder().food(0).build();
        assertThat(TileSupplyStrategy.MAX_FOOD.compare(a, b)).isEqualTo(-1);
        assertThat(TileSupplyStrategy.MAX_FOOD.compare(b, a)).isEqualTo(1);

        a = Supply.builder().food(1).build();
        b = Supply.builder().food(0).build();
        assertThat(TileSupplyStrategy.MAX_FOOD.compare(a, b)).isEqualTo(1);
        assertThat(TileSupplyStrategy.MAX_FOOD.compare(b, a)).isEqualTo(-1);

        a = Supply.builder().food(0).build();
        b = Supply.builder().food(0).build();
        assertThat(TileSupplyStrategy.MAX_FOOD.compare(a, b)).isEqualTo(0);
        assertThat(TileSupplyStrategy.MAX_FOOD.compare(b, a)).isEqualTo(0);
    }
}
