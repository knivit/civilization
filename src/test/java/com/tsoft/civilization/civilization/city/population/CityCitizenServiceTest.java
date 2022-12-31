package com.tsoft.civilization.civilization.city.population;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.civilization.city.citizen.CityCitizenService;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class CityCitizenServiceTest {

    @Test
    public void add_citizen() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 3 ",
            "-+--------",
            "0|. . g . ",
            "1| . p g .",
            "2|. . g . ",
            "3| . . . ."));

        world.createCivilization(RUSSIA, new MockScenario()
            .city("city", new Point(2, 0))
        );

        CityCitizenService populationService = world.city("city").getPopulationService();
        assertThat(populationService.getCitizenCount()).isEqualTo(1);

        populationService.addCitizen();

        assertThat(populationService.getCitizenCount()).isEqualTo(2);
    }
}
