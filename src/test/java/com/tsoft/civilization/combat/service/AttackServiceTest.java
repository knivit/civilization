package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.combat.CombatResultMock;
import com.tsoft.civilization.combat.CombatStrengthMock;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.civilization.city.City;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.util.Point;
import com.tsoft.civilization.web.render.WorldRender;
import org.junit.jupiter.api.Test;

import static com.tsoft.civilization.civilization.L10nCivilization.AMERICA;
import static com.tsoft.civilization.civilization.L10nCivilization.RUSSIA;
import static com.tsoft.civilization.combat.service.AttackService.*;
import static org.assertj.core.api.Assertions.assertThat;

public class AttackServiceTest {

    private static final AttackService attackService = new AttackService();

    @Test
    public void get_targets_to_attack_for_melee_attacker() {
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
            .warriors("foreignWarriors", new Point(2, 1))
        );

        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        // first, there is foreign workers to attack
        HasCombatStrengthList targets = attackService.getTargetsToAttack(world.get("warriors"));
        assertThat(targets)
            .hasSize(1)
            .containsExactly(world.unit("foreignWorkers"));

        // move close the foreign warriors and now they are the target too
        world.unit("foreignWarriors").getMovementService().moveTo(new Point(2, 0));
        targets = attackService.getTargetsToAttack(world.get("warriors"));
        assertThat(targets)
            .hasSize(2)
            .containsExactly(world.get("foreignWorkers"), world.get("foreignWarriors"));
    }

    // Scenario:
    // An archer can fire over an ocean tile but can't over a mountain
    @Test
    public void get_targets_to_attack_for_ranged_attacker() {
        MockWorld world = MockWorld.of(MockTilesMap.of(2,
            " |0 1 2 3 4 5 6 ", " |0 1 2 3 4 5 6 ",
            "-+--------------", "-+--------------",
            "0|. . g g . . . ", "0|. . M M . . . ",
            "1| . . g g . . .", "1| . . . j . . .",
            "2|. . g . g . . ", "2|. . f . . . . ",
            "3| . g g g . . .", "3| . . . h . . ."));

        // our forces
        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .archers("archers", new Point(2, 1)));

        // foreign forces
        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .city("foreignCity", new Point(2, 3))
            .workers("foreignWorkers", new Point(3, 1))
            .warriors("foreignWarriors1", new Point(2, 2))
            .warriors("foreignWarriors2", new Point(4, 2))
            .archers("foreignArchers1", new Point(1, 3))
            .archers("foreignArchers2", new Point(3, 3)));

        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());
        WorldRender.of(this).createHtml(world, russia);

        // look for targets
        HasCombatStrengthList targets = attackService.getTargetsToAttack(world.get("archers"));
        assertThat(targets)
            .hasSize(6)
            .containsExactlyInAnyOrder(
                world.get("foreignWorkers"),
                world.get("foreignWarriors1"), world.get("foreignWarriors2"),
                world.get("foreignArchers1"), world.get("foreignArchers2"),
                world.get("foreignCity"));

        // attack one of foreign warriors
        // combatants are on not adjacent tiles, so foreign warriors can't fire back
        assertThat(attackService.attackTarget(world.get("archers"), world.get("foreignWarriors2")))
            .usingComparator(CombatResultMock::compare)
            .isEqualTo(CombatResultMock.done(
                "SS:5 AS:5 DS:5 DSA:5 RE:0 REA:2 ME:0 MEA:0 DE:0 DEA:0 D:0",
                "SS:0 AS:0 DS:20 DSA:15 RE:0 REA:0 ME:0 MEA:0 DE:0 DEA:1 D:0"));

        assertThat(world.unit("archers").getPassScore()).isEqualTo(0);

        assertThat(world.unit("archers").calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:7 RAR:2 RAE:2 RBS:0",
                "MAL:0 MAS:0 MAE:0 MBS:0",
                "DL:0 DS:5 DE:0",
                "D:0"));

        assertThat(world.unit("foreignWarriors2").calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:0 RAR:0 RAE:0 RBS:0",
                "MAL:0 MAS:10 MAE:0 MBS:5",
                "DL:0 DS:15 DE:1",
                "D:0"));

        // do the next step to be able to strike again
        world.nextYear();

        // attack the foreign warriors again
        assertThat(attackService.attackTarget(world.get("archers"), world.get("foreignWarriors2")))
            .usingComparator(CombatResultMock::compare)
            .isEqualTo(CombatResultMock.done(
                "SS:7 AS:6 DS:5 DSA:5 RE:2 REA:4 ME:0 MEA:0 DE:0 DEA:0 D:0",
                "SS:0 AS:0 DS:16 DSA:10 RE:0 REA:0 ME:0 MEA:0 DE:1 DEA:2 D:0"));

        assertThat(world.unit("archers").getPassScore()).isEqualTo(0);

        // next step
        world.nextYear();

        // attack the second line - archers
        assertThat(attackService.attackTarget(world.get("archers"), world.get("foreignArchers1")))
            .usingComparator(CombatResultMock::compare)
            .isEqualTo(CombatResultMock.done(
                "SS:6 AS:6 DS:5 DSA:5 RE:4 REA:6 ME:0 MEA:0 DE:0 DEA:0 D:0",
                "SS:0 AS:0 DS:5 DSA:-1 RE:0 REA:0 ME:0 MEA:0 DE:0 DEA:1 D:1"));

        assertThat(world.unit("foreignArchers1").isDestroyed()).isTrue();

        // next step
        world.nextYear();

        // attack the second line - foreign city
        assertThat(attackService.attackTarget(world.get("archers"), world.get("foreignCity")))
            .usingComparator(CombatResultMock::compare)
            .isEqualTo(CombatResultMock.done(
                "SS:8 AS:8 DS:5 DSA:5 RE:6 REA:8 ME:0 MEA:0 DE:0 DEA:0 D:0",
                "SS:0 AS:0 DS:37 DSA:29 RE:0 REA:0 ME:0 MEA:0 DE:0 DEA:1 D:0"));
    }

    // Scenario:
    // There is a foreign city with foreign workers and foreign warriors in it.
    // Four our warriors (melee units) attack the foreign city.
    // After the city is conquered, foreign workers are captured and foreign warriors are destroyed.
    @Test
    public void warriors_conquer_foreign_city() {
        MockWorld world = MockWorld.of(MockTilesMap.of(
            " |0 1 2 3 4 5 6 ",
            "-+--------------",
            "0|. . g . . . . ",
            "1| . . g . . . .",
            "2|. . . g g g . ",
            "3| . . . g g g .",
            "4|. . . . . g . ",
            "5| . . . . . . ."));

        // our forces
        Civilization russia = world.createCivilization(RUSSIA, new MockScenario()
            .city("city", new Point(2, 0))
            .warriors("warriors1", new Point(4, 2))
            .warriors("warriors2", new Point(5, 2))
            .warriors("warriors3", new Point(3, 3))
            .warriors("warriors4", new Point(5, 3))
            .warriors("warriors5", new Point(5, 4)));

        // foreign forces
        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .city("foreignCity", new Point(4, 3))
            .warriors("foreignWarriors", new Point(4, 3))
            .workers("foreignWorkers", new Point(4, 3)));

        City foreignCity = world.city("foreignCity");
        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        assertThat(foreignCity.calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:7 RAR:2 RAE:0 RBS:0",
                "MAL:0 MAS:4 MAE:0 MBS:0",
                "DL:0 DS:40 DE:0",
                "D:0"));

        // strike 1
        assertThat(attackService.attackTarget(world.get("warriors1"), foreignCity))
            .usingComparator(CombatResultMock::compare)
            .isEqualTo(CombatResultMock.done(
                "SS:10 AS:10 DS:20 DSA:20 RE:0 RE:0 REA:0 ME:0 MEA:2 DE:0 DEA:0 D:0",
                "SS:0 AS:0 DS:40 DSA:30 RE:0 RE:0 REA:0 ME:0 MEA:0 DE:0 DEA:1 D:0"));

        assertThat(world.unit("warriors1"))
            .returns(0, AbstractUnit::getPassScore);

        assertThat(russia.getCityService().size()).isEqualTo(1);

        assertThat(america)
            .returns(1, e -> e.getCityService().size())
            .returns(2, e -> e.getUnitService().size());

        assertThat(world.unit("warriors1").calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:0 RAR:0 RAE:0 RBS:0",
                "MAL:0 MAS:10 MAE:2 MBS:5",
                "DL:0 DS:20 DE:0",
                "D:0"));

        assertThat(foreignCity.calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:7 RAR:2 RAE:0 RBS:0",
                "MAL:0 MAS:4 MAE:0 MBS:0",
                "DL:0 DS:30 DE:1",
                "D:0"));

        assertThat(world.unit("foreignWarriors").calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:0 RAR:0 RAE:0 RBS:0",
                "MAL:0 MAS:10 MAE:0 MBS:5",
                "DL:0 DS:20 DE:0",
                "D:0"));

        // strike 2
        assertThat(attackService.attackTarget(world.get("warriors2"), foreignCity))
            .usingComparator(CombatResultMock::compare)
            .isEqualTo(CombatResultMock.done(
                "SS:10 AS:9 DS:20 DSA:20 RE:0 REA:0 ME:0 MEA:2 DE:0 DEA:0 D:0",
                "SS:0 AS:0 DS:30 DSA:21 RE:0 REA:0 ME:0 MEA:0 DE:1 DEA:2 D:0"));

        assertThat(world.unit("warriors2"))
            .returns(0, AbstractUnit::getPassScore);

        assertThat(russia.getCityService().size()).isEqualTo(1);

        assertThat(america)
            .returns(1, e -> e.getCityService().size())
            .returns(2, e -> e.getUnitService().size());

        assertThat(world.unit("warriors2").calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:0 RAR:0 RAE:0 RBS:0",
                "MAL:0 MAS:10 MAE:2 MBS:5",
                "DL:0 DS:20 DE:0",
                "D:0"));

        assertThat(foreignCity.calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:7 RAR:2 RAE:0 RBS:0",
                "MAL:0 MAS:4 MAE:0 MBS:0",
                "DL:0 DS:21 DE:2",
                "D:0"));

        assertThat(world.unit("foreignWarriors").calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:0 RAR:0 RAE:0 RBS:0",
                "MAL:0 MAS:10 MAE:0 MBS:5",
                "DL:0 DS:20 DE:0",
                "D:0"));

        // strike 3
        assertThat(attackService.attackTarget(world.get("warriors3"), foreignCity))
            .usingComparator(CombatResultMock::compare)
            .isEqualTo(CombatResultMock.done(
                "SS:10 AS:8 DS:20 DSA:20 RE:0 REA:0 ME:0 MEA:2 DE:0 DEA:0 D:0",
                "SS:0 AS:0 DS:21 DSA:13 RE:0 REA:0 ME:0 MEA:0 DE:2 DEA:3 D:0"));

        assertThat(world.unit("warriors3"))
            .returns(0, AbstractUnit::getPassScore);

        assertThat(russia.getCityService().size()).isEqualTo(1);

        assertThat(america)
            .returns(1, e -> e.getCityService().size())
            .returns(2, e -> e.getUnitService().size());

        assertThat(world.unit("warriors3").calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:0 RAR:0 RAE:0 RBS:0",
                "MAL:0 MAS:10 MAE:2 MBS:5",
                "DL:0 DS:20 DE:0",
                "D:0"));

        assertThat(foreignCity.calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:7 RAR:2 RAE:0 RBS:0",
                "MAL:0 MAS:4 MAE:0 MBS:0",
                "DL:0 DS:13 DE:3",
                "D:0"));

        assertThat(world.unit("foreignWarriors").calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:0 RAR:0 RAE:0 RBS:0",
                "MAL:0 MAS:10 MAE:0 MBS:5",
                "DL:0 DS:20 DE:0",
                "D:0"));

        // strike 4
        assertThat(attackService.attackTarget(world.get("warriors4"), foreignCity))
            .usingComparator(CombatResultMock::compare)
            .isEqualTo(CombatResultMock.done(
                "SS:10 AS:7 DS:20 DSA:20 RE:0 REA:0 ME:0 MEA:2 DE:0 DEA:0 D:0",
                "SS:0 AS:0 DS:13 DSA:6 RE:0 REA:0 ME:0 MEA:0 DE:3 DEA:4 D:0"));

        assertThat(foreignCity.calcCombatStrength())
            .usingComparator(CombatStrengthMock::compare)
            .isEqualTo(CombatStrengthMock.of(
                "RAL:0 RAS:7 RAR:2 RAE:0 RBS:0",
                "MAL:0 MAS:4 MAE:0 MBS:0",
                "DL:0 DS:6 DE:4",
                "D:0"));

        // strike 5 - city and foreign workers in it are captured
        assertThat(attackService.attackTarget(world.get("warriors5"), foreignCity))
            .usingComparator(CombatResultMock::compare)
            .isEqualTo(CombatResultMock.done(
                "SS:10 AS:6 DS:20 DSA:20 RE:0 REA:0 ME:0 MEA:2 DE:0 DEA:0 D:0",
                "SS:0 AS:0 DS:6 DSA:0 RE:0 REA:0 ME:0 MEA:0 DE:4 DEA:5 D:1"));

        assertThat(russia.getCityService().size()).isEqualTo(2);

        assertThat(america.getCityService().size()).isEqualTo(0);

        // foreign city captured
        assertThat(world.city("foreignCity"))
            .returns(world.location("warriors5"), City::getLocation)
            .returns(russia, City::getCivilization);

        // foreign warriors destroyed
        assertThat(world.unit("foreignWarriors").isDestroyed()).isTrue();

        // foreign workers captured
        assertThat(russia.getUnitService().size()).isEqualTo(5 + 1);
        assertThat(world.unit("foreignWorkers"))
            .returns(world.location("foreignCity"), AbstractUnit::getLocation)
            .returns(russia, AbstractUnit::getCivilization);

        // foreign civilization erased
        assertThat(america.getUnitService().size()).isEqualTo(0);
        assertThat(america.isDestroyed()).isTrue();
    }

    @Test
    public void attacker_not_military() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .workers("workers", new Point(2, 0)));
        AbstractUnit workers = world.get("workers");

        assertThat(attackService.attack(workers, null)).isEqualTo(NOT_MILITARY_UNIT);
    }

    @Test
    public void no_targets_to_attack() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        AbstractUnit warriors = world.get("warriors");

        assertThat(attackService.attack(warriors, null)).isEqualTo(NO_TARGETS_TO_ATTACK);
    }

    @Test
    public void no_targets_to_attack_within_reach() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        AbstractUnit warriors = world.get("warriors");

        world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(2, 2)));

        assertThat(attackService.attack(warriors, null)).isEqualTo(NO_TARGETS_TO_ATTACK);
    }

    @Test
    public void attacker_not_found() {
        assertThat(attackService.attack(null, null)).isEqualTo(ATTACKER_NOT_FOUND);
    }

    @Test
    public void destroyed_attacker_not_found() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        AbstractUnit warriors = world.get("warriors");

        warriors.destroy();

        assertThat(attackService.attack(warriors, null)).isEqualTo(ATTACKER_NOT_FOUND);
    }
}
