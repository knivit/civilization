package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.CivilizationsRelations.NEUTRAL;
import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.civilization.action.DeclareWarAction.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DeclareWarActionTest {

    @Test
    public void declare_war() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization civ1 = world.createCivilization(RUSSIA);
        Civilization civ2 = world.createCivilization(AMERICA);

        assertThat(world.getCivilizationsRelations(civ1, civ2))
            .isEqualTo(NEUTRAL);

        assertThat(DeclareWarAction.declareWar(civ1, civ2))
            .isEqualTo(CAN_DECLARE_WAR);
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
        world.setCivilizationsRelations(civ1, civ2, CivilizationsRelations.WAR);

        assertThat(DeclareWarAction.declareWar(civ1, civ2))
            .isEqualTo(ALREADY_WAR);
    }
}
