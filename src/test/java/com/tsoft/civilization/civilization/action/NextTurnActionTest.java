package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.MoveState;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.civilization.action.NextTurnAction.MOVE_DONE;
import static org.assertj.core.api.Assertions.assertThat;

public class NextTurnActionTest {

    @Test
    public void nextTurn() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization russia = world.createCivilization(RUSSIA, new MockScenario());

        world.startGame();
        int startYear = world.getYear().getYear();

        assertThat(russia.getMoveState())
            .isNotEqualTo(MoveState.DONE);

        assertThat(NextTurnAction.nextTurn(russia))
            .isEqualTo(MOVE_DONE);

        assertThat(world.getYear().getYear())
            .isNotEqualTo(startYear);
    }
}
