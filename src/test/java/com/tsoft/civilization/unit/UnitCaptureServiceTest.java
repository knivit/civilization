package com.tsoft.civilization.unit;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.render.WorldRender;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.action.CaptureUnitAction.FOREIGN_UNIT_CAPTURED;
import static org.assertj.core.api.Assertions.assertThat;

public class UnitCaptureServiceTest {

    private static final UnitCaptureService unitCaptureService = new UnitCaptureService();

    @Test
    public void targets_for_melee_unit_to_capture() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 3 ",
            "-+--------",
            "0|. g g . ",
            "1| . g g .",
            "2|. . g . ",
            "3| . . . .");
        MockWorld world = MockWorld.of(map);

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(1, 0))
        );

        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(1, 1))
        );

        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        WorldRender.of(this).createHtml(world, russia);

        // see what we can capture
        List<Point> locations = unitCaptureService.getLocationsToCapture(world.unit("warriors"));
        assertThat(locations)
            .isNotNull()
            .hasSize(1);

        assertThat(unitCaptureService.getTargetToCaptureAtLocation(world.unit("warriors"), locations.get(0)))
            .isEqualTo(world.unit("foreignWorkers"));

        // capture the foreign workers and move on it's tile
//        assertThat(unitCaptureService.capture(world.unit("warriors"), world.location("foreignWorkers")))
  //          .isEqualTo(FOREIGN_UNIT_CAPTURED);

        assertThat(world.unit("warriors"))
            .returns(0, AbstractUnit::getPassScore)
            .returns(new Point(1, 1), AbstractUnit::getLocation);

        assertThat(world.unit("foreignWorkers"))
            .returns(world.unit("warriors").getLocation(), AbstractUnit::getLocation)
            .returns(world.unit("warriors").getCivilization(), AbstractUnit::getCivilization)
            .returns(0, AbstractUnit::getPassScore);
    }

    @Test
    public void targets_for_ranged_unit_to_capture() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 2,
            " |0 1 2 3 4 5 6 ", " |0 1 2 3 4 5 6 ",
            "-+--------------", "-+--------------",
            "0|. . g g . . . ", "0|. . M M . . . ",
            "1| . . g g . . .", "1| . . . j . . .",
            "2|. . g . g . . ", "2|. . f . . . . ",
            "3| . g g g . . .", "3| . . . h . . .");
        MockWorld world = MockWorld.of(map);

        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .archers("archers", new Point(2, 1))
        );

        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(3, 1))
        );

        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        // see what we can capture
        List<Point> locations = unitCaptureService.getLocationsToCapture(world.unit("archers"));
        assertThat(locations).hasSize(1);
        assertThat(unitCaptureService.getTargetToCaptureAtLocation(world.unit("archers"), locations.get(0)))
            .isEqualTo(world.unit("foreignWorkers"));

        // capture the foreign workers
        //assertThat(unitCaptureService.capture(world.unit("archers"), world.unit("foreignWorkers").getLocation()))
//            .isEqualTo(FOREIGN_UNIT_CAPTURED);

        assertThat(world.unit("foreignWorkers"))
            .returns(world.unit("archers").getLocation(), e -> e.getLocation())
            .returns(russia, e -> e.getCivilization());
    }
}
