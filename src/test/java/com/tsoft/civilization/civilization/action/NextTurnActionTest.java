package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.L10n.L10nCivilization.RUSSIA;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NextTurnActionTest {

    @Test
    public void canNextTurn() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization civilization = world.createCivilization(RUSSIA);

        assertEquals(NextTurnActionResults.CAN_NEXT_TURN, NextTurnAction.nextTurn(world));
    }
}
