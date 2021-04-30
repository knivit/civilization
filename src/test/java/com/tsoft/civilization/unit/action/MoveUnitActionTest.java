package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.unit.UnitMoveService;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.action.MoveUnitAction.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class MoveUnitActionTest {

    private final MockWorld world = MockWorld.newSimpleWorld();

    @Test
    public void invalid_location() {
        UnitMoveService unitMoveService = mock(UnitMoveService.class);
        MoveUnitAction moveUnitAction = new MoveUnitAction(unitMoveService);

        assertThat(moveUnitAction.move(null, null))
            .isEqualTo(INVALID_LOCATION);
    }

    @Test
    public void unit_not_found() {
        UnitMoveService unitMoveService = mock(UnitMoveService.class);
        MoveUnitAction moveUnitAction = new MoveUnitAction(unitMoveService);

        assertThat(moveUnitAction.move(null, new Point(1, 1)))
            .isEqualTo(UNIT_NOT_FOUND);
    }

    @Test
    public void invalid_destroyed() {
        UnitMoveService unitMoveService = mock(UnitMoveService.class);
        MoveUnitAction moveUnitAction = new MoveUnitAction(unitMoveService);

        Civilization russia = world.createCivilization(RUSSIA);
        Warriors warriors = UnitFactory.newInstance(russia, Warriors.CLASS_UUID);
        warriors.destroy();

        assertThat(moveUnitAction.move(warriors, new Point(1, 1)))
            .isEqualTo(UNIT_DESTROYED);
    }

    @Test
    public void invalid_unit_location() {
        UnitMoveService unitMoveService = mock(UnitMoveService.class);
        MoveUnitAction moveUnitAction = new MoveUnitAction(unitMoveService);

        Civilization russia = world.createCivilization(RUSSIA);
        Workers workers = UnitFactory.newInstance(russia, Workers.CLASS_UUID);

        assertThat(moveUnitAction.move(workers, new Point(1, 1)))
            .isEqualTo(INVALID_UNIT_LOCATION);
    }

    @Test
    public void no_locations_to_move() {
        MockWorld world = MockWorld.of(new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 3 ",
            "-+--------",
            "0|g . . g ",
            "1| . g . .",
            "2|g . . g ",
            "3| . g . ."));

        UnitMoveService unitMoveService = mock(UnitMoveService.class);
        MoveUnitAction moveUnitAction = new MoveUnitAction(unitMoveService);

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .workers("workers", new Point(0, 0))
        );

        assertThat(moveUnitAction.move(world.unit("workers"), new Point(1, 1)))
            .isEqualTo(NO_LOCATIONS_TO_MOVE);
    }
}
