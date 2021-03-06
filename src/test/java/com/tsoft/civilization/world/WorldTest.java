package com.tsoft.civilization.world;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class WorldTest {

    @Test
    public void can_not_create_one_civilization_twice() {
        MockWorld world = MockWorld.newSimpleWorld();

        assertThat(world.createCivilization(RUSSIA, new MockScenario()))
            .isNotNull()
            .returns(RUSSIA, Civilization::getName);

        assertThat(world.createCivilization(RUSSIA, new MockScenario()))
            .isNull();
    }

    @Test
    public void create_two_civilizations() {
        MockWorld world = MockWorld.newSimpleWorld();

        assertThat(world.createCivilization(RUSSIA, new MockScenario()))
            .isNotNull()
            .returns(RUSSIA, Civilization::getName);

        assertThat(world.createCivilization(AMERICA, new MockScenario()))
            .isNotNull()
            .returns(AMERICA, Civilization::getName);
    }
}
