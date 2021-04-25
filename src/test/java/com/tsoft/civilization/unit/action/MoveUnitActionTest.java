package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.unit.UnitMoveService;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.unit.action.MoveUnitAction.INVALID_LOCATION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MoveUnitActionTest {

    @Test
    public void invalid_location() {
        UnitMoveService unitMoveService = mock(UnitMoveService.class);
        MoveUnitAction moveUnitAction = new MoveUnitAction(unitMoveService);

        assertThat(moveUnitAction.move(null, null))
            .isEqualTo(INVALID_LOCATION);
    }

}
