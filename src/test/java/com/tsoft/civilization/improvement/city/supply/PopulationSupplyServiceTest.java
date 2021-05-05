package com.tsoft.civilization.improvement.city.supply;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class PopulationSupplyServiceTest {

    @Test
    public void find_location_for_citizen_with_max_food_strategy() {
        MockWorld world = MockWorld.of(new MockTilesMap(
            " |0 1 2 3 ",
            "-+--------",
            "0|d g i l ",
            "1| p s t a",
            "2|n f h j ",
            "3| m o , ."));

        world.createCivilization(RUSSIA, new MockScenario()
            .city("city", new Point(2, 0))
        );

        PopulationSupplyService supplyService = new PopulationSupplyService(world.city("city"));
        supplyService.setSupplyStrategy(TileSupplyStrategy.MAX_FOOD);

        assertThat(supplyService.findLocationForCitizen(Collections.emptyList()))
            .isEqualTo(new Point(2, 0));
    }

    @Test
    public void find_location_for_citizen_with_max_production_strategy() {
        MockWorld world = MockWorld.of(new MockTilesMap(
            " |0 1 2 3 ",
            "-+--------",
            "0|d g i l ",
            "1| p s t a",
            "2|n f h j ",
            "3| m o , ."));

        world.createCivilization(RUSSIA, new MockScenario()
            .city("city", new Point(2, 0))
        );

        PopulationSupplyService supplyService = new PopulationSupplyService(world.city("city"));
        supplyService.setSupplyStrategy(TileSupplyStrategy.MAX_PRODUCTION);

        assertThat(supplyService.findLocationForCitizen(Collections.emptyList()))
            .isEqualTo(new Point(2, 0));
    }
}
