package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.web.render.WorldRender;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.unit.action.AttackAction.ATTACKED;
import static com.tsoft.civilization.unit.action.AttackAction.TARGET_DESTROYED;
import static com.tsoft.civilization.unit.action.CaptureUnitAction.FOREIGN_UNIT_CAPTURED;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AttackActionTest {

    @Test
    public void targets_for_melee_attack_and_capture() {
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
            .warriors("foreignWarriors", new Point(2, 1))
        );

        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        WorldRender.of(this).createHtml(world, russia);

        // first, there is foreign workers to attack
        HasCombatStrengthList targets = AttackAction.getTargetsToAttack(world.unit("warriors"));
        assertThat(targets)
            .hasSize(1)
            .containsExactly(world.unit("foreignWorkers"));

        // move close the foreign warriors and now they are the target too
        world.unit("foreignWarriors").setLocation(new Point(2, 0));
        targets = AttackAction.getTargetsToAttack(world.unit("warriors"));

        assertThat(targets)
            .hasSize(2)
            .containsExactly(world.unit("foreignWorkers"), world.unit("foreignWarriors"));

        // attack one of them
        assertThat(AttackAction.attack(world.unit("warriors"), world.location("foreignWarriors")))
            .isEqualTo(ATTACKED);
    }

    // Scenario:
    // An archer can fire through an ocean tile but can't through a mountain
    @Test
    public void targets_for_ranged_attack_and_capture() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES, 2,
            " |0 1 2 3 4 5 6 ", " |0 1 2 3 4 5 6 ",
            "-+--------------", "-+--------------",
            "0|. . g g . . . ", "0|. . M M . . . ",
            "1| . . g g . . .", "1| . . . j . . .",
            "2|. . g . g . . ", "2|. . f . . . . ",
            "3| . g g g . . .", "3| . . . h . . .");
        MockWorld world = MockWorld.of(map);

        // our forces
        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .archers("archers", new Point(2, 1))
        );

        // foreign forces
        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .city("foreignCity", new Point(2, 3))
            .workers("foreignWorkers", new Point(3, 1))
            .warriors("foreignWarriors1", new Point(2, 2))
            .warriors("foreignWarriors2", new Point(4, 2))
            .archers("foreignArchers1", new Point(1, 3))
            .archers("foreignArchers2", new Point(3, 3))
        );

        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        // look for targets
        HasCombatStrengthList targets = AttackAction.getTargetsToAttack(world.unit("archers"));
        assertThat(targets)
            .hasSize(6)
            .containsExactlyInAnyOrder(
                world.unit("foreignWorkers"), world.unit("foreignWarriors1"), world.unit("foreignWarriors2"),
                world.unit("foreignArchers1"), world.unit("foreignArchers2"), world.city("foreignCity")
            );

        // see what we can capture
        List<Point> locations = CaptureUnitAction.getLocationsToCapture(world.unit("archers"));
        assertThat(locations).hasSize(1);
        assertThat(CaptureUnitAction.getTargetToCaptureAtLocation(world.unit("archers"), locations.get(0)))
            .isEqualTo(world.unit("foreignWorkers"));

        // capture the foreign workers
        assertThat(CaptureUnitAction.capture(world.unit("archers"), world.location("foreignWorkers")))
            .isEqualTo(FOREIGN_UNIT_CAPTURED);

        assertThat(world.unit("foreignWorkers"))
            .returns(world.unit("archers").getCivilization(), e -> e.getCivilization())
            .returns(world.location("archers"), e -> e.getLocation());

        // attack one of foreign warriors
        assertThat(AttackAction.attack(world.unit("archers"), world.location("foreignWarriors2")))
            .isEqualTo(ATTACKED);

        assertThat(world.unit("archers").getPassScore()).isEqualTo(0);
        assertThat(world.unit("foreignWarriors2").getCombatStrength().getStrength()).isEqualTo(7);

        // do the next step to be able to strike again
        world.move();

        // attack the foreign warriors again
        assertThat(AttackAction.attack(world.unit("archers"), world.location("foreignWarriors2")))
            .isEqualTo(TARGET_DESTROYED);

        assertThat(world.unit("archers").getPassScore()).isEqualTo(0);

        // next step
        world.move();

        // attack the second line - archers
        assertThat(AttackAction.attack(world.unit("archers"), world.location("foreignArchers1")))
            .isEqualTo(ATTACKED);

        // next step
        world.move();

        // attack the second line - foreign city
        assertThat(AttackAction.attack(world.unit("archers"), world.location("foreignCity")))
            .isEqualTo(ATTACKED);
    }

    // Scenario:
    // There is a foreign city with foreign workers and foreign warriors in it.
    // The foreign city has defense strength = 30.
    // Four our warriors (melee units) attack the foreign city.
    // After the city is conquered, foreign workers are captured and foreign warriors are destroyed.
    @Test
    public void warriors_conquer_foreign_city() {
        MockTilesMap map = new MockTilesMap(MapType.SIX_TILES,
            " |0 1 2 3 4 5 6 ",
            "-+--------------",
            "0|. . g . . . . ",
            "1| . . g . . . .",
            "2|. . . g g g . ",
            "3| . . . g g g .",
            "4|. . . . . g . ",
            "5| . . . . . . .");
        MockWorld world = MockWorld.of(map);

        // our forces
        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("Moscow", new Point(2, 0))
            .warriors("warriors1", new Point(4, 2))
            .warriors("warriors2", new Point(5, 2))
            .warriors("warriors3", new Point(3, 3))
            .warriors("warriors4", new Point(5, 4))
        );

        // foreign forces
        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .city("Chicago", new Point(4, 3))
            .warriors("foreignWarriors", new Point(4, 3))
            .workers("foreignWorkers", new Point(4, 3))
        );
        world.city("Chicago").getCombatStrength().setStrength(30);

        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        // strike 1
        assertThat(AttackAction.attack(world.unit("warriors1"), world.location("Chicago")))
            .isEqualTo(ATTACKED);

        assertThat(world.unit("warriors1"))
            .returns(0, e -> e.getPassScore())
            .returns(15, e -> e.getCombatStrength().getStrength())
            .returns(2, e -> e.getCombatStrength().getAttackExperience());

        assertThat(russia.cities().size()).isEqualTo(1);

        assertThat(america)
            .returns(1, e -> e.cities().size())
            .returns(2, e -> e.units().size());

        assertThat(world.city("Chicago"))
            .returns(30 - 10, e -> e.getCombatStrength().getStrength());

        assertThat(world.unit("foreignWarriors"))
            .returns(20, e -> e.getCombatStrength().getStrength());

        // strike 2
        assertThat(AttackAction.attack(world.unit("warriors2"), world.location("Chicago")))
            .isEqualTo(ATTACKED);

        assertThat(world.unit("warriors2"))
            .returns(0, e -> e.getPassScore())
            .returns(15, e -> e.getCombatStrength().getStrength())
            .returns(2, e -> e.getCombatStrength().getAttackExperience());

        assertThat(russia.cities().size()).isEqualTo(1);

        assertThat(america)
            .returns(1, e -> e.cities().size())
            .returns(2, e -> e.units().size());

        assertThat(world.city("Chicago"))
            .returns(20 - 9, e -> e.getCombatStrength().getStrength());

        assertThat(world.unit("foreignWarriors"))
            .returns(20, e -> e.getCombatStrength().getStrength());

        // strike 3
        assertThat(AttackAction.attack(world.unit("warriors3"), world.location("Chicago")))
            .isEqualTo(ATTACKED);

        assertThat(world.unit("warriors3"))
            .returns(0, e -> e.getPassScore())
            .returns(15, e -> e.getCombatStrength().getStrength())
            .returns(2, e -> e.getCombatStrength().getAttackExperience());

        assertThat(russia.cities().size()).isEqualTo(1);

        assertThat(america)
            .returns(1, e -> e.cities().size())
            .returns(2, e -> e.units().size());

        assertThat(world.city("Chicago"))
            .returns(20 - 9 - 8, e -> e.getCombatStrength().getStrength());

        assertThat(world.unit("foreignWarriors"))
            .returns(20, e -> e.getCombatStrength().getStrength());

        // strike 4
        assertThat(AttackAction.attack(world.unit("warriors4"), world.location("Chicago")))
            .isEqualTo(TARGET_DESTROYED);

        assertThat(world.unit("warriors4"))
            .returns(0, e -> e.getPassScore())
            .returns(15, e -> e.getCombatStrength().getStrength())
            .returns(2, e -> e.getCombatStrength().getAttackExperience());

        assertThat(russia.cities().size()).isEqualTo(2);

        assertThat(america.cities().size()).isEqualTo(0);

        // foreign city is captured
        assertThat(world.city("Chicago"))
            .returns(world.location("warriors4"), e -> e.getLocation())
            .returns(russia, e -> e.getCivilization());

        // foreign warriors are destroyed and foreign workers are captured
        assertThat(russia.units().size()).isEqualTo(5);
        assertThat(america.units().size()).isEqualTo(0);

        assertThat(world.unit("foreignWorkers"))
            .returns(world.location("Chicago"), e -> e.getLocation())
            .returns(russia, e -> e.getCivilization());
    }
}


