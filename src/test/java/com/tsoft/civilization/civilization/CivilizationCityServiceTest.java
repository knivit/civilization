package com.tsoft.civilization.civilization;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.city.CivilizationCityService;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class CivilizationCityServiceTest {

    @Test
    public void no_cities_with_available_actions() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .settlers("settlers", new Point(2, 0))
        );

        CivilizationCityService cityService = new CivilizationCityService(russia);

        // no cities
        assertThat(cityService.getCitiesWithAvailableActions()).isEmpty();

        // add a city
        russia.createCity((Settlers) world.unit("settlers"));

        // a new city can't do anything in this move
        assertThat(cityService.getCitiesWithAvailableActions()).isEmpty();

        // add a foreign civilization
        Civilization america = world.createCivilization(AMERICA);

        assertThat(world.getCivilizationsRelations(russia, america))
            .isNotNull()
            .returns(true, CivilizationsRelations::isNeutral);
    }
}
