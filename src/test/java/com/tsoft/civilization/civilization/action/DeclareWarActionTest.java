package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.*;
import static com.tsoft.civilization.civilization.action.DeclareWarAction.CAN_DECLARE_WAR;
import static com.tsoft.civilization.civilization.action.DeclareWarAction.WRONG_CIVILIZATION;
import static com.tsoft.civilization.civilization.action.DeclareWarAction.ALREADY_WAR;
import static org.assertj.core.api.Assertions.assertThat;

public class DeclareWarActionTest {

    @Test
    public void declare_war() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization civ1 = world.createCivilization(RUSSIA);
        Civilization civ2 = world.createCivilization(AMERICA);
        Civilization civ3 = world.createCivilization(JAPAN);

        assertThat(world.getCivilizationsRelations(civ1, civ2)).isEqualTo(CivilizationsRelations.neutral());
        assertThat(world.getCivilizationsRelations(civ1, civ3)).isEqualTo(CivilizationsRelations.neutral());
        assertThat(world.getCivilizationsRelations(civ2, civ3)).isEqualTo(CivilizationsRelations.neutral());

        assertThat(DeclareWarAction.declareWar(civ1, civ2)).isEqualTo(CAN_DECLARE_WAR);
        assertThat(world.getCivilizationsRelations(civ1, civ2)).isEqualTo(CivilizationsRelations.war());
        assertThat(world.getCivilizationsRelations(civ1, civ3)).isEqualTo(CivilizationsRelations.neutral());
        assertThat(world.getCivilizationsRelations(civ2, civ3)).isEqualTo(CivilizationsRelations.neutral());
    }

    @Test
    public void declare_war_fails_for_same_civilizations() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization civ1 = world.createCivilization(RUSSIA);
        Civilization civ2 = world.createCivilization(RUSSIA);

        assertThat(DeclareWarAction.declareWar(civ1, civ2))
            .isEqualTo(WRONG_CIVILIZATION);
    }

    @Test
    public void declare_war_fails_for_civilizations_at_war() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization civ1 = world.createCivilization(RUSSIA);
        Civilization civ2 = world.createCivilization(AMERICA);
        world.setCivilizationsRelations(civ1, civ2, CivilizationsRelations.war());

        assertThat(DeclareWarAction.declareWar(civ1, civ2))
            .isEqualTo(ALREADY_WAR);
    }
}
