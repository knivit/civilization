package com.tsoft.civilization.world.scenario;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.city.CivilizationCityService;
import com.tsoft.civilization.civilization.unit.CivilizationUnitService;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.unit.catalog.settlers.Settlers;
import com.tsoft.civilization.unit.catalog.warriors.Warriors;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.MockWorld.GRASSLAND_MAP_10x10;
import static com.tsoft.civilization.civilization.L10nCivilization.BARBARIANS;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class DefaultScenarioTest {

    @Test
    public void default_scenario() {
        MockWorld world = MockWorld.of(GRASSLAND_MAP_10x10);

        Civilization barbarians = world.createCivilization(BARBARIANS, new DefaultScenario());
        Civilization russia = world.createCivilization(RUSSIA, new DefaultScenario());

        assertThat(russia.getUnitService())
            .returns(2, CivilizationUnitService::size)
            .returns(1, e -> e.findByClassUuid(Settlers.CLASS_UUID).size())
            .returns(1, e -> e.findByClassUuid(Warriors.CLASS_UUID).size());

        assertThat(russia.getCityService())
            .isNotNull()
            .returns(0, CivilizationCityService::size);

        assertThat(world.getCivilizationsRelations(russia, barbarians))
            .isNotNull()
            .returns(true, CivilizationsRelations::isWar);
    }
}
