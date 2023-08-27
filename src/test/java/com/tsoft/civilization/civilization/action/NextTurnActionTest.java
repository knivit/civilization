package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationMoveState;
import com.tsoft.civilization.world.Year;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.civilization.action.NextTurnAction.MOVE_DONE;
import static org.assertj.core.api.Assertions.assertThat;

public class NextTurnActionTest {

    private static final NextTurnAction NEXT_TURN_ACTION = new NextTurnAction();

    @Test
    public void nextTurn() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario());

        Year startYear = world.getYear();

        assertThat(russia.getCivilizationMoveState()).isNotEqualTo(CivilizationMoveState.DONE);
        assertThat(NEXT_TURN_ACTION.nextTurn(russia)).isEqualTo(MOVE_DONE);
        assertThat(world.getYear()).isNotEqualTo(startYear);
    }
}
