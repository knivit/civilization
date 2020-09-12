package com.tsoft.civilization.action.unit;

import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.action.ActionAbstractResult;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.MapType;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.action.AttackAction;
import com.tsoft.civilization.unit.action.AttackActionResults;
import com.tsoft.civilization.unit.action.CaptureUnitAction;
import com.tsoft.civilization.unit.action.CaptureUnitActionResults;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.archers.Archers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
import com.tsoft.civilization.unit.UnitFactory;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AttackActionTest {
    @Test
    public void targetsForMeleeAttackAndCapture() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 3 ",
                "-+--------",
                "0|. g g . ",
                "1| . g g .",
                "2|. . g . ",
                "3| . . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(mockWorld, 0);
        Civilization c2 = new Civilization(mockWorld, 1);
        mockWorld.setCivilizationsRelations(c1, c2, CivilizationsRelations.WAR);

        Warriors warriors = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c1.addUnit(warriors, new Point(1, 0));
        Workers foreignWorkers = UnitFactory.newInstance(Workers.CLASS_UUID);
        c2.addUnit(foreignWorkers, new Point(1, 1));
        Warriors foreignWarriors1 = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c2.addUnit(foreignWarriors1, new Point(2, 1));
        Warriors foreignWarriors2 = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c2.addUnit(foreignWarriors2, new Point(2, 2));

        // first, there is foreign workers to attack
        HasCombatStrengthList targets = AttackAction.getTargetsToAttack(warriors);
        assertEquals(1, targets.size());
        assertEquals(foreignWorkers, targets.get(0));

        // move close the foreign warriors and now they are the target too
        foreignWarriors1.setLocation(new Point(2, 0));
        targets = AttackAction.getTargetsToAttack(warriors);
        assertEquals(2, targets.size());

        // see what we can capture
        List<Point> locations = CaptureUnitAction.getLocationsToCapture(warriors);
        assertEquals(1, locations.size());
        assertEquals(foreignWorkers, CaptureUnitAction.getTargetToCaptureAtLocation(warriors, locations.get(0)));

        // capture the foreign workers and move on it's tile
        assertEquals(CaptureUnitActionResults.FOREIGN_UNIT_CAPTURED, CaptureUnitAction.capture(warriors, foreignWorkers.getLocation()));
        assertEquals(new Point(1, 1), warriors.getLocation());
        assertEquals(foreignWorkers.getLocation(), warriors.getLocation());
        assertEquals(warriors.getCivilization(), foreignWorkers.getCivilization());

        // there must be two foreign warriors to attack
        targets = AttackAction.getTargetsToAttack(warriors);
        assertEquals(2, targets.size());
        assertTrue(targets.contains(foreignWarriors1));
        assertTrue(targets.contains(foreignWarriors2));

        // attack one of them
        assertEquals(AttackActionResults.ATTACKED, AttackAction.attack(warriors, foreignWarriors2.getLocation()));
    }

    // Scenario:
    // An archer can fire through an ocean tile but can't through a mountain
    @Test
    public void targetsForRangedAttackAndCapture() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES, 2,
                " |0 1 2 3 4 5 6 ", " |0 1 2 3 4 5 6 ",
                "-+--------------", "-+--------------",
                "0|. . g g . . . ", "0|. . M M . . . ",
                "1| . . g g . . .", "1| . . . j . . .",
                "2|. . g . g . . ", "2|. . f . . . . ",
                "3| . g g g . . .", "3| . . . h . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(mockWorld, 0);
        Civilization c2 = new Civilization(mockWorld, 1);
        mockWorld.setCivilizationsRelations(c1, c2, CivilizationsRelations.WAR);

        // our forces
        Archers archers = UnitFactory.newInstance(Archers.CLASS_UUID);
        c1.addUnit(archers, new Point(2, 1));

        // foreign forces
        Workers foreignWorkers = UnitFactory.newInstance(Workers.CLASS_UUID);
        c2.addUnit(foreignWorkers, new Point(3, 1));
        Warriors foreignWarriors1 = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c2.addUnit(foreignWarriors1, new Point(2, 2));
        Warriors foreignWarriors2 = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c2.addUnit(foreignWarriors2, new Point(4, 2));
        Archers foreignArchers1 = UnitFactory.newInstance(Archers.CLASS_UUID);
        c2.addUnit(foreignArchers1, new Point(1, 3));
        City foreignCity = new City(c2, new Point(2, 3));
        Archers foreignArchers2 = UnitFactory.newInstance(Archers.CLASS_UUID);
        c2.addUnit(foreignArchers2, new Point(3, 3));

        // look for targets
        HasCombatStrengthList targets = AttackAction.getTargetsToAttack(archers);
        assertEquals(6, targets.size());
        assertTrue(targets.contains(foreignWorkers));
        assertTrue(targets.contains(foreignWarriors1));
        assertTrue(targets.contains(foreignWarriors2));
        assertTrue(targets.contains(foreignArchers1));
        assertTrue(targets.contains(foreignCity));
        assertTrue(targets.contains(foreignArchers2));

        // see what we can capture
        List<Point> locations = CaptureUnitAction.getLocationsToCapture(archers);
        assertEquals(1, locations.size());
        assertEquals(foreignWorkers, CaptureUnitAction.getTargetToCaptureAtLocation(archers, locations.get(0)));

        // capture the foreign workers
        assertEquals(CaptureUnitActionResults.FOREIGN_UNIT_CAPTURED, CaptureUnitAction.capture(archers, foreignWorkers.getLocation()));
        assertEquals(foreignWorkers.getLocation(), archers.getLocation());
        assertEquals(archers.getCivilization(), foreignWorkers.getCivilization());

        // attack one of foreign warriors
        assertEquals(AttackActionResults.ATTACKED, AttackAction.attack(archers, foreignWarriors2.getLocation()));
        assertEquals(0, archers.getPassScore());
        assertEquals(7, foreignWarriors2.getCombatStrength().getStrength());

        // next step to be able to strike again
        mockWorld.step();

        // attack the foreign warriors again
        assertEquals(AttackActionResults.TARGET_DESTROYED, AttackAction.attack(archers, foreignWarriors2.getLocation()));
        assertEquals(0, archers.getPassScore());

        // next step
        mockWorld.step();

        // attack the second line - archers
        assertEquals(AttackActionResults.ATTACKED, AttackAction.attack(archers, foreignArchers1.getLocation()));

        // next step
        mockWorld.step();

        // attack the second line - foreign city
        assertEquals(AttackActionResults.ATTACKED, AttackAction.attack(archers, foreignCity.getLocation()));
    }

    // Scenario:
    // There is a foreign city with foreign workers and foreign warriors in it.
    // The foreign city has defense strength = 30.
    // Four our warriors (melee units) attack the foreign city.
    // After the city is conquered, foreign workers are captured and foreign warriors are destroyed.
    @Test
    public void warriorsConquerForeignCity() {
        MockTilesMap mockTilesMap = new MockTilesMap(MapType.SIX_TILES,
                " |0 1 2 3 4 5 6 ",
                "-+--------------",
                "0|. . g . . . . ",
                "1| . . g . . . .",
                "2|. . . g g g . ",
                "3| . . . g g g .",
                "4|. . . . . g . ",
                "5| . . . . . . .");
        MockWorld mockWorld = new MockWorld(mockTilesMap);
        Civilization c1 = new Civilization(mockWorld, 0);
        Civilization c2 = new Civilization(mockWorld, 1);
        mockWorld.setCivilizationsRelations(c1, c2, CivilizationsRelations.WAR);

        // our forces
        City city = new City(c1, new Point(2, 0));
        Warriors warriors1 = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c1.addUnit(warriors1, new Point(4, 2));
        Warriors warriors2 = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c1.addUnit(warriors2, new Point(5, 2));
        Warriors warriors3 = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c1.addUnit(warriors3, new Point(3, 3));
        Warriors warriors4 = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c1.addUnit(warriors4, new Point(5, 4));

        // foreign forces
        City foreignCity = new City(c2, new Point(4, 3));
        foreignCity.getCombatStrength().setStrength(30);
        Warriors foreignWarriors = UnitFactory.newInstance(Warriors.CLASS_UUID);
        c2.addUnit(foreignWarriors, new Point(4, 3));
        Workers foreignWorkers = UnitFactory.newInstance(Workers.CLASS_UUID);
        c2.addUnit(foreignWorkers, new Point(4, 3));

        // strike 1
        ActionAbstractResult result = AttackAction.attack(warriors1, foreignCity.getLocation());

        assertEquals(AttackActionResults.ATTACKED, result);
        assertEquals(0, warriors1.getPassScore());
        assertEquals(1, c1.getCities().size());
        assertEquals(1, c2.getCities().size());
        assertEquals(15, warriors1.getCombatStrength().getStrength());
        assertEquals(2, warriors1.getCombatStrength().getAttackExperience());
        assertEquals(30 - 10, foreignCity.getCombatStrength().getStrength());
        assertEquals(20, foreignWarriors.getCombatStrength().getStrength());
        assertEquals(2, c2.getUnits().size());

        // strike 2
        result = AttackAction.attack(warriors2, foreignCity.getLocation());

        assertEquals(AttackActionResults.ATTACKED, result);
        assertEquals(0, warriors2.getPassScore());
        assertEquals(1, c1.getCities().size());
        assertEquals(1, c2.getCities().size());
        assertEquals(15, warriors2.getCombatStrength().getStrength());
        assertEquals(2, warriors2.getCombatStrength().getAttackExperience());
        assertEquals(20 - 9, foreignCity.getCombatStrength().getStrength());
        assertEquals(20, foreignWarriors.getCombatStrength().getStrength());
        assertEquals(2, c2.getUnits().size());

        // strike 3
        result = AttackAction.attack(warriors3, foreignCity.getLocation());

        assertEquals(AttackActionResults.ATTACKED, result);
        assertEquals(0, warriors3.getPassScore());
        assertEquals(1, c1.getCities().size());
        assertEquals(1, c2.getCities().size());
        assertEquals(15, warriors3.getCombatStrength().getStrength());
        assertEquals(2, warriors3.getCombatStrength().getAttackExperience());
        assertEquals(20 - 9 - 8, foreignCity.getCombatStrength().getStrength());
        assertEquals(20, foreignWarriors.getCombatStrength().getStrength());
        assertEquals(2, c2.getUnits().size());

        // strike 4
        result = AttackAction.attack(warriors4, foreignCity.getLocation());

        assertEquals(AttackActionResults.TARGET_DESTROYED, result);
        assertEquals(0, warriors4.getPassScore());
        assertEquals(2, c1.getCities().size());
        assertEquals(0, c2.getCities().size());
        assertEquals(city.getCivilization(), foreignCity.getCivilization());
        assertEquals(15, warriors4.getCombatStrength().getStrength());
        assertEquals(2, warriors4.getCombatStrength().getAttackExperience());
        assertEquals(foreignCity.getLocation(), warriors4.getLocation());

        // foreign warriors are destroyed and foreign workers are captured
        assertEquals(5, c1.getUnits().size());
        assertEquals(0, c2.getUnits().size());
        assertEquals(foreignCity.getLocation(), foreignWorkers.getLocation());
        assertEquals(city.getCivilization(), foreignWorkers.getCivilization());
        assertEquals(city.getCivilization(), foreignCity.getCivilization());
    }
}


