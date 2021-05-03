package com.tsoft.civilization.world.scenario;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.city.CivilizationCityService;
import com.tsoft.civilization.civilization.unit.CivilizationUnitService;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.unit.civil.settlers.Settlers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.BARBARIANS;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class DefaultScenarioTest {

    @Test
    public void default_scenario() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization barbarians = world.createCivilization(BARBARIANS);
        Civilization russia = world.createCivilization(RUSSIA);

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
