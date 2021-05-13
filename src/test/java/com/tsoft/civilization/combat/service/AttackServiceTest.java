package com.tsoft.civilization.combat.service;

import com.tsoft.civilization.MockScenario;
import com.tsoft.civilization.MockWorld;
import com.tsoft.civilization.civilization.Civilization;
import com.tsoft.civilization.civilization.CivilizationsRelations;
import com.tsoft.civilization.combat.CombatResult;
import com.tsoft.civilization.combat.CombatStrength;
import com.tsoft.civilization.combat.HasCombatStrengthList;
import com.tsoft.civilization.improvement.city.City;
import com.tsoft.civilization.tile.MockTilesMap;
import com.tsoft.civilization.unit.AbstractUnit;
import com.tsoft.civilization.unit.civil.workers.Workers;
import com.tsoft.civilization.unit.military.warriors.Warriors;
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

        world.startGame();
        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        // first, there is foreign workers to attack
        HasCombatStrengthList targets = attackService.getTargetsToAttack(world.unit("warriors"));
        assertThat(targets)
            .hasSize(1)
            .containsExactly(world.unit("foreignWorkers"));

        // move close the foreign warriors and now they are the target too
        world.unit("foreignWarriors").getMovementService().setLocation(new Point(2, 0));
        targets = attackService.getTargetsToAttack(world.unit("warriors"));
        assertThat(targets)
            .hasSize(2)
            .containsExactly(world.unit("foreignWorkers"), world.unit("foreignWarriors"));
    }

    // Scenario:
    // An archer can fire through an ocean tile but can't through a mountain
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

        world.startGame();
        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());
        WorldRender.of(this).createHtml(world, russia);

        // look for targets
        HasCombatStrengthList targets = attackService.getTargetsToAttack(world.unit("archers"));
        assertThat(targets)
            .hasSize(6)
            .containsExactlyInAnyOrder(
                world.unit("foreignWorkers"),
                world.unit("foreignWarriors1"), world.unit("foreignWarriors2"),
                world.unit("foreignArchers1"), world.unit("foreignArchers2"),
                world.city("foreignCity")
            );

        // attack one of foreign warriors
        assertThat(attackService.attackTarget(world.unit("archers"), world.unit("foreignWarriors2")))
            .isNotNull()
            .returns(30, CombatResult::getAttackerDefenseStrength)
            .returns(25, CombatResult::getAttackerDefenseStrengthAfterAttack)
            .returns(0, CombatResult::getAttackerAttackExperience)
            .returns(2, CombatResult::getAttackerAttackExperienceAfterAttack)
            .returns(false, CombatResult::isAttackerDestroyed)

            .returns(13, CombatResult::getOriginalStrikeStrength)
            .returns(13, CombatResult::getAppliedStrikeStrength)

            .returns(20, CombatResult::getTargetDefenseStrength)
            .returns(7, CombatResult::getTargetDefenseStrengthAfterAttack)
            .returns(0, CombatResult::getTargetDefenseExperience)
            .returns(1, CombatResult::getTargetDefenseExperienceAfterAttack)
            .returns(false, CombatResult::isTargetDestroyed)

            .returns(5, CombatResult::getTargetBackFireStrength)

            .returns(true, CombatResult::isDone)
            .returns(false, CombatResult::isSkippedAsRangedUndershoot)
            .returns(false, CombatResult::isSkippedAsNoTargetsToAttack)
            .returns(false, CombatResult::isSkippedAsMeleeNotEnoughPassScore);

        assertThat(world.unit("archers").getPassScore()).isEqualTo(0);

        assertThat(world.unit("foreignWarriors2").calcCombatStrength())
            .isNotNull()
            .returns(10, CombatStrength::getMeleeAttackStrength)
            .returns(5, CombatStrength::getMeleeBackFireStrength)
            .returns(0, CombatStrength::getRangedAttackStrength)
            .returns(0, CombatStrength::getRangedAttackRadius)
            .returns(7, CombatStrength::getDefenseStrength)
            .returns(1, CombatStrength::getDefenseExperience)
            .returns(false, CombatStrength::isDestroyed);

        // do the next step to be able to strike again
        world.nextYear();

        // attack the foreign warriors again
        assertThat(attackService.attackTarget(world.unit("archers"), world.unit("foreignWarriors2")))
            .isNotNull()
            .returns(25, CombatResult::getAttackerDefenseStrength)
            .returns(20, CombatResult::getAttackerDefenseStrengthAfterAttack)
            .returns(2, CombatResult::getAttackerAttackExperience)
            .returns(4, CombatResult::getAttackerAttackExperienceAfterAttack)
            .returns(false, CombatResult::isAttackerDestroyed)

            .returns(15, CombatResult::getOriginalStrikeStrength)
            .returns(14, CombatResult::getAppliedStrikeStrength)

            .returns(7, CombatResult::getTargetDefenseStrength)
            .returns(-7, CombatResult::getTargetDefenseStrengthAfterAttack)
            .returns(1, CombatResult::getTargetDefenseExperience)
            .returns(2, CombatResult::getTargetDefenseExperienceAfterAttack)
            .returns(true, CombatResult::isTargetDestroyed)

            .returns(5, CombatResult::getTargetBackFireStrength)

            .returns(true, CombatResult::isDone)
            .returns(false, CombatResult::isSkippedAsRangedUndershoot)
            .returns(false, CombatResult::isSkippedAsNoTargetsToAttack)
            .returns(false, CombatResult::isSkippedAsMeleeNotEnoughPassScore);

        assertThat(world.unit("archers").getPassScore()).isEqualTo(0);

        // next step
        world.nextYear();

        // attack the second line - archers
        assertThat(attackService.attackTarget(world.unit("archers"), world.unit("foreignArchers1")))
            .isNotNull()
            .returns(20, CombatResult::getAttackerDefenseStrength)
            .returns(15, CombatResult::getAttackerDefenseStrengthAfterAttack)
            .returns(4, CombatResult::getAttackerAttackExperience)
            .returns(6, CombatResult::getAttackerAttackExperienceAfterAttack)
            .returns(false, CombatResult::isAttackerDestroyed)

            .returns(14, CombatResult::getOriginalStrikeStrength)
            .returns(14, CombatResult::getAppliedStrikeStrength)

            .returns(30, CombatResult::getTargetDefenseStrength)
            .returns(16, CombatResult::getTargetDefenseStrengthAfterAttack)
            .returns(0, CombatResult::getTargetDefenseExperience)
            .returns(1, CombatResult::getTargetDefenseExperienceAfterAttack)
            .returns(false, CombatResult::isTargetDestroyed)

            .returns(5, CombatResult::getTargetBackFireStrength)

            .returns(true, CombatResult::isDone)
            .returns(false, CombatResult::isSkippedAsRangedUndershoot)
            .returns(false, CombatResult::isSkippedAsNoTargetsToAttack)
            .returns(false, CombatResult::isSkippedAsMeleeNotEnoughPassScore);

        // next step
        world.nextYear();

        // attack the second line - foreign city
        assertThat(attackService.attackTarget(world.unit("archers"), world.city("foreignCity")))
            .isNotNull()
            .returns(15, CombatResult::getAttackerDefenseStrength)
            .returns(10, CombatResult::getAttackerDefenseStrengthAfterAttack)
            .returns(6, CombatResult::getAttackerAttackExperience)
            .returns(8, CombatResult::getAttackerAttackExperienceAfterAttack)
            .returns(false, CombatResult::isAttackerDestroyed)

            .returns(16, CombatResult::getOriginalStrikeStrength)
            .returns(16, CombatResult::getAppliedStrikeStrength)

            .returns(50, CombatResult::getTargetDefenseStrength)
            .returns(34, CombatResult::getTargetDefenseStrengthAfterAttack)
            .returns(0, CombatResult::getTargetDefenseExperience)
            .returns(1, CombatResult::getTargetDefenseExperienceAfterAttack)
            .returns(false, CombatResult::isTargetDestroyed)

            .returns(5, CombatResult::getTargetBackFireStrength)

            .returns(true, CombatResult::isDone)
            .returns(false, CombatResult::isSkippedAsRangedUndershoot)
            .returns(false, CombatResult::isSkippedAsNoTargetsToAttack)
            .returns(false, CombatResult::isSkippedAsMeleeNotEnoughPassScore);
    }

    // Scenario:
    // There is a foreign city with foreign workers and foreign warriors in it.
    // The foreign city has defense strength = 30.
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
            .warriors("warriors4", new Point(5, 4))
        );

        // foreign forces
        Civilization america = world.createCivilization(AMERICA, new MockScenario()
            .city("foreignCity", new Point(4, 3))
            .warriors("foreignWarriors", new Point(4, 3))
            .workers("foreignWorkers", new Point(4, 3))
        );

        world.startGame();

        City foreignCity = world.city("foreignCity");
        world.setCivilizationsRelations(russia, america, CivilizationsRelations.war());

        // strike 1
        assertThat(attackService.attackTarget(world.unit("warriors1"), foreignCity))
            .returns(true, CombatResult::isDone);

        assertThat(world.unit("warriors1"))
            .returns(0, AbstractUnit::getPassScore)
            .returns(15, e -> e.calcCombatStrength().getDefenseStrength());

        assertThat(russia.getCityService().size()).isEqualTo(1);

        assertThat(america)
            .returns(1, e -> e.getCityService().size())
            .returns(2, e -> e.getUnitService().size());

        assertThat(world.city("foreignCity"))
            .returns(30 - 10, e -> e.calcCombatStrength().getDefenseStrength());

        assertThat(world.unit("foreignWarriors"))
            .returns(20, e -> e.calcCombatStrength().getDefenseStrength());

        // strike 2
        assertThat(attackService.attackTarget(world.unit("warriors2"), foreignCity))
            .returns(true, CombatResult::isDone);

        assertThat(world.unit("warriors2"))
            .returns(0, AbstractUnit::getPassScore)
            .returns(15, e -> e.calcCombatStrength().getDefenseStrength());

        assertThat(russia.getCityService().size()).isEqualTo(1);

        assertThat(america)
            .returns(1, e -> e.getCityService().size())
            .returns(2, e -> e.getUnitService().size());

        assertThat(world.city("foreignCity"))
            .returns(20 - 9, e -> e.calcCombatStrength().getDefenseStrength());

        assertThat(world.unit("foreignWarriors"))
            .returns(20, e -> e.calcCombatStrength().getDefenseStrength());

        // strike 3
        assertThat(attackService.attackTarget(world.unit("warriors3"), foreignCity))
            .returns(true, CombatResult::isDone);

        assertThat(world.unit("warriors3"))
            .returns(0, AbstractUnit::getPassScore)
            .returns(15, e -> e.calcCombatStrength().getDefenseStrength());

        assertThat(russia.getCityService().size()).isEqualTo(1);

        assertThat(america)
            .returns(1, e -> e.getCityService().size())
            .returns(2, e -> e.getUnitService().size());

        assertThat(world.city("foreignCity"))
            .returns(20 - 9 - 8, e -> e.calcCombatStrength().getDefenseStrength());

        assertThat(world.unit("foreignWarriors"))
            .returns(20, e -> e.calcCombatStrength().getDefenseStrength());

        // strike 4
        assertThat(attackService.attackTarget(world.unit("warriors4"), foreignCity))
            .returns(true, CombatResult::isDone)
            .returns(true, CombatResult::isTargetDestroyed);

        assertThat(world.unit("warriors4"))
            .returns(0, AbstractUnit::getPassScore)
            .returns(15, e -> e.calcCombatStrength().getDefenseStrength());

        assertThat(russia.getCityService().size()).isEqualTo(2);

        assertThat(america.getCityService().size()).isEqualTo(0);

        // foreign city is captured
        assertThat(world.city("foreignCity"))
            .returns(world.location("warriors4"), City::getLocation)
            .returns(russia, City::getCivilization);

        // foreign warriors are destroyed and foreign workers are captured
        assertThat(russia.getUnitService().size()).isEqualTo(5);
        assertThat(america.getUnitService().size()).isEqualTo(0);

        assertThat(world.unit("foreignWorkers"))
            .returns(world.location("foreignCity"), AbstractUnit::getLocation)
            .returns(russia, AbstractUnit::getCivilization);
    }

    @Test
    public void attacker_not_military() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .workers("workers", new Point(2, 0)));
        Workers workers = (Workers) world.unit("workers");
        world.startGame();

        assertThat(attackService.attack(workers, null)).isEqualTo(NOT_MILITARY_UNIT);
    }

    @Test
    public void no_targets_to_attack() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        Warriors warriors = (Warriors) world.unit("warriors");
        world.startGame();

        assertThat(attackService.attack(warriors, null)).isEqualTo(NO_TARGETS_TO_ATTACK);
    }

    @Test
    public void no_targets_to_attack_within_reach() {
        MockWorld world = MockWorld.newSimpleWorld();

        world.createCivilization(RUSSIA, new MockScenario()
            .warriors("warriors", new Point(2, 0)));
        Warriors warriors = (Warriors) world.unit("warriors");

        world.createCivilization(AMERICA, new MockScenario()
            .workers("foreignWorkers", new Point(2, 2)));
        world.startGame();

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
        Warriors warriors = (Warriors) world.unit("warriors");
        world.startGame();

        warriors.destroy();

        assertThat(attackService.attack(warriors, null)).isEqualTo(ATTACKER_NOT_FOUND);
    }
}
