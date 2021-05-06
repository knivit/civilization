package com.tsoft.civilization.combat.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.combat.service.CaptureUnitService;
import com.tsoft.civilization.combat.action.CaptureUnitAction;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.util.Point;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.combat.service.CaptureUnitService.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CaptureUnitActionTest {

    private final MockWorld world = MockWorld.newSimpleWorld();

    @Test
    public void attacker_not_found() {
        CaptureUnitService captureService = mock(CaptureUnitService.class);
        CaptureUnitAction captureUnitAction = new CaptureUnitAction(captureService);

        assertThat(captureUnitAction.capture(null, null))
            .isEqualTo(ATTACKER_NOT_FOUND);
    }

    @Test
    public void no_locations_to_capture() {
        CaptureUnitService captureService = mock(CaptureUnitService.class);
        CaptureUnitAction captureUnitAction = new CaptureUnitAction(captureService);

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        Warriors warriors = (Warriors) world.unit("warriors");

        assertThat(captureUnitAction.capture(world.unit("warriors"), null))
            .isEqualTo(NO_LOCATIONS_TO_CAPTURE);
    }

    @Test
    public void invalid_location() {
        CaptureUnitService captureService = mock(CaptureUnitService.class);
        CaptureUnitAction captureUnitAction = new CaptureUnitAction(captureService);

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
        CaptureUnitService captureService = mock(CaptureUnitService.class);
        CaptureUnitAction captureUnitAction = new CaptureUnitAction(captureService);

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        Warriors warriors = (Warriors) world.unit("warriors");

        world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(2, 2)));
        Workers foreignWorkers = (Workers) world.unit("foreignWorkers");

        when(captureService.getLocationsToCapture(eq(warriors))).thenReturn(Collections.emptyList());

        assertThat(captureUnitAction.capture(warriors, null))
            .isEqualTo(NOTHING_TO_CAPTURE);
    }
}
