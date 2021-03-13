package com.tsoft.civilization.civilization.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.civilization.action.NextTurnAction.CAN_NEXT_TURN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class NextTurnActionTest {

    @Test
    public void canNextTurn() {
        MockWorld world = MockWorld.newSimpleWorld();
        Civilization civilization = world.createCivilization(RUSSIA);

        assertEquals(CAN_NEXT_TURN, NextTurnAction.nextTurn(world));
    }
}
