package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.unit.UnitCaptureService;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.action.AttackAction.INVALID_LOCATION;
import static com.tsoft.civilization.unit.action.CaptureUnitAction.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CaptureUnitActionTest {

    private final MockWorld world = MockWorld.newSimpleWorld();

    @Test
    public void attacker_not_found() {
        UnitCaptureService unitCaptureService = mock(UnitCaptureService.class);
        CaptureUnitAction captureUnitAction = new CaptureUnitAction(unitCaptureService);

        assertThat(captureUnitAction.capture(null, null))
            .isEqualTo(ATTACKER_NOT_FOUND);
    }

    @Test
    public void no_locations_to_capture() {
        UnitCaptureService unitCaptureService = mock(UnitCaptureService.class);
        CaptureUnitAction captureUnitAction = new CaptureUnitAction(unitCaptureService);

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        Warriors warriors = (Warriors) world.unit("warriors");

        assertThat(captureUnitAction.capture(world.unit("warriors"), null))
            .isEqualTo(NO_LOCATIONS_TO_CAPTURE);
    }

    @Test
    public void invalid_location() {
        UnitCaptureService unitCaptureService = mock(UnitCaptureService.class);
        CaptureUnitAction captureUnitAction = new CaptureUnitAction(unitCaptureService);

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        Warriors warriors = (Warriors) world.unit("warriors");

        world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(2, 2)));
        Workers foreignWorkers = (Workers) world.unit("foreignWorkers");

        assertThat(captureUnitAction.capture(warriors, null))
            .isEqualTo(INVALID_LOCATION);
    }

    @Test
    public void nothing_to_capture() {
        UnitCaptureService unitCaptureService = mock(UnitCaptureService.class);
        CaptureUnitAction captureUnitAction = new CaptureUnitAction(unitCaptureService);

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        Warriors warriors = (Warriors) world.unit("warriors");

        world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(2, 2)));
        Workers foreignWorkers = (Workers) world.unit("foreignWorkers");

        when(unitCaptureService.getLocationsToCapture(eq(warriors))).thenReturn(Collections.emptyList());

        assertThat(captureUnitAction.capture(warriors, null))
            .isEqualTo(NOTHING_TO_CAPTURE);
    }
}
