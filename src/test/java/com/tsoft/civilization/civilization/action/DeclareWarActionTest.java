package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.MockWorld.GRASSLAND_MAP_10x10;
import static com.tsoft.civilization.civilization.L10nCivilization.*;
import static com.tsoft.civilization.civilization.action.DeclareWarAction.CAN_DECLARE_WAR;
import static com.tsoft.civilization.civilization.action.DeclareWarAction.WRONG_CIVILIZATION;
import static com.tsoft.civilization.civilization.action.DeclareWarAction.ALREADY_WAR;
import static org.assertj.core.api.Assertions.assertThat;

public class DeclareWarActionTest {

    @Test
    public void declare_war() {
        MockWorld world = MockWorld.of(GRASSLAND_MAP_10x10);

        Civilization russia = world.createCivilization(RUSSIA);
        Civilization america = world.createCivilization(AMERICA);
        Civilization japan = world.createCivilization(JAPAN);

        world.startGame();

        assertThat(world.getCivilizationsRelations(russia, america)).isEqualTo(CivilizationsRelations.neutral());
        assertThat(world.getCivilizationsRelations(russia, japan)).isEqualTo(CivilizationsRelations.neutral());
        assertThat(world.getCivilizationsRelations(america, japan)).isEqualTo(CivilizationsRelations.neutral());

        assertThat(DeclareWarAction.declareWar(russia, america)).isEqualTo(CAN_DECLARE_WAR);
        assertThat(world.getCivilizationsRelations(russia, america)).isEqualTo(CivilizationsRelations.war());
        assertThat(world.getCivilizationsRelations(russia, japan)).isEqualTo(CivilizationsRelations.neutral());
        assertThat(world.getCivilizationsRelations(america, japan)).isEqualTo(CivilizationsRelations.neutral());
    }

    @Test
    public void declare_war_fails_for_same_civilizations() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario());

        world.startGame();

        assertThat(DeclareWarAction.declareWar(russia, russia))
            .isEqualTo(WRONG_CIVILIZATION);
    }

    @Test
    public void declare_war_fails_for_civilizations_at_war() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario());
        Civilization america = world.createCivilization(AMERICA, new MockScenario());

        world.startGame();
        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        assertThat(DeclareWarAction.declareWar(russia, america))
            .isEqualTo(ALREADY_WAR);
    }
}
