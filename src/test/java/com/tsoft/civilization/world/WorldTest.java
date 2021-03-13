package com.tsoft.civilization.world;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static org.assertj.core.api.Assertions.assertThat;

public class WorldTest {

    @Test
    public void can_not_create_one_civilization_twice() {
        World world = MockWorld.newSimpleWorld();

        assertThat(world.createCivilization(RUSSIA))
            .isNotNull()
            .returns(RUSSIA, Civilization::getName);

        assertThat(world.createCivilization(RUSSIA))
            .isNull();
    }

    @Test
    public void create_two_civilizations() {
        World world = MockWorld.newSimpleWorld();

        assertThat(world.createCivilization(RUSSIA))
            .isNotNull()
            .returns(RUSSIA, Civilization::getName);

        assertThat(world.createCivilization(AMERICA))
            .isNotNull()
            .returns(AMERICA, Civilization::getName);
    }
}
