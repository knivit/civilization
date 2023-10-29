package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.render.WorldRender;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.combat.service.CaptureUnitService.*;
import static com.tsoft.civilization.combat.service.CaptureUnitService.NOTHING_TO_CAPTURE;
import static org.assertj.core.api.Assertions.assertThat;

public class CaptureUnitServiceTest {

    private static final CaptureUnitService captureUnitService = new CaptureUnitService();

    @Test
    public void targets_for_melee_unit_to_capture() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 3 ",
            "-+--------",
            "0|. g g . ",
            "1| . g g .",
            "2|. . g . ",
            "3| . . . ."));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(1, 0))
        );

        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(1, 1))
        );

        WorldRender.of(this).createHtml(world, russia);

        // see what we can capture
        List<Point> locations = captureUnitService.getLocationsToCapture(world.get("warriors"));
        assertThat(locations)
            .isNotNull()
            .hasSize(1);

        assertThat(captureUnitService.getTargetToCaptureAtLocation(world.get("warriors"), locations.get(0)))
            .isEqualTo(world.unit("foreignWorkers"));

        // capture the foreign workers and move on it's tile
        assertThat(captureUnitService.capture(world.get("warriors"), world.location("foreignWorkers")))
            .isEqualTo(UNIT_CAPTURED);

        assertThat(world.unit("warriors"))
            .returns(0, AbstractUnit::getPassScore)
            .returns(new Point(1, 1), AbstractUnit::getLocation);

        assertThat(world.unit("foreignWorkers"))
            .returns(world.unit("warriors").getLocation(), AbstractUnit::getLocation)
            .returns(world.unit("warriors").getCivilization(), AbstractUnit::getCivilization)
            .returns(0, AbstractUnit::getPassScore);

        assertThat(world.getCivilizationsRelations(russia, america))
            .isEqualTo(CivilizationsRelations.war());
    }

    @Test
    public void targets_for_ranged_unit_to_capture() {
        MockWorld world = MockWorld.of(MockTilesMap.of(2,
            " |0 1 2 3 4 5 6 ", " |0 1 2 3 4 5 6 ",
            "-+--------------", "-+--------------",
            "0|. . g g . . . ", "0|. . M M . . . ",
            "1| . . g g . . .", "1| . . . j . . .",
            "2|. . g . g . . ", "2|. . f . . . . ",
            "3| . g g g . . .", "3| . . . h . . ."));

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .archers("archers", new Point(2, 1))
        );

        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(3, 1))
        );

        // see what we can capture
        List<Point> locations = captureUnitService.getLocationsToCapture(world.get("archers"));
        assertThat(locations).hasSize(1);
        assertThat(captureUnitService.getTargetToCaptureAtLocation(world.get("archers"), locations.get(0)))
            .isEqualTo(world.unit("foreignWorkers"));

        // capture the foreign workers
        assertThat(captureUnitService.capture(world.unit("archers"), world.unit("foreignWorkers").getLocation()))
            .isEqualTo(UNIT_CAPTURED);

        assertThat(world.unit("foreignWorkers"))
            .returns(world.unit("archers").getLocation(), AbstractUnit::getLocation)
            .returns(russia, AbstractUnit::getCivilization);

        assertThat(world.getCivilizationsRelations(russia, america))
            .isEqualTo(CivilizationsRelations.war());
    }

    @Test
    public void attacker_not_found() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(1, 1)));
        AbstractUnit warriors = world.get("warriors");
        warriors.destroy();

        world.createCivilization(AMERICA, new MockScenario()
            .workers("workers", new Point(2, 1)));
        AbstractUnit workers = world.get("workers");

        assertThat(captureUnitService.capture(warriors, workers.getLocation()))
            .isEqualTo(ATTACKER_NOT_FOUND);
    }

    @Test
    public void nothing_to_capture() {
        MockWorld world = MockWorld.newSimpleWorld();

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        AbstractUnit warriors = world.get("warriors");

        world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(2, 2)));
        AbstractUnit foreignWorkers = world.get("foreignWorkers");

        WorldRender.of(this).createHtml(world, russia);

        Point location = new Point(foreignWorkers.getLocation());

        assertThat(captureUnitService.capture(warriors, location))
            .isEqualTo(NOTHING_TO_CAPTURE);
    }

    @Test
    public void nothing_to_capture_at_location() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        AbstractUnit warriors = world.get("warriors");

        world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(2, 1)));

        assertThat(captureUnitService.capture(warriors, new Point(2, 2)))
            .isEqualTo(NOTHING_TO_CAPTURE);
    }
}
