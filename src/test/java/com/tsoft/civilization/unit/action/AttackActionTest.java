package com.tsoft.civilization.unit.action;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.unit.UnitFactory;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        Civilization c1 = world.createCivilization(RUSSIA);
        Civilization c2 = world.createCivilization(AMERICA);
        world.setCivilizationsRelations(c1, c2, CivilizationsRelations.war());

        Warriors warriors = UnitFactory.newInstance(c1, Warriors.CLASS_UUID);
        assertTrue(c1.units().addUnit(warriors, new Point(1, 0)));
        Workers foreignWorkers = UnitFactory.newInstance(c2, Workers.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignWorkers, new Point(1, 1)));
        Warriors foreignWarriors1 = UnitFactory.newInstance(c2, Warriors.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignWarriors1, new Point(2, 1)));
        WorldRender.of(this).createHtml(world, c1);

        // first, there is foreign workers to attack
        HasCombatStrengthList targets = AttackAction.getTargetsToAttack(warriors);
        assertThat(targets)
            .hasSize(1)
            .containsExactly(foreignWorkers);

        // move close the foreign warriors and now they are the target too
        foreignWarriors1.setLocation(new Point(2, 0));
        targets = AttackAction.getTargetsToAttack(warriors);
        assertThat(targets)
            .hasSize(2)
            .containsExactly(foreignWorkers, foreignWarriors1);

        // attack one of them
        assertThat(AttackAction.attack(warriors, foreignWarriors1.getLocation()))
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
        Civilization c1 = world.createCivilization(RUSSIA);
        Civilization c2 = world.createCivilization(AMERICA);
        world.setCivilizationsRelations(c1, c2, CivilizationsRelations.war());

        // our forces
        Archers archers = UnitFactory.newInstance(c1, Archers.CLASS_UUID);
        assertTrue(c1.units().addUnit(archers, new Point(2, 1)));

        // foreign forces
        Workers foreignWorkers = UnitFactory.newInstance(c2, Workers.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignWorkers, new Point(3, 1)));
        Warriors foreignWarriors1 = UnitFactory.newInstance(c2, Warriors.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignWarriors1, new Point(2, 2)));
        Warriors foreignWarriors2 = UnitFactory.newInstance(c2, Warriors.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignWarriors2, new Point(4, 2)));
        Archers foreignArchers1 = UnitFactory.newInstance(c2, Archers.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignArchers1, new Point(1, 3)));
        City foreignCity = c2.createCity(new Point(2, 3));
        Archers foreignArchers2 = UnitFactory.newInstance(c2, Archers.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignArchers2, new Point(3, 3)));

        // look for targets
        HasCombatStrengthList targets = AttackAction.getTargetsToAttack(archers);
        assertThat(targets)
            .hasSize(6)
            .containsExactly(foreignCity, foreignWorkers, foreignWarriors1, foreignWarriors2, foreignArchers1, foreignArchers2);

        // see what we can capture
        List<Point> locations = CaptureUnitAction.getLocationsToCapture(archers);
        assertThat(locations).hasSize(1);
        assertThat(CaptureUnitAction.getTargetToCaptureAtLocation(archers, locations.get(0))).isEqualTo(foreignWorkers);

        // capture the foreign workers
        assertThat(CaptureUnitAction.capture(archers, foreignWorkers.getLocation())).isEqualTo(FOREIGN_UNIT_CAPTURED);
        assertThat(foreignWorkers.getLocation()).isEqualTo(archers.getLocation());
        assertThat(foreignWorkers.getCivilization()).isEqualTo(archers.getCivilization());

        // attack one of foreign warriors
        assertThat(AttackAction.attack(archers, foreignWarriors2.getLocation())).isEqualTo(ATTACKED);
        assertThat(archers.getPassScore()).isEqualTo(0);
        assertThat(foreignWarriors2.getCombatStrength().getStrength()).isEqualTo(7);

        // do the next step to be able to strike again
        world.move();

        // attack the foreign warriors again
        assertThat(AttackAction.attack(archers, foreignWarriors2.getLocation())).isEqualTo(TARGET_DESTROYED);
        assertThat(archers.getPassScore()).isEqualTo(0);

        // next step
        world.move();

        // attack the second line - archers
        assertThat(AttackAction.attack(archers, foreignArchers1.getLocation())).isEqualTo(ATTACKED);

        // next step
        world.move();

        // attack the second line - foreign city
        assertThat(AttackAction.attack(archers, foreignCity.getLocation())).isEqualTo(ATTACKED);
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
        Civilization c1 = world.createCivilization(RUSSIA);
        Civilization c2 = world.createCivilization(AMERICA);
        world.setCivilizationsRelations(c1, c2, CivilizationsRelations.war());

        // our forces
        City city = c1.createCity(new Point(2, 0));
        Warriors warriors1 = UnitFactory.newInstance(c1, Warriors.CLASS_UUID);
        assertTrue(c1.units().addUnit(warriors1, new Point(4, 2)));
        Warriors warriors2 = UnitFactory.newInstance(c1, Warriors.CLASS_UUID);
        assertTrue(c1.units().addUnit(warriors2, new Point(5, 2)));
        Warriors warriors3 = UnitFactory.newInstance(c1, Warriors.CLASS_UUID);
        assertTrue(c1.units().addUnit(warriors3, new Point(3, 3)));
        Warriors warriors4 = UnitFactory.newInstance(c1, Warriors.CLASS_UUID);
        assertTrue(c1.units().addUnit(warriors4, new Point(5, 4)));

        // foreign forces
        City foreignCity = c2.createCity(new Point(4, 3));
        foreignCity.getCombatStrength().setStrength(30);
        Warriors foreignWarriors = UnitFactory.newInstance(c2, Warriors.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignWarriors, new Point(4, 3)));
        Workers foreignWorkers = UnitFactory.newInstance(c2, Workers.CLASS_UUID);
        assertTrue(c2.units().addUnit(foreignWorkers, new Point(4, 3)));

        // strike 1
        ActionAbstractResult result = AttackAction.attack(warriors1, foreignCity.getLocation());

        assertEquals(ATTACKED, result);
        assertEquals(0, warriors1.getPassScore());
        assertEquals(1, c1.cities().size());
        assertEquals(1, c2.cities().size());
        assertEquals(15, warriors1.getCombatStrength().getStrength());
        assertEquals(2, warriors1.getCombatStrength().getAttackExperience());
        assertEquals(30 - 10, foreignCity.getCombatStrength().getStrength());
        assertEquals(20, foreignWarriors.getCombatStrength().getStrength());
        assertEquals(2, c2.units().size());

        // strike 2
        result = AttackAction.attack(warriors2, foreignCity.getLocation());

        assertEquals(ATTACKED, result);
        assertEquals(0, warriors2.getPassScore());
        assertEquals(1, c1.cities().size());
        assertEquals(1, c2.cities().size());
        assertEquals(15, warriors2.getCombatStrength().getStrength());
        assertEquals(2, warriors2.getCombatStrength().getAttackExperience());
        assertEquals(20 - 9, foreignCity.getCombatStrength().getStrength());
        assertEquals(20, foreignWarriors.getCombatStrength().getStrength());
        assertEquals(2, c2.units().size());

        // strike 3
        result = AttackAction.attack(warriors3, foreignCity.getLocation());

        assertEquals(ATTACKED, result);
        assertEquals(0, warriors3.getPassScore());
        assertEquals(1, c1.cities().size());
        assertEquals(1, c2.cities().size());
        assertEquals(15, warriors3.getCombatStrength().getStrength());
        assertEquals(2, warriors3.getCombatStrength().getAttackExperience());
        assertEquals(20 - 9 - 8, foreignCity.getCombatStrength().getStrength());
        assertEquals(20, foreignWarriors.getCombatStrength().getStrength());
        assertEquals(2, c2.units().size());

        // strike 4
        result = AttackAction.attack(warriors4, foreignCity.getLocation());

        assertEquals(TARGET_DESTROYED, result);
        assertEquals(0, warriors4.getPassScore());
        assertEquals(2, c1.cities().size());
        assertEquals(0, c2.cities().size());
        assertEquals(city.getCivilization(), foreignCity.getCivilization());
        assertEquals(15, warriors4.getCombatStrength().getStrength());
        assertEquals(2, warriors4.getCombatStrength().getAttackExperience());
        assertEquals(foreignCity.getLocation(), warriors4.getLocation());

        // foreign warriors are destroyed and foreign workers are captured
        assertEquals(5, c1.units().size());
        assertEquals(0, c2.units().size());
        assertEquals(foreignCity.getLocation(), foreignWorkers.getLocation());
        assertEquals(city.getCivilization(), foreignWorkers.getCivilization());
        assertEquals(city.getCivilization(), foreignCity.getCivilization());
    }
}


